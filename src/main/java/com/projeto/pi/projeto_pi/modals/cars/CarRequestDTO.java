package com.projeto.pi.projeto_pi.modals.cars;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.pi.projeto_pi.modals.interests.Interest;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class CarRequestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A marca deve ser informada")
    @NotNull(message = "Valor invalido para a marca")
    private String marca;

    @NotBlank(message = "A descricao deve ser informada")
    @NotNull(message = "Valor invalido para a descricao")
    private String descricao;

    @NotBlank(message = "O modelo deve ser informado")
    @NotNull(message = "Valor invalido para o modelo")
    private String modelo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O ano do modelo deve ser informado")
    @NotNull(message = "Valor invalido para o ano do modelo")
    private Date anoModelo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Valor invalido para o ano de fabricacao")
    @PastOrPresent(message = "A data de fabricacao nao pode ser posterior a data atual")
    private Date anoFabricacao;

    @NotNull(message = "Valor invalido para o valor")
    @Min(value = 0, message = "O valor tem que ser maior que 0")
    private double valor;

    @NotBlank(message = "A imagem deve ser informada")
    @NotNull(message = "Valor invalido para a imagem")
    private String image;

    @JoinColumn(name = "carId")
    private Interest interest;

    public Car toEntity() {
        return new Car(id, marca, descricao, modelo, anoModelo, anoFabricacao, valor, image, interest);
    }

}
