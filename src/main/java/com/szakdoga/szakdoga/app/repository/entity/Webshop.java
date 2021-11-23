package com.szakdoga.szakdoga.app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Webshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String priceSelector;
    @Column(nullable = false)
    private Integer deliveryPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "webshop")
    private List<WebshopProduct> webshopProducts = new ArrayList<>();

    public Webshop(Long id, String name, String priceSelector, Integer deliveryPrice) {
        this.id = id;
        this.name = name;
        this.priceSelector = priceSelector;
        this.deliveryPrice = deliveryPrice;
    }




}
