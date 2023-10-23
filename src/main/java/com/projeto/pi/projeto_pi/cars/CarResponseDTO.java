package com.projeto.pi.projeto_pi.cars;

import java.sql.Date;

public record CarResponseDTO(Long id, String marca,
        String modelo, Date anoFabricacao, Date anoModelo, Double valor, String descricao, String image) {
    public CarResponseDTO(Car car) {
        this(car.getId(), car.getMarca(), car.getModelo(), car.getAnoFabricacao(), car.getAnoModelo(),
                car.getValor(), car.getDescricao(), car.getImage());
    }

}
