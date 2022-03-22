package io.github.danielzyla.article;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Controller
public class ArticlesPageController {
    private final ArticleService service;
    private final ArticlePaging articlePaging;

    ArticlesPageController(final ArticleService service, final ArticlePaging articlePaging) {
        this.service = service;
        this.articlePaging = articlePaging;
    }

    @GetMapping("/articlesPage/{country}/{category}/{pageNumber}")
    String getArticlesPageView(
            Model model,
            @PathVariable String country,
            @PathVariable String category,
            @PathVariable Integer pageNumber
    ) throws InterruptedException {
        if (!this.articlePaging.getCountry().equals(country)) {
            this.service.resetPage();
            this.articlePaging.setCountry(country);
        }
        if (!this.articlePaging.getCategory().equals(category)) {
            this.service.resetPage();
            this.articlePaging.setCategory(category);
        }
        this.articlePaging.setCurrentPage(pageNumber);
        String message = this.service.getArticlesPage(model);
        if (message != null) return message;
        model.addAttribute("selectedArticles", this.service.getSelectedArticlesDtoTotal());
        addAttributesToModel(model);
        return "articlesPage";
    }

    @PostMapping(path = "/articlesPage/{country}/{category}/{pageNumber}", params = "saveArticle")
    String saveSelectedArticles(
            Model model,
            @ModelAttribute("selectedArticles") SelectedArticlesDto fromPage
    ) throws InterruptedException {
        this.service.combineSelectedWithTotal(fromPage);
        String message = this.service.getArticlesPage(model);
        if (message != null) return message;
        addAttributesToModel(model);
        return "articlesPage";
    }

    @GetMapping("/downloadFile/articlesData")
    ResponseEntity<ByteArrayResource> downloadFile() throws IOException {
        try {
            this.service.saveSelectedArticlesToList();
            this.service.saveArticlesToFile(this.service.getArticlesToSave());
            this.service.getArticlesToSave().clear();
            return ResponseEntity.ok()
                    .contentType(MediaType.asMediaType(MediaType.valueOf("text/csv")))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=articlesData.csv")
                    .body(new ByteArrayResource(Files.readAllBytes(Paths.get("articlesData.csv"))));
        } catch (NoSuchFileException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private void addAttributesToModel(final Model model) {
        model.addAttribute("country", this.articlePaging.getCountry());
        model.addAttribute("countries", this.articlePaging.getListOfCountries());
        model.addAttribute("category", this.articlePaging.getCategory());
        model.addAttribute("categories", this.articlePaging.getListOfCategories());
        model.addAttribute("pageNumber", this.articlePaging.getCurrentPage());
        model.addAttribute("totalPages", this.articlePaging.getTotalPages());
        model.addAttribute("pageOfArticles", this.service.getDownloadedDtoListMap().get(articlePaging.getCurrentPage()));
    }

    @ExceptionHandler
    public String errorHandler(Model model, Exception e) {
        model.addAttribute("error", e);
        return "error";
    }
}