package io.github.danielzyla.article;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
class ArticleRestClient {
    private final static String NEWS_API_URL_PATH = "https://newsapi.org/v2/top-headlines/";
    private final static String COUNTRY = "pl";
    private final static String CATEGORY = "business";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private ResponseEntity<ArticleApiResponsePage> responseEntity;
    private final ArticlePaging articlePaging;

    ArticleRestClient(final ArticlePaging articlePaging) {
        this.articlePaging = articlePaging;
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    ArticleApiResponsePage getArticlesPage(final String apiKey) throws InterruptedException {
        Thread getResponse = new Thread(() -> setResponseEntity(apiKey));
        getResponse.setDaemon(true);
        getResponse.start();
        getResponse.join();
        if (getResponseEntity() != null) {
            return getResponseEntity().getBody();
        } else return null;
    }

    void setResponseEntity(final String apiKey) {
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            responseEntity = restTemplate.exchange(
                    String.format(NEWS_API_URL_PATH + "?country=%s&category=%s&pageSize=%s&page=%s",
                            COUNTRY,
                            CATEGORY,
                            ArticlePaging.PAGE_SIZE,
                            articlePaging.getCurrentPage()
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
