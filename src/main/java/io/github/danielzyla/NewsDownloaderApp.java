package io.github.danielzyla;

import io.github.danielzyla.article.ArticlesPageController;
import org.json.JSONArray;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class NewsDownloaderApp {

    private boolean isAppRunning;
    private ArticlesPageController controller;

    NewsDownloaderApp() {
        this.isAppRunning = true;
        this.controller = null;
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
            controller.updatePage(1);
            operate();
        } catch (Exception e) {
            System.out.println("Podano nieprawidłowy klucz !");
            isAppRunning = false;
        }
    }

    void operate() {

        while (isAppRunning) {
            JSONArray articlePage = controller.getPageInJSON().getJSONArray("articles");

            for (int i = 0; i < articlePage.length(); i++) {
                final Map<String, Object> stringObjectMap = articlePage.getJSONObject(i).toMap();

                System.out.println("\n PODGLĄD ARTYKUŁU: \n");
                System.out.println("strona: " + controller.getPage() + " z " + controller.getTotalPages() + "\n");
                stringObjectMap.forEach((key, value) -> System.out.println(key + ": " + value + "\n"));

                Scanner input = new Scanner(System.in);
                System.out.println("Zapis do pliku ? Wpisz 'T' lub 'N'. " +
                        "Aby wyjść ze strony wciśnij dowolny klawisz oraz enter ...");
                String inputCommand = input.next();

                if (inputCommand.equalsIgnoreCase("T")) controller.addArticlesToSet(articlePage, i);
                else if (inputCommand.equalsIgnoreCase("N")) continue;
                else break;
            }

            String info = "Aby wyjść z programu wciśnij 0, a następnie enter.";
            System.out.println("Aby wczytać kolejną stronę wpisz jej numer. " + info);

            Integer pageNum = null;
            while (pageNum == null) {
                String info2 = "Wpisz prawidłowy numer strony ! " + info;
                try {
                    Scanner input = new Scanner(System.in);
                    pageNum = input.nextInt();
                    System.out.println(pageNum);
                    if (pageNum > 0 && pageNum <= controller.getTotalPages()) {
                        controller.updatePage(pageNum);
                    } else if (pageNum == 0) {
                        isAppRunning = false;
                    } else {
                        pageNum = null;
                        System.out.println(info2);
                    }
                } catch (InputMismatchException | IOException e) {
                    pageNum = null;
                    System.out.println(info2);
                }
            }
        }

        if (controller != null) controller.saveArticlesToFile();
    }
}
