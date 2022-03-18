package io.github.danielzyla.article;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SelectedArticlesDto {
    private Set<Long> selectedArticleDtoIds = new HashSet<>();

    SelectedArticlesDto() {
    }
}
