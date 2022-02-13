package io.github.danielzyla.article;

import io.github.danielzyla.article.Article;
import io.github.danielzyla.article.ArticleService;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleServiceTest {

    String articlesStub = "[{\"publishedAt\":\"2022-02-11T21:05:00Z\",\"author\":\"Krzysztof Kolany\",\"urlToImage\":\"https://galeria.bankier.pl/p/5/8/d437464930930e-945-560-0-95-1739-1043.jpg\",\"description\":\"Drożało złoto, a polski złoty wydatnie osłabił się wobec euro i dolara. Inwestorów niepokoi polityka Rezerwy Federalnej oraz spekulacje o rosyjskiej inwazji na Ukrainę\",\"source\":{\"name\":\"Bankier.pl\",\"id\":null},\"title\":\"Fed i Ukraina wzbudzają niepokój. Spadki na Wall Street. Złoto w górę, złoty ostro w dół - Bankier.pl\",\"url\":\"https://www.bankier.pl/wiadomosc/Spadki-na-Wall-Street-Zloto-w-gore-zloty-ostro-w-dol-8276691.html\",\"content\":\"fot. LUCAS JACKSON Reuters Pitkowa sesja na nowojorskich giedach zakoczya sidotkliwymi spadkami gównych indeksów. Inwestorów niepokoi polityka Rezerwy Federalnej oraz spekulacje o rosyjskiej i… [+2897 chars]\"},{\"publishedAt\":\"2022-02-11T20:23:00Z\",\"author\":null,\"urlToImage\":\"https://i.iplsc.com/-/000EIH294EM06RFT-C429.jpg\",\"description\":\"Kliknij i zobacz więcej.\",\"source\":{\"name\":\"Interia.pl\",\"id\":null},\"title\":\"Oto mroczny diament z kosmosu. Ma ponad 2,6 mld lat - Interia\",\"url\":\"https://geekweek.interia.pl/lifestyle/news-oto-mroczny-diament-z-kosmosu-ma-ponad-2-6-mld-lat,nId,5826771\",\"content\":\"Ten niezwyky oszlifowany czarny diament zosta wystawiony na aukcj wLondynie i natychmiast sprzeda si za ponad 4 miliony dolarów (17 milionów zotych). Anonimowy kupiec zapaci za niego jedn z kryptowa… [+1507 chars]\"},{\"publishedAt\":\"2022-02-11T19:58:53Z\",\"author\":\"TOS\",\"urlToImage\":\"https://v.wpimg.pl/OTEwODc0YDUkFTl3TEttIGdNbS0KEmN2MFV1Zkx_YGZ2R388UgV8YGoRJigGQiokKlkoNhZALiM1WT8oTFE_PWoBfmsHWTwkKRY2awZdLTEhWHolVlZ6NSZAYiIFAnZ5cRJ2fU4IfGEjWix1UQN8bCETdiZQU20p\",\"description\":\"Rządowa tarcza antyinflacyjna mocno obniżyła ceny paliw w Polsce, jednak wpływ na nie mają także czynniki niezależne od rządu. Dlatego paliwa znowu drożeją. Za benzynę i olej napędowy musimy teraz płacić\",\"source\":{\"name\":\"Finanse.wp.pl\",\"id\":null},\"title\":\"Ceny paliw rosną. Gdyby nie tarcza, przebiłyby psychologiczną granicę - Wirtualna Polska\",\"url\":\"https://finanse.wp.pl/ceny-paliw-rosna-gdyby-nie-tarcza-antyinflacyjna-przebilyby-granice-szesciu-zlotych-6736315807587136a\",\"content\":\"Rzdowa tarcza antyinflacyjna mocno obniya ceny paliw w Polsce, jednak wpyw na nie maj take czynniki niezalene od rzdu. Dlatego paliwa znowu droej. Za benzyn i olej napdowy musimy teraz paci 9-10 gros… [+4418 chars]\"},{\"publishedAt\":\"2022-02-11T19:48:40Z\",\"author\":\"Business Insider Polska\",\"urlToImage\":\"https://ocdn.eu/pulscms-transforms/1/ekrk9kpTURBXy82ZGJlNjdjYTgwYjRkZGFhMjU4NTE0ZjI5MzhmNmM3NC5qcGeSlQMAIM0EAM0CQJMFzQSwzQJ2gaEwAQ\",\"description\":\"Nowe doniesienia o możliwym ataku Rosji na Ukrainę spowodowały gwałtowny wzrost cen ropy do poziomów z 2014 r. i skłoniły amerykańskich inwestorów do sprzedaży akcji.\",\"source\":{\"name\":\"Businessinsider.com.pl\",\"id\":null},\"title\":\"Inwestorzy przestraszyli się wojny. Spadki na Wall Street - BusinessInsider\",\"url\":\"https://businessinsider.com.pl/wiadomosci/inwestorzy-przestraszyli-sie-wojny-spadki-na-wall-street/3pwhrhf\",\"content\":\"Na Wall Street nie dziao si zbyt wiele w pitek, dopóki nie pojawiy si nowe doniesienia dotyczce moliwego ataku Rosji na Ukrain. Sprawiy one, e po poudniu czasu nowojorskiego inwestorzy zaczli wyprzed… [+1104 chars]\"}]";

    @Test
    public void addArticlesToSet_shouldAddOneArticleToArticleSetAfterEveryInvocation() {

        //given
        JSONArray jsonArray = new JSONArray(articlesStub);
        ArticleService service = new ArticleService();
        String[] authors = {"Krzysztof Kolany", null, "TOS", "Business Insider Polska"};

        //when
        service.addArticlesToSet(jsonArray, 0);
        service.addArticlesToSet(jsonArray, 1);
        service.addArticlesToSet(jsonArray, 2);
        service.addArticlesToSet(jsonArray, 3);

        //then
        assertEquals(service.getArticleSet().size(), 4, "addArticlesToSet() invoked 4 times");
        assertThat(service.getArticleSet().stream().iterator().next().getAuthor(), isIn(authors));
    }
}