package dmit2015.dto;

import dmit2015.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    //    @Mappings({
//            @Mapping(target = "dtoPropertyName", source = "entity.entityPropertyName")
//    })
    TodoDto toDto(Todo entity);

    //    @Mappings({
//            @Mapping(target = "dtoPropertyName", source = "entityPropertyName")
//    })
    Todo toEntity(TodoDto dto);

}