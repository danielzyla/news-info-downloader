package io.github.danielzyla.article;

import io.github.danielzyla.utils.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

class ArticleFileHandler implements FileHandler {

    private static ArticleFileHandler fileHandler;

    private ArticleFileHandler() throws IOException {
        createFile("articlesData.txt");
    }

    public static ArticleFileHandler getInstance() throws IOException {
        if (fileHandler == null) {
            fileHandler = new ArticleFileHandler();
        }
        return fileHandler;
    }

    void saveArticlesToFile(final HashSet<ArticleWriteDto> articleSet) {
        articleSet.stream().iterator().forEachRemaining(article -> {
            StringBuilder textLineToSave = new StringBuilder();
            try {
                Files.writeString(
                        Paths.get("articlesData.txt"),
                        (
                                textLineToSave.append(article.getTitle()).append(":")
                                        .append(article.getDescription()).append(":")
                                        .append(article.getAuthor()).append(":")
                                        + System.lineSeparator()
                        ),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
