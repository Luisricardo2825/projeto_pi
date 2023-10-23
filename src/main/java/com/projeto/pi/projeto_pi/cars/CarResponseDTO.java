package com.projeto.pi.projeto_pi.cars;

public record CarResponseDTO(Long id, String title, String image, Integer price) {
    public CarResponseDTO(Car food) {
        this(food.getId(), food.getTitle(), food.getImage(), food.getPrice());
    }
}
