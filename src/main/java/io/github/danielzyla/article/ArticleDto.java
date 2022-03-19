package io.github.danielzyla.article;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static io.github.danielzyla.utils.DateTimeConverter.getFormattedPublishedAt;

@Getter
@Setter
public class ArticleDto {
    private long id;
    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
    private final static String REG_EX = "(\n|\r|\r\n)";

    public ArticleDto(Article article) {
        this.id = ArticleIdCounter.getId();
        this.source = article.getSource() != null ? article.getSource() : new ArticleSource();
        this.author = article.getAuthor() != null ? article.getAuthor() : "brak danych";
        this.title = article.getTitle() != null ? article.getTitle().replaceAll(REG_EX, "") : "brak danych";
        this.description = article.getDescription() != null ? article.getDescription().replaceAll(REG_EX, "") : "brak danych";
        this.url = article.getUrl() != null ? article.getUrl() : "brak danych";
        this.urlToImage = article.getUrlToImage() != null ? article.getUrlToImage() : "brak danych";
        this.publishedAt = article.getPublishedAt() != null ? getFormattedPublishedAt(article.getPublishedAt()) : "brak danych";
        this.content = article.getContent() != null ? article.getContent().replaceAll(REG_EX, "") : "brak danych";
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
