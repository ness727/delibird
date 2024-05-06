package com.megamaker.userservice.util;

import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseCheckUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.*;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RequestRegisterUser user);
    ResponseRegisterUser toResponseUser(RequestRegisterUser user);
    ResponseUser toResponseUser(User user);

    ResponseCheckUser toResponseCheckUser(User user);

    ResponseRegisterUser toResponseRegisterUser(User user);
}
