package io.github.danielzyla;

import io.github.danielzyla.controller.ArticlesPageController;
import io.github.danielzyla.file.FileHandler;
import io.github.danielzyla.service.ArticleService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class NewsDownloaderApp {

    public static void main(String[] args) throws IOException {

        boolean isAppRunning = true;

        ArticlesPageController controller = null;
        JSONObject pageInJSON = null;
        ArticleService service = null;
        FileHandler fileHandler = null;

        try {
            System.out.println("Wpisz klucz do News API :");
            Scanner inputApiKey = new Scanner(System.in);
            final String apiKey = inputApiKey.next();
            controller = new ArticlesPageController(apiKey);
            pageInJSON = controller.getPageInJSON(1);
            controller.setTotalPages(pageInJSON.getInt("totalResults"));
            service = new ArticleService();
            fileHandler = FileHandler.getInstance();
        } catch (Exception e) {
            System.out.println("Podano nieprawidłowy klucz !");
            isAppRunning = false;
        }


        while (isAppRunning) {
            JSONArray articlePage = pageInJSON.getJSONArray("articles");

            for (int i = 0; i < articlePage.length(); i++) {
                final Map<String, Object> stringObjectMap = articlePage.getJSONObject(i).toMap();

                System.out.println("\n PODGLĄD ARTYKUŁU: \n");
                System.out.println("strona: " + controller.getPage() + " z " + controller.getTotalPages() + "\n");
                stringObjectMap.forEach((key, value) -> System.out.println(key + ": " + value + "\n"));

                Scanner input = new Scanner(System.in);
                System.out.println("Zapis do pliku ? Wpisz 'T' lub 'N'. " +
                        "Aby wyjść ze strony wciśnij dowolny klawisz oraz enter ...");
                String inputCommand = input.next();

                if (inputCommand.equalsIgnoreCase("T")) service.addArticlesToSet(articlePage, i);
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
                        pageInJSON = controller.getPageInJSON(pageNum);
                        controller.setTotalPages(pageInJSON.getInt("totalResults"));
                    } else if (pageNum == 0) {
                        isAppRunning = false;
                    } else {
                        pageNum = null;
                        System.out.println(info2);
                    }
                } catch (InputMismatchException e) {
                    pageNum = null;
                    System.out.println(info2);
                }
            }
        }

        if (service != null) {
            assert fileHandler != null;
            fileHandler.saveArticlesToFile(service.getArticleSet());
        }
    }
}
