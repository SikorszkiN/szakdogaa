package com.szakdoga.szakdoga.app.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class WebshopProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Length(max = 1000)
    private String link;
    private int price;
    private int deliveryTime;
    private boolean availability;

    public WebshopProduct() {

    }
}
