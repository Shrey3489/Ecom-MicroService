package com.ShreyEcomMicro.user.DTO;

import com.ShreyEcomMicro.user.Entity.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private AddressDto address;
}
