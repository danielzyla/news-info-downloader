package io.github.danielzyla.article;

import org.springframework.stereotype.Component;

@Component
public class ArticlePaging {
    public final static int PAGE_SIZE = 4;
    private int currentPage;
    private int totalPages;

    ArticlePaging() {
    }

    public void setTotalPages(final int totalResults) {
        this.totalPages = (int) Math.ceil((double) totalResults / (double) PAGE_SIZE);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }
}
