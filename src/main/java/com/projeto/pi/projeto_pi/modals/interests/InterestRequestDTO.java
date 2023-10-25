package com.projeto.pi.projeto_pi.modals.interests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.pi.projeto_pi.modals.cars.Car;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class InterestRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "O data de nascimento deve ser informado")
    @Past(message = "A data de nascimento deve ser posterior a data atual")
    private Date dataInteresse;

    @NotBlank(message = "O carro deve ser informado")
    private Car car;

    @NotBlank(message = "O usuario deve ser informado")
    private String nome;

    @NotBlank(message = "O usuario deve ser informado")
    @Pattern(regexp = "(?:([+]\\d{1,4})[-.\\s]?)?(?:[(](\\d{1,3})[)][-.\\s]?)?(\\d{1,4})[-.\\s]?(\\d{1,4})[-.\\s]?(\\d{1,9})", message = "Telefone invalido")
    private String telefone;

    private Boolean ativo;

    public Interest toEntity() {
        if (ativo == null) {
            this.ativo = false;
        }
        return new Interest(id, dataInteresse, new Car(), nome, telefone, ativo);
    }
}
