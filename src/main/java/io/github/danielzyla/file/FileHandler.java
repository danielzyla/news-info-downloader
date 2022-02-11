package io.github.danielzyla.file;

import io.github.danielzyla.entity.Article;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.logging.Logger;

public class FileHandler {

    private static FileHandler fileHandler;
    protected final Logger log = Logger.getLogger(FileHandler.class.getName());
    static File articleDataFile;

    private FileHandler() throws IOException {
        createFile();
    }

    public static FileHandler getInstance() throws IOException {
        if (fileHandler == null) {
            fileHandler = new FileHandler();
        }
        return fileHandler;
    }

    private void createFile() throws IOException {
        articleDataFile = new File("articlesData.txt");
        if (!articleDataFile.exists()) {
            boolean isNewFileCreated = articleDataFile.createNewFile();
            log.info("new articleDataFile creation status: " + isNewFileCreated);
        } else {
            log.info("articleDataFile exists");
            new FileOutputStream("articlesData.txt").close();
        }
    }

    public void saveArticlesToFile(final HashSet<Article> articleSet) {
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
