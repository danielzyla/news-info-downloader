package io.github.danielzyla.article;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@ToString @Getter
public class ArticleApiResponsePage {
    private String status;
    private int totalResults;
    private ArrayList<ArticleDto> articles;
}
