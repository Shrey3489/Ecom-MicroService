package com.ShreyEcom_micro_App.GateWay;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController
{
    @GetMapping("/fallback/products")
    public ResponseEntity<String> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Product Service is currently unavailable. Please try again later.");
    }
}
