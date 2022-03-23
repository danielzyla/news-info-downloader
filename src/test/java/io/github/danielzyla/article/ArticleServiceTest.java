package io.github.danielzyla.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticlePaging paging;

    @Mock
    private ArticleRestClient restClient;

    @Mock
    private Model model;

    @InjectMocks
    private ArticleService service;

    ArticleApiResponsePage getArticleApiResponsePageStub() throws NoSuchFieldException, IllegalAccessException {
        ArticleApiResponsePage apiResponsePage = new ArticleApiResponsePage();
        final Field articlesField = ArticleApiResponsePage.class.getDeclaredField("articles");
        articlesField.setAccessible(true);
        List<Article> articles = new ArrayList<>(List.of(new Article(), new Article(), new Article(), new Article()));
        articlesField.set(apiResponsePage, articles);
        final Field totalResults = ArticleApiResponsePage.class.getDeclaredField("totalResults");
        totalResults.setAccessible(true);
        totalResults.set(apiResponsePage, 4);
        return apiResponsePage;
    }

    @Test
    void getArticleDtoListFromPage_shouldReturnListOfArticleDtoElementsFromArticleApiResponsePage() throws NoSuchFieldException, IllegalAccessException {
        //given
        final ArticleApiResponsePage articleApiResponsePage = getArticleApiResponsePageStub();
        //when
        //then
        assertEquals(service.getArticleDtoListFromPage(articleApiResponsePage).size(), 4);
        assertThat(service.getArticleDtoListFromPage(articleApiResponsePage).get(0)).isInstanceOf(ArticleDto.class);
    }

    @Test
    void updatePage_shouldReturnNotEmptyOptionalOfArticleApiResponsePage() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        //given
        service.setApiKey("testKey");
        when(paging.getCountry()).thenReturn("testCountry");
        when(paging.getCategory()).thenReturn("testCategory");
        when(paging.getCurrentPage()).thenReturn(1);

        final ArticleApiResponsePage articleApiResponsePage = getArticleApiResponsePageStub();
        when(restClient.getArticlesPage("testCountry", "testCategory", "testKey", 1)).thenReturn(articleApiResponsePage);

        //when
        //then
        assertThat(service.updatePage()).hasValue(articleApiResponsePage);
        assertThat(service.updatePage()).isNotEmpty();
        assertTrue(service.updatePage().isPresent());
    }

    @Test
    void updatePage_shouldReturnEmptyOptionalOfArticleApiResponsePage() throws InterruptedException {
        //given
        service.setApiKey("testKey");
        when(paging.getCountry()).thenReturn("testCountry");
        when(paging.getCategory()).thenReturn("testCategory");
        when(paging.getCurrentPage()).thenReturn(1);
        when(restClient.getArticlesPage("testCountry", "testCategory", "testKey", 1)).thenReturn(null);

        //when
        //then
        assertEquals(service.updatePage(), Optional.empty());
        assertThat(service.updatePage()).isEmpty();
    }

    @Test
    void getArticlesPage_shouldSetResponsePageWhenTheResponsePageIsInResponsePageMap() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        //given
        final ArticleApiResponsePage articleApiResponsePage = getArticleApiResponsePageStub();
        service.getApiResponsePageMap().put(1, articleApiResponsePage);
        when(paging.getCurrentPage()).thenReturn(1);
        service.setApiKey("testKey");

        //when
        final String articlesPage = service.getArticlesPage(model);

        //then
        assertThat(articlesPage).isNull();
        assertThat(service.getApiResponsePageCurrent()).isEqualTo(articleApiResponsePage);
    }

    @Test
    void getArticlesPage_shouldReturnMessageWhenResponsePageIsNotInResponsePageMapAndOptionalOfArticleApiResponsePageIsEmpty() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        //given
        service.getApiResponsePageMap().put(1, getArticleApiResponsePageStub());
        when(paging.getCurrentPage()).thenReturn(2);
        service.setApiKey("testKey");
        when(paging.getCountry()).thenReturn("testCountry");
        when(paging.getCategory()).thenReturn("testCategory");
        when(restClient.getArticlesPage("testCountry", "testCategory", "testKey", 2)).thenReturn(null);

        //when
        final String articlesPage = service.getArticlesPage(model);

        //then
        assertNotNull(articlesPage);
        assertThat(articlesPage).isEqualTo("redirect:/?message=nieprawid%C5%82owy+klucz+lub+brak+odpowiedzi+z+serwera+API");
    }

    @Test
    void getArticlesPage_shouldSetResponsePageWhenTheResponsePageIsNotInResponsePageMapAndOptionalOfArticleApiResponsePageIsPresent() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        //given
        final ArticleApiResponsePage articleApiResponsePage1 = getArticleApiResponsePageStub();
        final ArticleApiResponsePage articleApiResponsePage2 = getArticleApiResponsePageStub();
        service.getApiResponsePageMap().put(1, articleApiResponsePage1);
        service.getDownloadedDtoListMap().put(1, service.getArticleDtoListFromPage(articleApiResponsePage1));
        when(paging.getCurrentPage()).thenReturn(2);
        service.setApiKey("testKey");
        when(paging.getCountry()).thenReturn("testCountry");
        when(paging.getCategory()).thenReturn("testCategory");
        when(restClient.getArticlesPage("testCountry", "testCategory", "testKey", 2)).thenReturn(articleApiResponsePage2);

        //when
        final String articlesPage = service.getArticlesPage(model);

        //then
        assertThat(articlesPage).isNull();
        assertThat(service.getApiResponsePageCurrent()).isEqualTo(articleApiResponsePage2);
        assertThat(service.getApiResponsePageMap().size()).isEqualTo(2);
        assertThat(service.getDownloadedDtoListMap().size()).isEqualTo(2);
    }
}