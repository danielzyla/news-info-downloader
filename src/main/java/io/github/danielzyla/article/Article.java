package io.github.danielzyla.article;

import java.util.Objects;

class Article {
    private String title;
    private String description;
    private String author;

    Article() {
        this.title  = "brak danych";
        this.description = "brak danych";
        this.author = "nieznany";
    }

    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(final String author) {
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
