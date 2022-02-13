package io.github.danielzyla.article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

class ArticleService {

    private final HashSet<Article> articleSet = new HashSet<>();

    void addArticlesToSet(final JSONArray articlePage, final int i) {
        Article article = new Article();
        JSONObject articleJSONObject = articlePage.getJSONObject(i);
        if (!articleJSONObject.isNull("title")) article.setTitle(articleJSONObject.getString("title"));
        if (!articleJSONObject.isNull("description"))
            article.setDescription(articleJSONObject.getString("description"));
        if (!articleJSONObject.isNull("author")) article.setAuthor(articleJSONObject.getString("author"));
        articleSet.add(article);
    }

    HashSet<Article> getArticleSet() {
        return articleSet;
    }
}
