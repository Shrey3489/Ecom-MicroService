package com.ShreyEcomMicro.product.Service;


import com.ShreyEcomMicro.product.Dto.ProductResponseDto;
import com.ShreyEcomMicro.product.Dto.ProductsRequestDto;
import com.ShreyEcomMicro.product.Entity.Product;
import com.ShreyEcomMicro.product.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductsRequestDto productsRequestDto) {
        Product product = new Product();
        updateProductfromRequest(product,productsRequestDto);
        Product savedProduct = productRepository.save(product);
        return mapProctuctToResponse(product);
    }

    private ProductResponseDto mapProctuctToResponse(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setQuantity(product.getQuantity());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setActive(product.getActive());
        return productResponseDto;
    }

    private void updateProductfromRequest(Product product, ProductsRequestDto productsRequestDto) {
        product.setName(productsRequestDto.getName());
        product.setPrice(productsRequestDto.getPrice());
        product.setDescription(productsRequestDto.getDescription());
        product.setCategory(productsRequestDto.getCategory());
        product.setQuantity(productsRequestDto.getQuantity());
        product.setImageUrl(productsRequestDto.getImageUrl());
    }

    public Optional<ProductResponseDto> updateProduct(ProductsRequestDto productsRequestDto, Long id) {
        return productRepository.findById(id)
                .map(existing ->
                {
                    updateProductfromRequest(existing,productsRequestDto);
                    Product savedProduct = productRepository.save(existing);
                    return mapProctuctToResponse(savedProduct);
                });
    }

    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map(this::mapProctuctToResponse).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getAllActiveProduct() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapProctuctToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponseDto> findbyId(Long id) {
        return productRepository.findById(id).map(this::mapProctuctToResponse);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                        .map(product1 ->
                        {
                            product1.setActive(false);
                            productRepository.save(product1);
                            return true;
                        }).orElse(false);

    }

    public List<ProductResponseDto> findByKeyword(String keyword)
    {
        return productRepository.findByKeyWord(keyword)
                .stream().map(this::mapProctuctToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id)
    {
        return productRepository.findByIdAndActiveTrue(id).map(this::mapProctuctToResponse).orElse(null);
    }
}
