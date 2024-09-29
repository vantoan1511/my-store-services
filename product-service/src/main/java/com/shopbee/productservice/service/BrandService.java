package com.shopbee.productservice.service;

import com.shopbee.productservice.entity.Brand;
import com.shopbee.productservice.repository.BrandRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BrandService {

    BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand getBySlug(String slug) {
        return brandRepository.findBySlug(slug)
                .orElseThrow();
    }

}
