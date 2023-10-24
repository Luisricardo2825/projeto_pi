package com.projeto.pi.projeto_pi.modals.users;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    Optional<User> findById(long id);

    User save(User item);

    void deleteById(Long id);

    Optional<User> findByLoginIgnoreCase(String login);
}
