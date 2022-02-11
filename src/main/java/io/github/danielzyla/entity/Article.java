package io.github.danielzyla.entity;

import java.util.Objects;

public class Article {
    private String title;
    private String description;
    private String author;

    public Article() {
        this.title  = "brak danych";
        this.description = "brak danych";
        this.author = "nieznany";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Article article = (Article) o;
        return Objects.equals(title, article.title)
                && Objects.equals(description, article.description)
                && Objects.equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author);
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
