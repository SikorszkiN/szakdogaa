package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WebScrapeService {

    private final WebshopRepository webshopRepository;

    public int getPrice(String url, String webshopName) {
        Webshop webshop = webshopRepository.findByName(webshopName).orElseThrow(() -> new NoEntityException("Webshop not found!, Please check webshop name!"));

        int price = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        var elements = doc.select(webshop.getPriceSelector());
        var element = elements.get(0);
        var priceString = element.text();
       /* price = Integer.parseInt(priceString.replaceAll("[a-zA-Z]*", "")
                .replaceAll("[\\\\D.]", "").replaceAll(" ", "")
                .trim());*/
        price = Integer.parseInt(priceString.replaceAll("[\\D.]",""));
        return price;
    }

}
