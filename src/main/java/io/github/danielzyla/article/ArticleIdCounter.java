package io.github.danielzyla.article;

class ArticleIdCounter {
    private static long id = 1L;

    static long getId() {
        long idToReturn = ArticleIdCounter.id;
        ArticleIdCounter.id++;
        return idToReturn;
    }
}
