package io.github.danielzyla.article;

import org.json.JSONArray;
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
    private JSONObject pageInJSON;
    private final ArticleService service;
    private final ArticleFileHandler fileHandler;

    public ArticlesPageController(String apiKey) throws IOException {
        this.country = "pl";
        this.category = "business";
        this.apiKey = apiKey;
        this.pageSize = 4;
        this.page = 1;
        setConnection();
        this.service = new ArticleService();
        this.fileHandler = ArticleFileHandler.getInstance();
    }

    private void setConnection() throws IOException {
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
        }
    }

    String getArticlesPage(int page) throws IOException {
        this.page = page;
        connection = null;
        setConnection();
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

    void setTotalPages(final int totalResults) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) pageSize);
    }

    public void addArticlesToSet(final JSONArray articlePage, final int i) {
        service.addArticlesToSet(articlePage, i);
    }

    public void saveArticlesToFile() {
        fileHandler.saveArticlesToFile(service.getArticleSet());
    }

    public void updatePage(final int i) throws IOException {
        setPageInJSON(i);
        setTotalPages(pageInJSON.getInt("totalResults"));
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    void setPageInJSON(int page) throws IOException {
        final String articlesPage = getArticlesPage(page);
        this.pageInJSON = new JSONObject(articlesPage);
    }

    public JSONObject getPageInJSON() {
        return pageInJSON;
    }
}
