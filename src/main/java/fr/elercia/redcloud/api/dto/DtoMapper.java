package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static UserDto entityToDto(User user) {
        return new UserDto(user.getName(), user.getResourceId(), user.getUserType(), user.getCreatedDate());
    }

    public static List<UserDto> entityToDto(List<User> user) {
        return user.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static TokenDto map(Token token) {
        return new TokenDto(token.getAccessToken(), token.getTokenType(), token.getExpireIn(), token.getRefreshToken());
    }
}
