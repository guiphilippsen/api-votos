package com.github.guiphilippsen.api_votos.repository;

import com.github.guiphilippsen.api_votos.entity.Eleitor;
import com.github.guiphilippsen.api_votos.entity.enums.StatusEleitor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    @Query("SELECT e FROM Eleitor e WHERE e.status = :status")
    List<Eleitor> findByStatus(StatusEleitor statusEleitor);

    @Modifying
    @Transactional
    @Query("UPDATE Eleitor e SET e.email = :email, e.cpf = :cpf WHERE e.id = userId")
    int updateUserEmailAndCpf(Long userId, String email, String cpf);

    @Modifying
    @Transactional
    @Query("UPDATE Eleitor e SET e.status = :newStatus WHERE e.id = :userId")
    int updateUserStatus(Long userId, StatusEleitor newStatus);
}
