package io.github.danielzyla.article;

import lombok.Getter;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Component
@SessionScope
class ArticleRestClient {
    private final static String NEWS_API_URL_PATH = "https://newsapi.org/v2/top-headlines/";
    private final static String CATEGORY = "business";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private ResponseEntity<ArticleApiResponsePage> responseEntity;

    ArticleRestClient() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    ArticleApiResponsePage getArticlesPage(final String country, final String apiKey, final int currentPage) throws InterruptedException {
        Thread getResponse = new Thread(() -> setResponseEntity(country, apiKey, currentPage));
        getResponse.setDaemon(true);
        getResponse.start();
        getResponse.join();
        if (getResponseEntity() != null) {
            return getResponseEntity().getBody();
        } else return null;
    }

    void setResponseEntity(final String country, final String apiKey, final int currentPage) {
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            responseEntity = restTemplate.exchange(
                    String.format(NEWS_API_URL_PATH + "?country=%s&category=%s&pageSize=%s&page=%s",
                            country,
                            CATEGORY,
                            ArticlePaging.PAGE_SIZE,
                            currentPage
                    ),
                    HttpMethod.GET,
                    request,
                    ArticleApiResponsePage.class
            );
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (ResourceAccessException e) {
            e.printStackTrace();
            responseEntity = null;
        }
    }

    ResponseEntity<ArticleApiResponsePage> getResponseEntity() {
        return responseEntity;
    }
}
