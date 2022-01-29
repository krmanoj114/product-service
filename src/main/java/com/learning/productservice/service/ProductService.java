package com.learning.productservice.service;

import com.learning.productservice.dto.ProductDto;
import com.learning.productservice.repository.ProductRepository;
import com.learning.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAll(){
       return this.productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return this.productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(dto -> EntityDtoUtil.toEntity(dto))
                .flatMap(entity ->this.productRepository.insert(entity))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return this.productRepository.findById(id)
                .flatMap(p ->productDtoMono
                                        .map(dto ->EntityDtoUtil.toEntity(dto))
                                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);

    }

    public Mono<Void> deleteProduct(String id){

        return this.productRepository.deleteById(id);
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max){
        return this.productRepository.findByPriceBetween(Range.closed(min,max))
                .map(EntityDtoUtil::toDto);
    }


}
