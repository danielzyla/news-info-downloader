package io.github.danielzyla.article;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

class ArticleRestClient {
    private final static String NEWS_API_URL_PATH = "https://newsapi.org/v2/top-headlines/";
    private final static String COUNTRY = "pl";
    private final static String CATEGORY = "business";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private ResponseEntity<ArticleApiResponsePage> responseEntity;

    ArticleRestClient() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    ArticleApiResponsePage getArticlesPage(final String apiKey, final int pageSize, final int page) throws InterruptedException {
        Thread getResponse = new Thread(() -> setResponseEntity(apiKey, pageSize, page));
        getResponse.setDaemon(true);
        getResponse.start();
        getResponse.join();
        return getResponseEntity().getBody();
    }

    void setResponseEntity(final String apiKey, final int pageSize, final int page) {
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            responseEntity = restTemplate.exchange(
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
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    ResponseEntity<ArticleApiResponsePage> getResponseEntity() {
        return responseEntity;
    }
}
