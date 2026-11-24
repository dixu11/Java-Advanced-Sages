package pl.sages.jext.examples.ch6.libraries.mapping;

import lombok.Getter;
import lombok.Builder;
import java.time.Instant;
import java.util.List;

@Getter @Builder
public class UserEntity {
    private Long id;
    private String email;
    private String passwordHash;
    private Instant createdAt;
    private List<String> roles;
}
