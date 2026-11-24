package pl.sages.jext.examples.ch6.libraries.mapping;

import java.util.List;

public record MailUserDto(Long id, String mail, List<String> roles) {}
