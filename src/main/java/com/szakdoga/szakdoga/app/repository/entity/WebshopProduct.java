package com.szakdoga.szakdoga.app.repository.entity;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
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
    private int deliveryTime;
    private boolean availability;

    public WebshopProduct() {

    }
}
