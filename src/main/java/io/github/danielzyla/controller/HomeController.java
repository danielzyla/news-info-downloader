package io.github.danielzyla.controller;

import io.github.danielzyla.NewsDownloaderApp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
class HomeController {

    private final NewsDownloaderApp newsDownloaderApp;

    HomeController(final NewsDownloaderApp newsDownloaderApp) {
        this.newsDownloaderApp = newsDownloaderApp;
    }

    @GetMapping("/")
    String getHomePage(Model model, @RequestParam(name = "message", defaultValue = "") String message) {
        model.addAttribute("message", URLDecoder.decode(message, StandardCharsets.UTF_8));
        return "home";
    }

    @PostMapping("/")
    String enterApiKey(
            Model model,
            @RequestParam(name = "inputApiKey", defaultValue = "") String inputApiKey
    ) {
        try {
            newsDownloaderApp.start(inputApiKey);
            return "redirect:/articlesPage";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "home";
        }
    }
}
