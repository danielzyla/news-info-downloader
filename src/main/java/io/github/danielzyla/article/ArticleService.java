package io.github.danielzyla.article;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.annotation.SessionScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@SessionScope
public class ArticleService {

    private final ArticleFileHandler fileHandler;
    private final ArticleRestClient restClient;
    private final ArticlePaging articlePaging;
    private String apiKey;
    private ArticleApiResponsePage apiResponsePageCurrent;
    private final HashMap<Integer, ArticleApiResponsePage> apiResponsePageMap = new HashMap<>();
    private final HashMap<Integer, List<ArticleDto>> downloadedDtoListMap = new HashMap<>();
    private final SelectedArticlesDto selectedArticlesDtoTotal = new SelectedArticlesDto();
    private final HashSet<ArticleDto> articlesToSave = new HashSet<>();

    ArticleService(final ArticleFileHandler fileHandler, final ArticleRestClient restClient, final ArticlePaging articlePaging) {
        this.fileHandler = fileHandler;
        this.restClient = restClient;
        this.articlePaging = articlePaging;
    }

    public Optional<ArticleApiResponsePage> updatePage() throws InterruptedException {
        return Optional.ofNullable(this.restClient.getArticlesPage(getApiKey(), articlePaging.getCurrentPage()));
    }

    String getArticlesPage(final Model model) throws InterruptedException {
        if (getApiResponsePageMap().containsKey(articlePaging.getCurrentPage())) {
            this.apiResponsePageCurrent = getApiResponsePageMap().get(this.articlePaging.getCurrentPage());
        } else {
            final Optional<ArticleApiResponsePage> responsePageOptional = this.updatePage();
            if (responsePageOptional.isPresent()) {
                this.apiResponsePageCurrent = responsePageOptional.get();
                getApiResponsePageMap().put(articlePaging.getCurrentPage(), getApiResponsePageCurrent());
                getDownloadedDtoListMap().put(articlePaging.getCurrentPage(), this.getArticleDtoListFromPage(getApiResponsePageCurrent()));
            } else {
                final String message = URLEncoder.encode("nieprawidłowy klucz lub brak odpowiedzi z serwera API", StandardCharsets.UTF_8);
                model.addAttribute("message", message);
                return "redirect:/?message=" + message;
            }
        }
        this.articlePaging.setTotalPages(getApiResponsePageCurrent().getTotalResults());
        return null;
    }

    List<ArticleDto> getArticleDtoListFromPage(final ArticleApiResponsePage apiResponsePage) {
        return apiResponsePage.getArticles().stream()
                .map(ArticleDto::new)
                .collect(Collectors.toList());
    }

    void combineSelectedWithTotal(final SelectedArticlesDto fromPage) {
        final List<ArticleDto> articleDtos = getDownloadedDtoListMap().get(articlePaging.getCurrentPage());
        for (ArticleDto article : articleDtos) {
            final long id = article.getId();
            if (fromPage.getSelectedArticleDtoIds().contains(id)) {
                getSelectedArticlesDtoTotal().getSelectedArticleDtoIds().add(id);
            } else {
                getSelectedArticlesDtoTotal().getSelectedArticleDtoIds().remove(id);
            }
        }
    }

    void saveSelectedArticlesToList() {
        for (Map.Entry<Integer, List<ArticleDto>> entry : getDownloadedDtoListMap().entrySet()) {
            for (Long id : getSelectedArticlesDtoTotal().getSelectedArticleDtoIds()) {
                for (ArticleDto a : entry.getValue()) {
                    if (id == a.getId()) {
                        getArticlesToSave().add(a);
                    }
                }
            }
        }
    }

    void saveArticlesToFile(final HashSet<ArticleDto> articlesToSave) {
        fileHandler.saveArticlesToFile(articlesToSave);
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }
}
