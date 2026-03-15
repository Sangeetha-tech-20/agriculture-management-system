package com.agriculture.repository;

import com.agriculture.entity.Irrigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IrrigationRepository extends JpaRepository<Irrigation, Long> {
    List<Irrigation> findByUserId(Long userId);
    // This method fixes your error:
    List<Irrigation> findByUserIdAndScheduleDateAfter(Long userId, LocalDate date);
}