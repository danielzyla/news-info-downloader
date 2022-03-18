# news-info-downloader

[![version](https://img.shields.io/badge/version-1.3.0-yellow)]()

## Project overview

The app allows you to get articles from [News API](https://newsapi.org/) and download them to file.
The result is paginated and you will get it as html view at http://localhost:8080/articlesPage/{pageNumber}.

## Technology used

Java 11 SE, Apache Maven, Spring Boot, RestTemplate client (Spring Framework), Project Lombok, Thymeleaf

## Instruction

Once you start the app at home path http://localhost:8080/ you will be asked to enter your apiKey to get the access.
Authentication is performed via the X-Api-Key HTTP header.
