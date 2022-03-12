package io.github.danielzyla.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.ZonedDateTime;
import java.util.Objects;

import static io.github.danielzyla.utils.DateTimeConverter.getFormattedPublishedAt;

@Getter
@Setter
public class ArticleReadDto {
    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public ArticleReadDto(Article article) {
        this.source = article.getSource() != null ? article.getSource() : new ArticleSource();
        this.author = article.getAuthor() != null ? article.getAuthor().replaceAll("\"", "'") : "brak danych";
        this.title = article.getTitle() != null ? article.getTitle().replaceAll("\"", "'") : "brak danych";
        this.description = article.getDescription() != null ? article.getDescription().replaceAll("\"", "'") : "brak danych";
        this.url = article.getUrl() != null ? article.getUrl() : "brak danych";
        this.urlToImage = article.getUrlToImage() != null ? article.getUrlToImage() : "brak danych";
        this.publishedAt = article.getPublishedAt() != null ? getFormattedPublishedAt(article.getPublishedAt()) : ZonedDateTime.now().toString();
        this.content = article.getContent() != null ? article.getContent().replaceAll("\"", "'") : "brak danych";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleReadDto articleReadDto = (ArticleReadDto) o;
        return Objects.equals(title, articleReadDto.title)
                && Objects.equals(description, articleReadDto.description)
                && Objects.equals(author, articleReadDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author);
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
