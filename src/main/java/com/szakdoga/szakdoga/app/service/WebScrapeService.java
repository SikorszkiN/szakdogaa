package com.szakdoga.szakdoga.app.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WebScrapeService {

    public int getPrice(String url, String selector) {
        int price = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        var elements = doc.select(selector);
        var element = elements.get(0);
        var priceString = element.text();
        price = Integer.parseInt(priceString.replaceAll("[a-zA-Z]*", "")
                .replaceAll("[\\\\D.]", "").replaceAll(" ", "")
                .trim());
        return price;
    }

}
