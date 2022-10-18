package dev.jtm.library.services.security;

import dev.jtm.library.entities.security.AppRole;
import dev.jtm.library.security.exceptions.PasswordNotMatchException;
import dev.jtm.library.security.exceptions.RoleNotFoundException;
import dev.jtm.library.security.request.LoginRequest;
import dev.jtm.library.security.request.RegisterRequest;
import dev.jtm.library.security.request.ResetPasswordRequest;
import dev.jtm.library.security.response.AppUserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;

public interface AppUsersService extends UserDetailsService {
    String authenticate(LoginRequest request, AuthenticationManager authenticationManager);

    AppUserResponse authUser(Authentication authentication);

    AppUserResponse storeUser(RegisterRequest registerRequest) throws RoleNotFoundException, MessagingException;

    AppUserResponse updateUser(RegisterRequest request, Authentication authentication);

    void removeUser(String username);

    AppUserResponse resetPassword(ResetPasswordRequest request, String username) throws PasswordNotMatchException;

    AppUserResponse disabledAccount(String username);

    AppUserResponse enabledAccount(String username);

    AppRole saveRole(AppRole role);
}
