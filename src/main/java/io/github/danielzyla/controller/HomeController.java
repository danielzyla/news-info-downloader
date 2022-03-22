package io.github.danielzyla.controller;

import io.github.danielzyla.article.ArticlePaging;
import io.github.danielzyla.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
class HomeController {

    private final ArticleService service;
    private final ArticlePaging articlePaging;

    HomeController(final ArticleService service, final ArticlePaging articlePaging) {
        this.service = service;
        this.articlePaging = articlePaging;
    }

    @GetMapping("/")
    String getHomePage(Model model, @RequestParam(name = "message", defaultValue = "") String message) {
        model.addAttribute("message", URLDecoder.decode(message, StandardCharsets.UTF_8));
        return "home";
    }

    @PostMapping("/")
    String enterApiKey(@RequestParam(name = "inputApiKey", defaultValue = "") String inputApiKey) {
        try {
            service.setApiKey(inputApiKey);
            service.updatePage();
            return "redirect:/articlesPage/" + articlePaging.getCountry() + "/" + articlePaging.getCategory() + "/" + articlePaging.getCurrentPage();
        } catch (IllegalArgumentException | InterruptedException e) {
            e.printStackTrace();
            return "home";
        }
    }
}
