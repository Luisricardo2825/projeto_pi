package com.projeto.pi.projeto_pi.modals.users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String login;
    private String senha;
    private String nome;
    private Boolean ativo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataCadastro;
    private String role;

    public UserResponseDTO(User user) {
        this(user.getId(), user.getLogin(), user.getSenha(), user.getNome(), user.getAtivo(), user.getDataNascimento(),
                user.getDataCadastro(), user.getRole());
    }

}
