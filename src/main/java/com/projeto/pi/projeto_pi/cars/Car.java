package com.projeto.pi.projeto_pi.cars;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Carros")
@Entity(name = "carros")
@EqualsAndHashCode(of = "id")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private String descricao;

    private String modelo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date anoModelo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date anoFabricacao;

    private double valor;

    private String image;

    public Car(CarResponseDTO data) {
        this.marca = data.marca();
        this.descricao = data.descricao();
        this.descricao = data.descricao();
        this.modelo = data.modelo();
        this.anoModelo = data.anoModelo();
        this.anoFabricacao = data.anoFabricacao();
        this.valor = data.valor();
        this.image = data.image();
        this.id = data.id();
    }

    public CarResponseDTO toDTO() {
        return new CarResponseDTO(this);
    }
}
