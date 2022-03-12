package io.github.danielzyla.article;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SelectedArticlesDto {

    private List<String> articleDtoList = new ArrayList<>();

    public SelectedArticlesDto() {
    }
}
