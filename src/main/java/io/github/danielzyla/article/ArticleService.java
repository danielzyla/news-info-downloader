package io.github.danielzyla.article;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

class ArticleService {

    private final ArticleFileHandler fileHandler;

    ArticleService() throws IOException {
        this.fileHandler = ArticleFileHandler.getInstance();
    }

    List<ArticleReadDto> getArticleDtoListFromPage(final ArticleApiResponsePage apiResponsePage) {
        return apiResponsePage.getArticles().stream()
                .map(ArticleReadDto::new)
                .collect(Collectors.toList());
    }

    void saveArticlesToFile(final HashSet<ArticleWriteDto> articlesToSave) {
        fileHandler.saveArticlesToFile(articlesToSave);
    }
}
