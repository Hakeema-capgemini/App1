package com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.project.dto.UserDTO;
import com.example.project.vo.UserVO;

@Mapper(componentModel = "spring")

public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO toDTO(UserVO userVO); // Convert VO to DTO

	UserVO toVO(UserDTO userDTO);

}
