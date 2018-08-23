package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.business.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public UserDto entityToDto(User user) {
        return new UserDto(user.getName(), user.getResourceId(), user.getUserType(), user.getCreatedDate());
    }

    public List<UserDto> entityToDto(List<User> user) {
        return user.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
