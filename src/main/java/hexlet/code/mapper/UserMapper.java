package hexlet.code.mapper;

import hexlet.code.dto.UserDTO;
import hexlet.code.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.MappingTarget;

@Mapper(
        uses =  { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract User map(UserDTO userData);
    @Mapping(target = "password", ignore = true)
    public abstract UserDTO map(User user);
    @InheritConfiguration
    public abstract void update(UserDTO userData, @MappingTarget User user);
}
