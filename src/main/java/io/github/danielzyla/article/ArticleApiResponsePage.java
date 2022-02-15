package io.github.danielzyla.article;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ArticleApiResponsePage {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;
}
