package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.user.UserExportDTO;
import com.example.xmlexercise.entity.user.UsersExportAndCountDTO;

public interface UserService {
    UserExportDTO getUsersWithSuccessfullySoldProducts();

    UsersExportAndCountDTO getUsersAndProducts();
}
