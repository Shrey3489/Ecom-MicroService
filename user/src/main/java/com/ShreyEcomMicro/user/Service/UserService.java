package com.ShreyEcomMicro.user.Service;


import com.ShreyEcomMicro.user.DTO.AddressDto;
import com.ShreyEcomMicro.user.DTO.UserRequestDto;
import com.ShreyEcomMicro.user.DTO.UserResponse;
import com.ShreyEcomMicro.user.Entity.Address;
import com.ShreyEcomMicro.user.Entity.User;
import com.ShreyEcomMicro.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getUserList() {
        return userRepository.findAll().stream()
                .map(this::MapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse setUser(UserRequestDto userRequestDto) {
        User userEntity = new User();
        UpdateUserFromRequest(userEntity,userRequestDto);
        userEntity = userRepository.save(userEntity);
        return MapToUserResponse(userEntity);
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(this::MapToUserResponse).orElse(null);
    }

    public boolean updateUser(UserRequestDto userRequestDto, Long id) {
       return userRepository.findById(id).map(
               existuser ->
               {
                   UpdateUserFromRequest(existuser,userRequestDto);
                   userRepository.save(existuser);
                   return true;
               }).orElse(false);
    }

    private void UpdateUserFromRequest(User userEntity, UserRequestDto userRequest)
    {
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());
        if(userRequest.getAddress()!=null)
        {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            userEntity.setAddress(address);

        }
    }

    private UserResponse MapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setId(user.getId().toString());
        userResponse.setRole(user.getUserRole());
        if(user.getAddress() != null)
        {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDto);
        }

        return userResponse;
    }
}
