package io.github.danielzyla.article;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

@Controller
public class ArticlesPageController {
    private String apiKey;
    private int page;
    private final static int PAGE_SIZE = 4;
    private int totalPages;
    private final ArticleService service;
    private final ArticleRestClient restClient;
    private ArticleApiResponsePage apiResponsePage;

    public ArticlesPageController() throws IOException {
        this.restClient = new ArticleRestClient();
        this.service = new ArticleService();
    }

    void setTotalPages(final int totalResults, final int pageSize) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) pageSize);
    }

    public void updatePage(final int pageSize, final int page) throws InterruptedException {
        this.page = page;
        this.apiResponsePage = this.restClient.getArticlesPage(this.apiKey, pageSize, getPage());
        if (this.apiResponsePage != null) {
            setTotalPages(this.apiResponsePage.getTotalResults(), pageSize);
        }
    }

    @GetMapping("/articlesPage/{pageNumber}")
    public String getArticlesPageView(Model model, @PathVariable Integer pageNumber) throws InterruptedException {
        if (getApiResponsePage() != null) {
            if (this.page != pageNumber) {
                this.updatePage(PAGE_SIZE, pageNumber);
            }
            model.addAttribute("selectedArticles", new SelectedArticlesDto());
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("totalPages", getTotalPages());
            model.addAttribute("pageOfArticles", getArticleDtoListFromPage());
            return "articlesPage";
        } else {
            final String message = URLEncoder.encode("invalid API key given", StandardCharsets.UTF_8);
            model.addAttribute("message", message);
            return "redirect:/?message=" + message;
        }
    }


    @PostMapping(path = "/articlesPage/{pageNumber}", params = "saveArticle")
    public String saveSelectedArticles(Model model, @PathVariable Integer pageNumber, @ModelAttribute("selectedArticles") SelectedArticlesDto selectedArticlesDto) throws InterruptedException, JsonProcessingException {
        HashSet<ArticleWriteDto> articlesToSave = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < selectedArticlesDto.getArticleDtoList().size(); i++) {
            ArticleWriteDto articleWriteDto = objectMapper.readValue(selectedArticlesDto.getArticleDtoList().get(i), ArticleWriteDto.class);
            articlesToSave.add(articleWriteDto);
        }
        saveArticlesToFile(articlesToSave);

        if (getApiResponsePage() != null) {
            if (this.page != pageNumber) {
                this.updatePage(PAGE_SIZE, pageNumber);
            }
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("totalPages", getTotalPages());
            model.addAttribute("pageOfArticles", getArticleDtoListFromPage());
            return "articlesPage";
        }
        return "/articlesPage/" + pageNumber;
    }

    public List<ArticleReadDto> getArticleDtoListFromPage() {
        return service.getArticleDtoListFromPage(getApiResponsePage());
    }

    public void saveArticlesToFile(final HashSet<ArticleWriteDto> articlesToSave) {
        service.saveArticlesToFile(articlesToSave);
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArticleApiResponsePage getApiResponsePage() {
        return apiResponsePage;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    @ExceptionHandler
    public String errorHandler(Model model, Exception e) {
        model.addAttribute("error", e);
        return "error";
    }
}