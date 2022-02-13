# news-info-downloader

[![version](https://img.shields.io/badge/version-1.1.0-yellow)]()

## Project overview

The app allows you to get articles from [News API](https://newsapi.org/) and view them on the console.
You can choose which one should be saved to file.
The data extracted from articles in the file has the following format:
```
title:description:author
title:description:author
...
```

## Technology used

Java 11 SE, Apache Maven, RestTemplate client (Spring Framework), Project Lombok

## Instruction

Once you start the app you will be asked to enter your apiKey to get the access.
Authentication is performed via the X-Api-Key HTTP header.
The articles are displayed one by one on the current page. You can change the page by entering its number.
The file is updated when the app is terminated and it is rewritten after every run of the app.
