package com.szakdoga.szakdoga.app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "component_webshopProduct",
            joinColumns = {@JoinColumn(name = "component_id")},
            inverseJoinColumns = {@JoinColumn(name="webshop_Product_id")})
    private List<WebshopProduct> webshopProducts = new ArrayList<>();

    @ManyToMany(mappedBy = "components")
    private List<Product> products = new ArrayList<>();

}
