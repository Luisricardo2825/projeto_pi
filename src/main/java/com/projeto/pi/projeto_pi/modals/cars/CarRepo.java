package com.projeto.pi.projeto_pi.modals.cars;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface CarRepo extends PagingAndSortingRepository<Car, Long> {
    Optional<Car> findById(long id);

    @Query(value = "Select * from CARROS c where COALESCE( ( Select ativo from interesses i where i.car_id = c.id and i.ativo = true ), false ) = false and c.id= :id order by c.id", nativeQuery = true)
    Optional<Car> findByIdActive(long id);

    @Query(value = "Select * from CARROS c where COALESCE( ( Select ativo from interesses i where i.car_id = c.id and i.ativo = true ), false ) = false order by c.id", nativeQuery = true)
    Page<Car> findAllActive(Pageable pageable);

    Car save(Car item);

    void deleteById(Long id);
}