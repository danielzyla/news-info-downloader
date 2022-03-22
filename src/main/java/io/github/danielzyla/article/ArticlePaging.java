package io.github.danielzyla.article;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

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

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public List<String> getListOfCountries() {
        return List.of("pl", "gb", "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co",
                "cu", "cz", "de", "eg", "fr", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp",
                "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pt", "ro", "rs",
                "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za");
    }
}
