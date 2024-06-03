package discord_bot.utils.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

/**
 * Sample Java code for youtube.search.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/code-samples#java
 */

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;

import discord_bot.utils.Couple;
import discord_bot.utils.Env;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class ApiYoutube {

    private static final String DEVELOPER_KEY = Env.getYtbToken();

    private static final String APPLICATION_NAME = "DiscordBot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final YouTube youtubeService;
    private static final YouTube.Search.List request;

    static {

        try {
            youtubeService = getService();
            request = youtubeService.search().list("snippet");
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Build and return an authorized API client service.
    *
    * @return an authorized API client service
    * @throws GeneralSecurityException, IOException
    */
    public static YouTube getService() throws GeneralSecurityException, IOException {

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
    
    public static Couple<String, String> search(String query) throws IOException {

        SearchListResponse response =  request.setKey(DEVELOPER_KEY)
                .setQ(query)
                .setMaxResults(1L)
                .execute();

        String URL = "https://www.youtube.com/watch?v=" + response.getItems().get(0).getId().getVideoId();
        String title = response.getItems().get(0).getSnippet().getTitle();

        return new Couple<>(title, URL);
    }

    public static List<String> getPlaylistItems(String songIdentifier) throws IOException {
        
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
            .list("snippet");

        songIdentifier = songIdentifier.substring(songIdentifier.indexOf("list=") + 5);
        songIdentifier = songIdentifier.substring(0, songIdentifier.indexOf("&") == -1 ? songIdentifier.length() : songIdentifier.indexOf("&"));

        PlaylistItemListResponse response = request
            .setKey(DEVELOPER_KEY)
            .setPlaylistId(songIdentifier)
            .execute();

        List<String> ret = new ArrayList<>();
        
        int maxI = 10;
        int i = 0;

        do {
            
            for (PlaylistItem item : response.getItems()) {
                ret.add(item.getSnippet().getResourceId().getVideoId());
            }
            
            if (i++ >= maxI) {
                return ret;
            }

        } while( response.getNextPageToken() != null && (response = request.setPageToken(response.getNextPageToken()).execute()) != null);
        
        return ret;
    } 
}