package com.shopbee.productservice.repository;

import com.shopbee.productservice.entity.Brand;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class BrandRepository implements PanacheRepository<Brand> {

    public Optional<Brand> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }
}