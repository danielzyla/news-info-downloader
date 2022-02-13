package io.github.danielzyla.article;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ArticleFileHandlerTest {

    @Test
    public void getInstance_shouldReturnTheSameFileHandlerInstance_WhenCalledMultipleTimes() throws IOException {
        //given, when
        ArticleFileHandler fileHandler1 = ArticleFileHandler.getInstance();
        ArticleFileHandler fileHandler2 = ArticleFileHandler.getInstance();
        ArticleFileHandler fileHandler3 = ArticleFileHandler.getInstance();
        ArticleFileHandler fileHandler4 = ArticleFileHandler.getInstance();

        //then
        assertEquals(fileHandler1, fileHandler2);
        assertEquals(fileHandler2, fileHandler3);
        assertEquals(fileHandler3, fileHandler4);
        assertSame(fileHandler1, fileHandler4);
    }

    @Test
    public void saveArticlesToFile() {
        //TODO
    }
}