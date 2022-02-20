package io.github.danielzyla;

import io.github.danielzyla.article.ArticleDto;
import io.github.danielzyla.article.ArticlesPageController;

import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class NewsDownloaderApp {

    private boolean isAppRunning;
    private ArticlesPageController controller;
    private final static int PAGE_SIZE = 4;
    private final HashSet<ArticleDto> articlesToSave;

    NewsDownloaderApp() {
        this.isAppRunning = true;
        this.controller = null;
        this.articlesToSave = new HashSet<>();
    }

    public static void main(String[] args) {
        NewsDownloaderApp app = new NewsDownloaderApp();
        app.start();
    }

    void start() {
        try {
            System.out.println("Wpisz klucz do News API :");
            Scanner inputApiKey = new Scanner(System.in);
            final String apiKey = inputApiKey.next();
            controller = new ArticlesPageController(apiKey);
            controller.updatePage(PAGE_SIZE, 1);
        } catch (Exception e) {
            System.out.println("Podano nieprawidłowy klucz !");
            controller = null;
        }
        if (controller != null) operate();
    }

    void operate() {
        while (isAppRunning) {
            final List<ArticleDto> articlesPage = controller.getArticleDtoListFromPage();

            iterateAndViewArticles(articlesPage);

            String info = "Aby wyjść z programu wciśnij 0, a następnie enter.";
            System.out.println("Aby wczytać kolejną stronę wpisz jej numer. " + info);

            selectPage(info);
        }

        if (controller != null) controller.saveArticlesToFile(this.articlesToSave);
    }

    void selectPage(final String info) {
        Integer pageNum = null;
        while (pageNum == null) {
            String info2 = "Wpisz prawidłowy numer strony ! " + info;
            try {
                Scanner input = new Scanner(System.in);
                pageNum = input.nextInt();
                System.out.println(pageNum);
                if (pageNum > 0 && pageNum <= controller.getTotalPages()) {
                    controller.updatePage(PAGE_SIZE, pageNum);
                } else if (pageNum == 0) {
                    isAppRunning = false;
                } else {
                    pageNum = null;
                    System.out.println(info2);
                }
            } catch (InputMismatchException | IOException | InterruptedException e) {
                pageNum = null;
                System.out.println(info2);
            }
        }
    }

    void iterateAndViewArticles(final List<ArticleDto> articlesPage) {
        for (final ArticleDto currentArticle : articlesPage) {
            System.out.println(
                    "\nPODGLĄD ARTYKUŁU: \n\n" +
                            "strona: " + controller.getPage() + " z " + controller.getTotalPages() + "\n\n" +
                            currentArticle + "\n"
            );

            Scanner input = new Scanner(System.in);
            System.out.println("Zapis do pliku ? Wpisz 'T' lub 'N'. " +
                    "Aby wyjść ze strony wciśnij dowolny klawisz oraz enter ...");
            String inputCommand = input.next();

            if (inputCommand.equalsIgnoreCase("T")) addArticleToSet(currentArticle);
            else if (inputCommand.equalsIgnoreCase("N")) continue;
            else break;
        }
    }

    void addArticleToSet(final ArticleDto currentArticle) {
        articlesToSave.add(currentArticle);
    }
}
