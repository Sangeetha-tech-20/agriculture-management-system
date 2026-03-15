package com.agriculture.service;

import com.agriculture.entity.Irrigation;
import com.agriculture.repository.IrrigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IrrigationService {
    @Autowired
    private IrrigationRepository irrigationRepository;

    public Irrigation scheduleIrrigation(Irrigation irrigation) {
        return irrigationRepository.save(irrigation);
    }

    public List<Irrigation> getIrrigationsByUserId(Long userId) {
        return irrigationRepository.findByUserId(userId);
    }

    public Optional<Irrigation> getIrrigationById(Long id) {
        return irrigationRepository.findById(id);
    }

    public Irrigation updateIrrigation(Irrigation irrigation) {
        return irrigationRepository.save(irrigation);
    }

    public void deleteIrrigation(Long id) {
        irrigationRepository.deleteById(id);
    }

    public List<Irrigation> getUpcomingIrrigations(Long userId) {
        return irrigationRepository.findByUserIdAndScheduleDateAfter(userId, LocalDate.now());
    }
}