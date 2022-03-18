package io.github.danielzyla.article;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class ArticleApiResponsePage {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleApiResponsePage that = (ArticleApiResponsePage) o;
        return Objects.equals(articles, that.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articles);
    }
}
