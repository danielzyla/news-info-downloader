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
        ArticleRestClient articleRestClient = new ArticleRestClient();
        ResponseEntity<ArticleApiResponsePage> pageResponseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        final String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(10);

        //when
        final ResponseEntity<ArticleApiResponsePage> responseEntity = articleRestClient.getResponseEntity(randomAlphanumeric, 5, 1);

        assertEquals(pageResponseEntity, responseEntity);
        assertEquals("401 UNAUTHORIZED", responseEntity.getStatusCode().toString());
    }
}