package com.bank.internetbankapi.domain.repository;

import com.bank.internetbankapi.domain.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {

    @Query("select op from Operation op where op.userId = :userId " +
            "and (op.createdAt >= :startDate or cast(:startDate AS java.time.LocalDateTime) is null)" +
            "and (op.createdAt <= :endDate or cast(:endDate AS java.time.LocalDateTime) is null)")
    List<Operation> findOperationList(@Param("userId") UUID userId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);
}
