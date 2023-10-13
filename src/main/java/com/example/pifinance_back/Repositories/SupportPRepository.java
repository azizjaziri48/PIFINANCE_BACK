package com.example.pifinance_back.Repositories;

import com.example.pifinance_back.Entities.SupportP;
import com.example.pifinance_back.Entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportPRepository extends JpaRepository<SupportP,Integer> {
    @Query("select s from SupportP s where s.Type = ?1")
    List<SupportP> findByType(Type Type);

}
