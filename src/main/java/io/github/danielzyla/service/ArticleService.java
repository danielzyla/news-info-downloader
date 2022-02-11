package io.github.danielzyla.service;

import io.github.danielzyla.entity.Article;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

public class ArticleService {

    private final HashSet<Article> articleSet = new HashSet<>();

    public void addArticlesToSet(final JSONArray articlePage, final int i) {
        Article article = new Article();
        JSONObject articleJSONObject = articlePage.getJSONObject(i);
        if (!articleJSONObject.isNull("title")) article.setTitle(articleJSONObject.getString("title"));
        if (!articleJSONObject.isNull("description"))
            article.setDescription(articleJSONObject.getString("description"));
        if (!articleJSONObject.isNull("author")) article.setAuthor(articleJSONObject.getString("author"));
        articleSet.add(article);
    }

    public HashSet<Article> getArticleSet() {
        return articleSet;
    }
}
