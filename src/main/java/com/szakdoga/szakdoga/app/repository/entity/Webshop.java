package com.szakdoga.szakdoga.app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @OneToMany(orphanRemoval = true, mappedBy = "webshop")
    private List<WebshopProduct> webshopProducts = new ArrayList<>();

    public Webshop(Long id, String name, String priceSelector, Integer deliveryPrice) {
        this.id = id;
        this.name = name;
        this.priceSelector = priceSelector;
        this.deliveryPrice = deliveryPrice;
    }


}
