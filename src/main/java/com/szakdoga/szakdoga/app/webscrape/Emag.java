package com.szakdoga.szakdoga.app.webscrape;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Emag implements WebScrape {


    @Override
    public int priceWebScrape(String url) {
        int price = 0;
        try {
            var doc = Jsoup.connect(url).get();
            var elements = doc.select("nav .product-new-price");
            var element = elements.get(0);
            var priceString = element.text();
           price = Integer.parseInt(priceString.replaceAll("[a-zA-Z]*", "")
                    .replaceAll("[\\\\D.]", "")
                    .trim());
        }catch (IOException e){
            log.error(e.getMessage());
        }
        return price;
    }
}
