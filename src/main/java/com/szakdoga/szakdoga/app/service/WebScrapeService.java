package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WebScrapeService {

    private final WebshopRepository webshopRepository;

    public int getPrice(String url, String webshopName) {
        List<String> webshopNames = new ArrayList<>();
        for(var webshop : webshopRepository.findAll()){
            webshopNames.add(webshop.getName());
        }
        Webshop webshop = webshopRepository.findByName(webshopName).orElseThrow(() -> new NoEntityException("Webshop not found!, Please choose one of these webshops" + webshopNames));

        int price = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        var elements = Objects.requireNonNull(doc).select(webshop.getPriceSelector());
        var element = elements.get(0);
        var priceString = element.text();
        price = Integer.parseInt(priceString.replaceAll("[\\D.]",""));
        return price;
    }

}
