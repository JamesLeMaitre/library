package dev.jtm.library.security.response;

import dev.jtm.library.entities.security.AppRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class AppUserResponse {
    private String username;

    private String email;

    private Boolean isNotLocked;

    private Boolean isActive;

    private Collection<AppRole> roles;
}
