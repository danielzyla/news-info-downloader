package io.github.danielzyla.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class FileHandlerTest {

    @Test
    void getInstance_shouldReturnTheSameFileHandlerInstance_WhenCalledMultipleTimes() throws IOException {
        //given, when
        FileHandler fileHandler1 = FileHandler.getInstance();
        FileHandler fileHandler2 = FileHandler.getInstance();
        FileHandler fileHandler3 = FileHandler.getInstance();
        FileHandler fileHandler4 = FileHandler.getInstance();

        //then
        assertEquals(fileHandler1, fileHandler2);
        assertEquals(fileHandler2, fileHandler3);
        assertEquals(fileHandler3, fileHandler4);
        assertSame(fileHandler1, fileHandler4);
    }

    @Test
    void saveArticlesToFile() {
        //TODO
    }
}