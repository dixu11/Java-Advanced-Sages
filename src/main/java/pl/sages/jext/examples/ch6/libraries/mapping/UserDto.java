package pl.sages.jext.examples.ch6.libraries.mapping;

import java.util.List;

public record UserDto(Long id, String email, List<String> roles) {}
