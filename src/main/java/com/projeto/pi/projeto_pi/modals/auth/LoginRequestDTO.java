package com.projeto.pi.projeto_pi.modals.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "O login deve ser informado")
    @NotNull(message = "O login deve ser informado")
    private String login;
    @NotBlank(message = "A senha deve ser informada")
    @NotNull(message = "A senha deve ser informada")
    private String senha;
}
