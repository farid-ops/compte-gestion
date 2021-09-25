package com.snowden.core.dao;

import com.snowden.core.model.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    @Query("select c from Compte  c where c.client.id =:x")
    Page<Compte> listeCompte(@Param("x") Long code, Pageable pageable);
}
