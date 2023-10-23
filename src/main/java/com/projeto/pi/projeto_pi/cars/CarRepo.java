package com.projeto.pi.projeto_pi.cars;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepo extends PagingAndSortingRepository<Car, Long> {
    Optional<Car> findById(long id);

    Car save(Car item);

    void deleteById(Long id);
}