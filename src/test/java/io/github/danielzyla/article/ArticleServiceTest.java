package io.github.danielzyla.article;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleServiceTest {

    static ArticleService service;

    @Test
    public void addArticlesToSet_shouldAddArticleToArticleSet() {

        //given
        ArticleService service = new ArticleService();
        ArticleDto articleDto = new ArticleDto();

        //when
        service.addArticlesToSet(articleDto);

        //then
        assertEquals(service.getArticleSet().size(), 1);
        assertThat(service.getArticleSet().stream().iterator().next(), is(instanceOf(Article.class)));
    }

    @Test
    public void addArticlesToSet_shouldNotAddToArticlesDuplicatesToSet() throws NoSuchFieldException, IllegalAccessException {

        //given
        final Field author = ArticleDto.class.getDeclaredField("author");
        author.setAccessible(true);
        final Field description = ArticleDto.class.getDeclaredField("description");
        description.setAccessible(true);
        final Field title = ArticleDto.class.getDeclaredField("title");
        title.setAccessible(true);

        ArticleDto articleDto1 = new ArticleDto();
        author.set(articleDto1, "author");
        description.set(articleDto1, "description");
        description.set(articleDto1, "title");

        ArticleDto articleDto2 = new ArticleDto();
        author.set(articleDto2, "author");
        description.set(articleDto2, "description");
        description.set(articleDto2, "title");

        ArticleDto articleDto3 = new ArticleDto();
        author.set(articleDto3, "author");
        description.set(articleDto3, "description");
        description.set(articleDto3, "title");

        //when
        service.addArticlesToSet(articleDto1);
        service.addArticlesToSet(articleDto2);
        service.addArticlesToSet(articleDto3);

        //then
        assertEquals(service.getArticleSet().size(), 1);
        assertThat(service.getArticleSet().stream().iterator().next(), is(instanceOf(Article.class)));
    }

    @BeforeAll
    static void getArticleServiceStub() {
        service = new ArticleService();
    }
}