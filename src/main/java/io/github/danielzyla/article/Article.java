package io.github.danielzyla.article;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
class Article {
    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private ZonedDateTime publishedAt;
    private String content;
}
