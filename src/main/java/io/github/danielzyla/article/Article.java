package io.github.danielzyla.article;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Article {
    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private ZonedDateTime publishedAt;
    private String content;
}
