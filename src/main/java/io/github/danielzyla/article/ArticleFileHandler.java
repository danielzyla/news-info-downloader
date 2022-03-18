package io.github.danielzyla.article;

import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

@Component
class ArticleFileHandler {

    private static final String FILENAME = "articlesData.csv";

    void saveArticlesToFile(final HashSet<ArticleDto> articleSet) {
        try {
            FileSystemUtils.deleteRecursively(Paths.get(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        articleSet.stream().iterator().forEachRemaining(article -> {
            StringBuilder textLineToSave = new StringBuilder();
            try {
                Files.writeString(
                        Paths.get(ArticleFileHandler.FILENAME),
                        (
                                textLineToSave
                                        .append(article.getPublishedAt()).append(";")
                                        .append(article.getSource().getName()).append(";")
                                        .append(article.getAuthor()).append(";")
                                        .append(article.getUrl()).append(";")
                                        .append(article.getTitle()).append(";")
                                        .append(article.getDescription())
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
