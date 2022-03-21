# news-info-downloader

[![version](https://img.shields.io/badge/version-1.3.2-yellow)]()

## Project overview

The app allows you to get articles from [News API](https://newsapi.org/) and download them to file.
The result is paginated and you will get it as html view at path /articlesPage/{pageNumber}.

You can see how it works on the web by clicking [here](https://news-info-downloader.herokuapp.com/).

The source of articles and the page size are set statically on the backend, but can be easily easily adjusted to the sources offered by News API.

## Technology used

Java 11 SE, Apache Maven, Spring Boot, RestTemplate client (Spring Framework), Project Lombok, Thymeleaf

## Instruction

Once you start the app at home path you will be asked to enter your apiKey to get the access.
Authentication is performed via the X-Api-Key HTTP header.
