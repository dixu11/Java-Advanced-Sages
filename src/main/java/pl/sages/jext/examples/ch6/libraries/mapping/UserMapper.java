package pl.sages.jext.examples.ch6.libraries.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserDto toDto(UserEntity e);

    @Mapping(target = "mail", source = "email")
    MailUserDto toMailDto(UserEntity e);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtos(List<UserEntity> entities);
}
