package io.github.danielzyla.article;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
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

    @GetMapping("/articlesPage/{pageNumber}")
    String getArticlesPageView(Model model, @PathVariable Integer pageNumber) throws InterruptedException {
        this.articlePaging.setCurrentPage(pageNumber);
        String message = this.service.getArticlesPage(model);
        if (message != null) return message;
        model.addAttribute("selectedArticles", this.service.getSelectedArticlesDtoTotal());
        addAttributesToModel(model);
        return "articlesPage";
    }

    @PostMapping(path = "/articlesPage/{pageNumber}", params = "saveArticle")
    String saveSelectedArticles(
            Model model,
            @PathVariable Integer pageNumber,
            @ModelAttribute("selectedArticles") SelectedArticlesDto fromPage
    ) throws InterruptedException {
        this.articlePaging.setCurrentPage(pageNumber);
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
                    .contentType(MediaType.asMediaType(MimeType.valueOf("text/csv")))
                    .body(new ByteArrayResource(Files.readAllBytes(Paths.get("articlesData.csv"))));
        } catch (NoSuchFileException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private void addAttributesToModel(final Model model) {
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