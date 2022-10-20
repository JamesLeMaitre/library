package dev.jtm.library.security.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class ResetPasswordRequest {

    @NotNull
    @NotBlank
    private String old_password;

    @NotNull
    @NotBlank
    private String new_password;

    @NotNull
    @NotBlank
    private String confirm_password;
}
