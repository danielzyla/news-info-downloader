package io.github.danielzyla.article;

import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
public class ArticleDto {
    private final ArticleSource source;
    private final String author;
    private final String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final ZonedDateTime publishedAt;
    private final String content;

    public ArticleDto(Article article) {
        this.source = article.getSource() != null ? article.getSource() : new ArticleSource();
        this.author = article.getAuthor() != null ? article.getAuthor() : "brak danych";
        this.title = article.getTitle() != null ? article.getTitle() : "brak danych";
        this.description = article.getDescription() != null ? article.getDescription() : "brak danych";
        this.url = article.getUrl() != null ? article.getUrl() : "brak danych";
        this.urlToImage = article.getUrlToImage() != null ? article.getUrlToImage() : "brak danych";
        this.publishedAt = article.getPublishedAt() != null ? article.getPublishedAt().withZoneSameInstant(ZoneId.systemDefault()) : ZonedDateTime.now();
        this.content = article.getContent() != null ? article.getContent() : "brak danych";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleDto articleDto = (ArticleDto) o;
        return Objects.equals(title, articleDto.title)
                && Objects.equals(description, articleDto.description)
                && Objects.equals(author, articleDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author);
    }
}
