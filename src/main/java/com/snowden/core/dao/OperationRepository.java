package com.snowden.core.dao;

import com.snowden.core.model.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o where o.compte.codeCompte like :x order by o.operationDate desc ")
    Page<Operation> listeOperation(@Param("x") String codeCompte, Pageable pageable);
}
