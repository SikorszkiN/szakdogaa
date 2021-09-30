package com.szakdoga.szakdoga.app.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Webshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int deliveryTime;
    private boolean availability;

    /*@ManyToMany(mappedBy = "webshops")
    private List<Component> components;*/

}
