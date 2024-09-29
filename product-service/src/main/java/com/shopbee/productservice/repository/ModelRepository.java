package com.shopbee.productservice.repository;

import com.shopbee.productservice.entity.Model;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ModelRepository implements PanacheRepository<Model> {

    public Optional<Model> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }
}