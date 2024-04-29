package com.megamaker.userservice.util;

import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseCheckUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    User requestRegisterUserToUser(RequestRegisterUser user);
    ResponseRegisterUser requestRegisterUserToResponseUser(RequestRegisterUser user);
    ResponseUser userToResponseUser(User user);

    ResponseCheckUser userToResponseCheckUser(User user);
}
