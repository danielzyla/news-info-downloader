package io.github.danielzyla.article;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
class ArticleIdCounter {
    private static long id = 1L;

    static long getId() {
        long idToReturn = ArticleIdCounter.id;
        ArticleIdCounter.id++;
        return idToReturn;
    }
}
