package com.szakdoga.szakdoga.app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class WebshopProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Length(max = 1000)
    @Column(nullable = false)
    private String link;
    private int price;

    @ManyToOne
    @JoinColumn(name = "webshop_id")
    private Webshop webshop;

    @JsonIgnore
    @ManyToMany(mappedBy = "webshopProducts")
    private List<Component> components = new ArrayList<>();

    public WebshopProduct(Long id, String name, @Length(max = 1000) String link, int price, Webshop webshop) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.webshop = webshop;
    }

    public WebshopProduct(Long id, String name, @Length(max = 1000) String link, int price) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
    }
}
