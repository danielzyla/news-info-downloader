package io.github.danielzyla.article;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArticlesPageController {

    private final String country;
    private final String category;
    private final String apiKey;
    private int page;
    private final int pageSize;
    private HttpURLConnection connection;
    private int totalPages;

    public ArticlesPageController(String apiKey) throws IOException {
        this.country = "pl";
        this.category = "business";
        this.apiKey = apiKey;
        this.pageSize = 4;
        this.page = 1;
        getConnection();
    }

    private void getConnection() throws IOException {
        URL url = new URL(String.format("\n" +
                        "https://newsapi.org/v2/top-headlines?country=%s&category=%s&pageSize=%s&page=%s",
                country,
                category,
                pageSize,
                page
        ));

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("X-Api-Key", apiKey);
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() != 200) {
            System.out.println("No success response");
            return;
        }
    }

    public String getArticlesPage(int page) throws IOException {
        this.page = page;
        connection = null;
        getConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String newsDataLine;
        StringBuffer stringBuffer = new StringBuffer();

        while ((newsDataLine = reader.readLine()) != null) {
            stringBuffer.append(newsDataLine);
        }
        String newsDataPage = stringBuffer.toString();
        reader.close();
        return newsDataPage;
    }

    public void setTotalPages(final int totalResults) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) pageSize);
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public JSONObject getPageInJSON(int page) throws IOException {
        final String articlesPage = getArticlesPage(page);
        return new JSONObject(articlesPage);
    }
}
