package com.example.pifinance_back.Repositories;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.UserEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    @Query("SELECT u FROM Client u WHERE u.email = :email")
    Client findByEmail1(@Param("email") String email);
    boolean existsByEmail(String email);
    Optional<Client> findByToken(String token);
    List<Client> findByRole(UserEnum role);
    Optional<Client> findByFirstnameAndLastname(String firstname, String lastname);

    Client findClientById(Long id);
    Client findByName(String customerName);
    @Query("SELECT c FROM Client c WHERE c.id = :id AND c.pwd_user IS NOT NULL")
    Optional<Client> findClientWithPassword(@Param("id") Long id);

}
