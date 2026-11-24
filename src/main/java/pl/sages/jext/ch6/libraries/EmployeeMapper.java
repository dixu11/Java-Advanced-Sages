package pl.sages.jext.ch6.libraries;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Pokaz MapStruct (slajdy 164-165), gotowe. Implementację tego interfejsu generuje procesor
 * adnotacji przy kompilacji (target/generated-sources/annotations).
 */
@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    record EmployeeDto(String fullName, String department) {
    }

    @Mapping(target = "fullName", expression = "java(p.getFirstName() + \" \" + p.getLastName())")
    EmployeeDto toDto(Employee p);
}
