package discord_bot.utils.youtube;

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
import com.google.api.services.youtube.model.SearchListResponse;

import discord_bot.common.Couple;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ApiYoutube {

    private static final String DEVELOPER_KEY = "AIzaSyAq0yKDe2x_Wb5Yb6quZvR9gVIr2WazcNU";

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
}