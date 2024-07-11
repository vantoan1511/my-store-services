package com.mystore.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESSES")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;

    private String city;

    private String province;

    private String district;

    private String ward;

    private String line;
}
