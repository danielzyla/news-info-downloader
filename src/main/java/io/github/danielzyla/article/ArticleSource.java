package io.github.danielzyla.article;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
class ArticleSource {
    private final Object id;
    private final String name;

    ArticleSource() {
        this.id = "brak danych";
        this.name = "brak danych";
    }
}
