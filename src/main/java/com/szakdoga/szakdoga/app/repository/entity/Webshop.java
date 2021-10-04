package com.szakdoga.szakdoga.app.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Length(max = 1000)
    private String link;
    private int price;
    private int deliveryTime;
    private boolean availability;

    /*@ManyToMany(mappedBy = "webshops")
    private List<Component> components;*/

}
