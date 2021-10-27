package com.szakdoga.szakdoga.app.dto;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebshopDeliveryPrice {

    Map<Long, Integer> postaKoltseg = new HashMap<>();

    List<ComponentPriceCalculate> node = new ArrayList<>();

    boolean vegallapot = false;

    public List<WebshopDeliveryPrice> expand (List<WebshopProduct> webshopProducts, boolean vegallapot){
        List<WebshopDeliveryPrice> deliveryPrices = new ArrayList<>();
        for (WebshopProduct wp : webshopProducts){
            WebshopDeliveryPrice webshopDeliveryPrice = new WebshopDeliveryPrice();
            webshopDeliveryPrice.node.addAll(node);
            webshopDeliveryPrice.postaKoltseg.putAll(postaKoltseg);
            webshopDeliveryPrice.vegallapot = vegallapot;
           if(!postaKoltseg.containsKey(wp.getWebshop().getId())){
               webshopDeliveryPrice.postaKoltseg.put(wp.getWebshop().getId(), wp.getWebshop().getDeliveryPrice());
           }
           webshopDeliveryPrice.node.add(new ComponentPriceCalculate(wp.getPrice(), wp.getId()));
           deliveryPrices.add(webshopDeliveryPrice);
        }
        return deliveryPrices;
    }

    public Integer calculatePrice(){
        Integer price = 0;
        for(ComponentPriceCalculate nodePrice : node){
            price+=nodePrice.getComponentPrice();
        }
        for(Integer pk : postaKoltseg.values()){
            price+=pk;
        }
        return price;
    }


}
