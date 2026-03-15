package com.agriculture.repository;

import com.agriculture.entity.Crop;
import com.agriculture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByUser(User user);
    List<Crop> findByUserId(Long userId);
}