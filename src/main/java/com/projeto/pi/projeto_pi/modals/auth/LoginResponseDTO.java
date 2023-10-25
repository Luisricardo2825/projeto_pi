package com.projeto.pi.projeto_pi.modals.auth;

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
public class LoginResponseDTO {

    private String token;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exp;
    private String role;
    private Long userId;
    private String user;
    private String name;
    private String login;

}
