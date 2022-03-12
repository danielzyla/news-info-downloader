package io.github.danielzyla.article;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleWriteDto {
    private String author;
    private String title;
    private String description;
}
