package com.projeto.pi.projeto_pi.cars;

import java.sql.Date;

public record CarRequestDTO(Long id, String marca,
        String modelo, Date anoFabricacao, Date anoModelo, Double valor, String descricao, String image) {

    public Car toEntity() {

        return new Car(id, marca,
                modelo, anoFabricacao, anoModelo, valor, descricao, image);

    }

}
