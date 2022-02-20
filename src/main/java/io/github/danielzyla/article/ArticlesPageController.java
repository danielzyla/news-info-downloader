package io.github.danielzyla.article;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class ArticlesPageController {
    private final String apiKey;
    private int page;
    private int totalPages;
    private final ArticleService service;
    private final ArticleRestClient restClient;
    private ArticleApiResponsePage apiResponsePage;

    public ArticlesPageController(String apiKey) throws IOException {
        this.apiKey = apiKey;
        this.restClient = new ArticleRestClient();
        this.service = new ArticleService();
    }

    void setTotalPages(final int totalResults, final int pageSize) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) pageSize);
    }

    public void updatePage(final int pageSize, final int page) throws IOException, InterruptedException {
        this.page = page;
        this.apiResponsePage = this.restClient.getArticlesPage(this.apiKey, pageSize, getPage());
        setTotalPages(this.apiResponsePage.getTotalResults(), pageSize);
    }

    public List<ArticleDto> getArticleDtoListFromPage() {
        return service.getArticleDtoListFromPage(getApiResponsePage());
    }

    public void saveArticlesToFile(final HashSet<ArticleDto> articlesToSave) {
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
}
