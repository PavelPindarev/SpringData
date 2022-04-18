package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.product.Product;
import com.example.xmlexercise.entity.user.User;
import com.example.xmlexercise.entity.user.UserAttributesDTO;
import com.example.xmlexercise.entity.user.UserExportDTO;
import com.example.xmlexercise.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        mapper = new ModelMapper();
    }


    @Override
    @Transactional
    public UserExportDTO getUsersWithSuccessfullySoldProducts() {
        List<User> users = this.userRepository.findUsersWithSoldProductsAndProductsWithBuyer();

        List<UserAttributesDTO> userAttributesDTOList = users
                .stream()
                .map(user -> mapper.map(user, UserAttributesDTO.class))
                .collect(Collectors.toList());

        return new UserExportDTO(userAttributesDTOList);
    }
}
