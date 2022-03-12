package io.github.danielzyla;

import io.github.danielzyla.article.ArticlesPageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsDownloaderApp {

    private final ArticlesPageController controller;
    private final static int PAGE_SIZE = 4;

    public NewsDownloaderApp(ArticlesPageController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(NewsDownloaderApp.class, args);
    }

    public void start(final String inputApiKey) {
        try {
            controller.setApiKey(inputApiKey);
            controller.updatePage(PAGE_SIZE, 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Podano nieprawidłowy klucz !");
        }
    }
}
