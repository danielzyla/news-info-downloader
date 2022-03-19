package io.github.danielzyla.article;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleRestClientTest {

    @Test
    void getResponseEntity_shouldReturn401apiServerResponse_WhenWrongApiKeyIsGiven() {
        //given
        ArticlePaging paging = new ArticlePaging();
        paging.setCurrentPage(1);
        ArticleRestClient articleRestClient = new ArticleRestClient(paging);
        ResponseEntity<ArticleApiResponsePage> pageResponseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        final String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(10);

        //when
        articleRestClient.setResponseEntity(randomAlphanumeric);

        assertEquals(pageResponseEntity, articleRestClient.getResponseEntity());
        assertEquals("401 UNAUTHORIZED", articleRestClient.getResponseEntity().getStatusCode().toString());
    }
}