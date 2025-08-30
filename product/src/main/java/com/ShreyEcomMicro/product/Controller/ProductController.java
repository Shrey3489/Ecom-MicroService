package com.ShreyEcomMicro.product.Controller;


import com.ShreyEcomMicro.product.Dto.ProductResponseDto;
import com.ShreyEcomMicro.product.Dto.ProductsRequestDto;
import com.ShreyEcomMicro.product.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("add")
    public ResponseEntity<ProductResponseDto> save(@RequestBody ProductsRequestDto productsRequestDto)
    {
        return new ResponseEntity<>(productService.createProduct(productsRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> update(@RequestBody ProductsRequestDto productsRequestDto, @PathVariable Long id)
    {
        return productService.updateProduct(productsRequestDto,id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ProductResponseDto>> findAll()
    {
        return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<ProductResponseDto>> findAllActivePorduct()
    {
        return new ResponseEntity<>(productService.getAllActiveProduct(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id)
    {
        ProductResponseDto loProductResponseDto = productService.getProductById(id);

        if(loProductResponseDto==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(loProductResponseDto,HttpStatus.OK);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id)
    {
        return productService.findbyId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        return productService.deleteProduct(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<ProductResponseDto>> findAByKEYWord(@RequestParam String keyword)
    {
        return new ResponseEntity<>(productService.findByKeyword(keyword), HttpStatus.OK);
    }
}
