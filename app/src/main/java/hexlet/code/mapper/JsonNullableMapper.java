package hexlet.code.mapper;

import org.mapstruct.Condition;
import org.openapitools.jackson.nullable.JsonNullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public class JsonNullableMapper {
    public <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }
    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }
    @Condition
    public <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
