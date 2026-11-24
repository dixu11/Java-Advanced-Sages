package pl.sages.jext.examples.ch6.libraries.mapping;

import java.time.Instant;
import java.util.List;

/**
 * Slajdy 164-165: encja -> DTO, zmiana nazwy pola, pominięcie i lista.
 * Uruchomienie: mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch6.libraries.mapping.EntityDtoDemo
 * Wygenerowany mapper: target/generated-sources/annotations/.../UserMapperImpl.java.
 * DTO -> nowa encja nie odtwarza hasła ani daty utworzenia; ustawia je logika aplikacji.
 */
public class EntityDtoDemo {
    public static void main(String[] args) {
        var entity = UserEntity.builder().id(7L).email("ala@example.com")
            .passwordHash("hash-z-bazy").createdAt(Instant.parse("2026-11-24T08:00:00Z"))
            .roles(List.of("USER")).build();
        UserMapper mapper = new UserMapperImpl(); // wygenerowany przy kompilacji
        var dto = mapper.toDto(entity);
        System.out.println(dto); // UserDto[id=7, email=ala@example.com, roles=[USER]]
        System.out.println(mapper.toMailDto(entity).mail()); // ala@example.com
        System.out.println(mapper.toEntity(dto).getPasswordHash()); // null
        System.out.println(mapper.toDtos(List.of(entity)).size()); // 1
    }
}
