package com.projeto.pi.projeto_pi.modals.users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.pi.projeto_pi.annotations.EnumValidator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O login deve ser informado")
    @NotNull(message = "Valor invalido para o login")
    private String login;
    @NotBlank(message = "A senha deve ser informada")
    @NotNull(message = "Valor invalido para a senha")
    private String senha;
    @NotBlank(message = "O nome deve ser informado")
    @NotNull(message = "Valor invalido para o nome")
    private String nome;

    @NotNull(message = "O ativo deve ser informado")
    private Boolean ativo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O data de nascimento deve ser informado")
    @Past(message = "A data de nascimento deve ser posterior a data atual")
    private Date dataNascimento;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O data de cadastro deve ser informado")
    @PastOrPresent(message = "A data de cadastro nao pode ser posterior a data atual")
    private Date dataCadastro;

    @EnumValidator(enumclass = User.Role.class, message = "Valor inválido para role")
    private String role;

    public User toEntity() {
        return new User(id, login, senha, nome, ativo, dataNascimento, dataCadastro, role);
    }

}
