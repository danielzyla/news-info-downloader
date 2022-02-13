package io.github.danielzyla.article;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticlesPageControllerTest {

    @Test
    public void getArticlesPage_shouldThrowExceptionWhenWrongApiKeyIsUsed() throws IOException {
        //given
        ArticlesPageController controller = new ArticlesPageController("xx");
        int page = 5;

        //when, then
        assertThrows(IOException.class, () -> controller.getArticlesPage(page));
    }

    @Test
    public void setTotalPages_shouldReturn_ProperValueOfTotalPages() throws IOException, NoSuchFieldException, IllegalAccessException {
        //given
        ArticlesPageController controller = new ArticlesPageController("xx");
        final Field pageSize = ArticlesPageController.class.getDeclaredField("pageSize");
        pageSize.setAccessible(true);
        pageSize.set(controller, 5);
        int totalResults = 51;

        //when
        controller.setTotalPages(totalResults);

        //then
        assertEquals(11, controller.getTotalPages());
    }

}