package com.example.pifinance_back.Repositories;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.Wallet;
import com.example.pifinance_back.Entities.WalletEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Currency;
import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByType(@Param("type") WalletEnum type);
    List<Wallet> findByCurrency(@Param("currency") Currency currency);
    List<Wallet> findByUserAndType(@Param("user") Client user, @Param("type") WalletEnum type);
    List<Wallet> findByUserAndIsActive(@Param("user") Client user, @Param("isActive") boolean isActive);
    List<Wallet> findByUser(Client user);

}
