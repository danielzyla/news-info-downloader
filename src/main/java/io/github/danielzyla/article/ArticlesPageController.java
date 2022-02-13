package io.github.danielzyla.article;

import java.io.IOException;

public class ArticlesPageController {
    private final String apiKey;
    private int page;
    private int totalPages;
    private final ArticleService service;
    private final ArticleFileHandler fileHandler;
    private final ArticleRestClient restClient;
    private ArticleApiResponsePage apiResponsePage;

    public ArticlesPageController(String apiKey) throws IOException {
        this.apiKey = apiKey;
        this.restClient = new ArticleRestClient();
        this.service = new ArticleService();
        this.fileHandler = ArticleFileHandler.getInstance();
    }

    void setTotalPages(final int totalResults, final int pageSize) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) pageSize);
    }

    public void updatePage(final int pageSize, final int page) throws IOException {
        this.page = page;
        this.apiResponsePage = this.restClient.getArticlesPage(this.apiKey, pageSize, getPage());
        setTotalPages(this.apiResponsePage.getTotalResults(), pageSize);
    }

    public void addArticleToSet(final ArticleDto articlePage) {
        service.addArticlesToSet(articlePage);
    }

    public void saveArticlesToFile() {
        fileHandler.saveArticlesToFile(service.getArticleSet());
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
