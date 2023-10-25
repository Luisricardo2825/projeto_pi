package com.projeto.pi.projeto_pi.modals.interests;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.projeto.pi.projeto_pi.modals.cars.Car;

@Repository
public interface InterestRepo extends PagingAndSortingRepository<Interest, Long> {
    Optional<Interest> findById(long id);

    Optional<Interest> findByNome(String nome);

    Optional<Interest> findByNomeIgnoreCase(String nome);

    Optional<Interest> findByCarro(Car carro);

    Interest save(Interest item);

    Iterable<Interest> findAll();

    Page<Interest> findAllByNome(String nome, Pageable pageable);

    void deleteById(Long id);

}