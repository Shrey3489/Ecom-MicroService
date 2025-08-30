package com.ShreyEcom_micro_App.Order.Dto;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
