package io.github.danielzyla.article;

import lombok.Getter;

@Getter
class ArticleSource {
    private final Object id;
    private final String name;

    ArticleSource() {
        this.id = "brak danych";
        this.name = "brak danych";
    }
}
