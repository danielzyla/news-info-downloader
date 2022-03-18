package io.github.danielzyla.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleFileHandler fileHandler;

    @Mock
    private ArticlePaging paging;

    @Mock
    private ArticleRestClient restClient;

    @InjectMocks
    private ArticleService service;

    @Test
    void getArticleDtoListFromPage_shouldReturnListOfArticleDtoElementsFromArticleApiResponsePage() throws NoSuchFieldException, IllegalAccessException {
        //given

        ArticleApiResponsePage apiResponsePage = new ArticleApiResponsePage();
        final Field articlesField = ArticleApiResponsePage.class.getDeclaredField("articles");
        articlesField.setAccessible(true);
        ArrayList<Article> articles = new ArrayList<>();
        Article article1 = new Article();
        Article article2 = new Article();
        Article article3 = new Article();
        Article article4 = new Article();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);
        articlesField.set(apiResponsePage, articles);

        //when
        //then
        assertEquals(service.getArticleDtoListFromPage(apiResponsePage).size(), 4);
        assertThat(service.getArticleDtoListFromPage(apiResponsePage).get(0), is(instanceOf(ArticleDto.class)));
    }
}