package com.szakdoga.szakdoga.app.webscrape;

import org.springframework.stereotype.Component;

@Component
public interface WebScrape {
    int priceWebScrape(String url);
}
