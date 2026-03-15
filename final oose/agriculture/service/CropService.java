package com.agriculture.service;

import com.agriculture.entity.Crop;
import com.agriculture.entity.User;
import com.agriculture.repository.CropRepository;
import com.agriculture.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CropService {
    private final CropRepository cropRepository;
    private final UserRepository userRepository;

    public CropService(CropRepository cropRepository, UserRepository userRepository) {
        this.cropRepository = cropRepository;
        this.userRepository = userRepository;
    }
    
    public List<Crop> getCropsByUserId(Long userId) {
        return cropRepository.findByUserId(userId);
    }

    public Crop getCropById(Long id) {
        return cropRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Crop not found"));
    }

    // Method used by Controller
    @Transactional
    public void saveCrop(Crop crop) {
        cropRepository.save(crop);
    }
    
    @Transactional
    public void deleteCrop(Long id) {
        cropRepository.deleteById(id);
    }
}