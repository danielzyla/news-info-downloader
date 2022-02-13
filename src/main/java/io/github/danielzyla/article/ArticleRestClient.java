package io.github.danielzyla.article;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

class ArticleRestClient {
    private final static String NEWS_API_URL_PATH = "https://newsapi.org/v2/top-headlines/";
    private final static String COUNTRY = "pl";
    private final static String CATEGORY = "business";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;


    ArticleRestClient() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    ArticleApiResponsePage getArticlesPage(final String apiKey, final int pageSize, final int page) {
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<ArticleApiResponsePage> response = restTemplate.exchange(
                    String.format(NEWS_API_URL_PATH + "?country=%s&category=%s&pageSize=%s&page=%s",
                            COUNTRY,
                            CATEGORY,
                            pageSize,
                            page
                    ),
                    HttpMethod.GET,
                    request,
                    ArticleApiResponsePage.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.getCause();
            return null;
        }
    }
}
