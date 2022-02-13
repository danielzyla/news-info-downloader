package io.github.danielzyla.article;

import java.util.HashSet;

class ArticleService {

    private final HashSet<Article> articleSet = new HashSet<>();

    void addArticlesToSet(final ArticleDto articleDto) {
        final Article articleToSave = dtoToArticle(articleDto);
        articleSet.add(articleToSave);
    }

    Article dtoToArticle(final ArticleDto articleDto) {
        Article article = new Article();
        article.setDescription(articleDto.getDescription());
        article.setTitle(articleDto.getTitle());
        article.setAuthor(articleDto.getAuthor());
        return article;
    }

    HashSet<Article> getArticleSet() {
        return articleSet;
    }
}
