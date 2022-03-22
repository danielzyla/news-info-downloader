package io.github.danielzyla.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Component
@SessionScope
public class ArticlePaging {
    public final static int PAGE_SIZE = 4;
    private int currentPage;
    private int totalPages;
    private String country;

    ArticlePaging() {
        this.currentPage = 1;
        this.country = "pl";
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

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
