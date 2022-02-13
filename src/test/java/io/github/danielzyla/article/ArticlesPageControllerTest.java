package io.github.danielzyla.article;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class ArticlesPageControllerTest {

    @Test
    public void updatePage_shouldCallSetTotalPagesMethod() throws IOException, NoSuchFieldException, IllegalAccessException {
        //given
        ArticlesPageController controller = new ArticlesPageController("test-key");
        final Field restClient = ArticlesPageController.class.getDeclaredField("restClient");
        restClient.setAccessible(true);
        ArticleRestClient articleRestClientMock = Mockito.mock(ArticleRestClient.class);
        restClient.set(controller, articleRestClientMock);

        ArticleApiResponsePage apiResponsePage = new ArticleApiResponsePage();
        final Field totalResults = ArticleApiResponsePage.class.getDeclaredField("totalResults");
        totalResults.setAccessible(true);
        totalResults.set(apiResponsePage, 50);

        given(articleRestClientMock.getArticlesPage("test-key", 4, 1)).willReturn(apiResponsePage);

        //when
        controller.updatePage(4, 1);

        //then
        assertEquals(apiResponsePage, controller.getApiResponsePage());
    }

    @Test
    public void setTotalPages_shouldReturn_ProperValueOfTotalPages() throws IOException {
        //given
        ArticlesPageController controller = new ArticlesPageController("test-key");
        int totalResults = 100;
        int pageSize = 8;

        //when
        controller.setTotalPages(totalResults, pageSize);

        //then
        assertEquals(13, controller.getTotalPages());
    }
}