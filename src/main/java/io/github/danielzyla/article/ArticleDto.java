package io.github.danielzyla.article;

import lombok.Getter;

import java.util.Date;

@Getter
public class ArticleDto {
    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;
    private String content;

    @Override
    public String toString() {
        return "źródło='" + source.getName() + "'\n" +
                "autor='" + author + "'\n" +
                "tytuł='" + title + "'\n" +
                "opis='" + description + "'\n" +
                "link='" + url + "'\n" +
                "link do obrazka='" + urlToImage + "'\n" +
                "opublikowano='" + publishedAt + "'\n" +
                "zawartość='" + content + "'";
    }
}
