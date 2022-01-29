package com.learning.productservice.service;

import com.learning.productservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv", 4000);
        ProductDto p2 = new ProductDto("slr-camera", 40000);
        ProductDto p3 = new ProductDto("iphone", 50000);
        ProductDto p4 = new ProductDto("head-phone", 5000);
        Flux.just(p1, p2, p3, p4)
                .flatMap(p ->this.service.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);

    }
}
