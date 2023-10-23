package com.projeto.pi.projeto_pi.cars;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String title;
    private String image;
    private Integer price;

    public Car(CarResponseDTO data) {
        this.title = data.title();
        this.image = data.image();
        this.price = data.price();
    }

    public CarResponseDTO toDTO() {
        return new CarResponseDTO(this);
    }
}
