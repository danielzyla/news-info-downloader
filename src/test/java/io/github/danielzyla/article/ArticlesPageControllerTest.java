package io.github.danielzyla.article;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class ArticlesPageControllerTest {

    static ArticlesPageController controller;
    static ArticleApiResponsePage apiResponsePage;
    static ArticleRestClient articleRestClientMock;
    static ArrayList<Article> articleArrayList;

    @BeforeAll
    static void setController() throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        controller = new ArticlesPageController("test-key");
        apiResponsePage = new ArticleApiResponsePage();
        articleRestClientMock = Mockito.mock(ArticleRestClient.class);

        final Field restClient = ArticlesPageController.class.getDeclaredField("restClient");
        restClient.setAccessible(true);
        restClient.set(controller, articleRestClientMock);

        final Field totalResults = ArticleApiResponsePage.class.getDeclaredField("totalResults");
        totalResults.setAccessible(true);
        totalResults.set(apiResponsePage, 50);

        final Field articles = ArticleApiResponsePage.class.getDeclaredField("articles");
        articles.setAccessible(true);
        articleArrayList = new ArrayList<>();
        articleArrayList.add(new Article());
        articleArrayList.add(new Article());
        articleArrayList.add(new Article());
        articles.set(apiResponsePage, articleArrayList);

        given(articleRestClientMock.getArticlesPage("test-key", 4, 1)).willReturn(apiResponsePage);

        final Field apiResponsePageField = ArticlesPageController.class.getDeclaredField("apiResponsePage");
        apiResponsePageField.setAccessible(true);
        apiResponsePageField.set(controller, apiResponsePage);
    }

    @Test
    void updatePage_shouldCallSetTotalPagesMethod() throws IOException, InterruptedException {
        //given
        //see @BeforeAll

        //when
        controller.updatePage(4, 1);

        //then
        assertEquals(apiResponsePage, controller.getApiResponsePage());
    }

    @Test
    void setTotalPages_shouldReturnProperValueOfTotalPages() {
        //given
        int totalResults = 100;
        int pageSize = 8;

        //when
        controller.setTotalPages(totalResults, pageSize);

        //then
        assertEquals(13, controller.getTotalPages());
    }

    @Test
    void getArticleDtoListFromPage_shouldReturnListOfArticleDtoFromService() {
        //given
        //see @BeforeAll

        //when
        final List<ArticleDto> articleDtoListFromPage = controller.getArticleDtoListFromPage();

        //then
        assertEquals(articleArrayList.size(), articleDtoListFromPage.size());
        assertThat(articleDtoListFromPage.get(0), is(instanceOf(ArticleDto.class)));
    }
}