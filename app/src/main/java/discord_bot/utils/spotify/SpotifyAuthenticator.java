package discord_bot.utils.spotify;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import discord_bot.utils.Env;

public class SpotifyAuthenticator {

    public static SpotifyAuthenticator instance;

    private String accessToken;
    private long expiresIn;

    public static SpotifyAuthenticator getInstance() {
        if (instance == null) {
            instance = new SpotifyAuthenticator();
        }
        return instance;
    }

    private SpotifyAuthenticator() {
        authenticate();
    }

    private void authenticate() {

        String clientId = Env.getSpotifyClientI();
        String clientSecret = Env.getSpotifyClientS();

        String tokenUrl = "https://accounts.spotify.com/api/token";

        try {
            HttpPost postRequest = new HttpPost(tokenUrl);
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));

            // Construire le corps de la requête pour obtenir le token d'accès avec le type de subvention "client_credentials"
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "client_credentials"));
            postRequest.setEntity(new UrlEncodedFormEntity(params));
            
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(postRequest);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                JSONObject jsonResponse = new JSONObject(responseString);
                
                this.accessToken = jsonResponse.getString("access_token");
                this.expiresIn = jsonResponse.getInt("expires_in") + System.currentTimeMillis() / 1000;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle(String spotifyURL) {

        if ( System.currentTimeMillis() / 1000 > this.expiresIn ) {
            authenticate();
        }
            
        String url = "https://api.spotify.com/v1/tracks" + spotifyURL.substring(spotifyURL.lastIndexOf("/"));

        try {
            HttpGet getRequest = new HttpGet(url);
            getRequest.setHeader("Authorization", "Bearer " + accessToken);

            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(getRequest);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                JSONObject jsonResponse = new JSONObject(responseString);

                return jsonResponse.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getPlaylistTracks(String songIdentifier) {

        if ( System.currentTimeMillis() / 1000 > this.expiresIn ) {
            authenticate();
        }

        int end = songIdentifier.lastIndexOf("?") == -1 ? songIdentifier.length() : songIdentifier.lastIndexOf("?");
        String url = "https://api.spotify.com/v1/playlists" + songIdentifier.substring(songIdentifier.lastIndexOf("/"), end) + "/tracks";

        List<String> tracks = new ArrayList<>();
        try {
            do {
                HttpGet getRequest = new HttpGet(url);
                getRequest.setHeader("Authorization", "Bearer " + accessToken);

                HttpClient httpClient = HttpClients.createDefault();
                HttpResponse response = httpClient.execute(getRequest);

                HttpEntity entity = response.getEntity();
                url = null;
                if (entity != null) {
                    
                    String responseString = EntityUtils.toString(entity);
                    JSONObject jsonResponse = new JSONObject(responseString);

                    for (int i = 0; i < jsonResponse.getJSONArray("items").length(); i++) {
                        
                        JSONObject track = jsonResponse.getJSONArray("items").getJSONObject(i).getJSONObject("track");
                        JSONObject artist = track.getJSONArray("artists").getJSONObject(0);
                        
                        if ( track.getBoolean("is_local") ) continue;
                        
                        tracks.add(track.getString("name") + " " + artist.getString("name"));
                    }

                    if (jsonResponse.has("next")) {
                        url = jsonResponse.getString("next");
                    }
                }
            } while (url != null && !url.equals("null"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tracks;
    }
}