package com.agriculture.controller;

import com.agriculture.dto.ApiResponse;
import com.agriculture.entity.Crop;
import com.agriculture.entity.User;
import com.agriculture.service.CropService;
import com.agriculture.service.UserService;
import com.agriculture.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin(origins = "*")
public class CropController {
    private final CropService cropService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public CropController(CropService cropService, UserService userService, JwtUtil jwtUtil) {
        this.cropService = cropService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        try {
            Long userId = getCurrentUserId();
            List<Crop> crops = cropService.getCropsByUserId(userId);
            crops.forEach(c -> c.setUser(null));
            return ResponseEntity.ok(crops);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> addCrop(@RequestBody Map<String, Object> request) {
        try {
            Long userId = getCurrentUserId();
            User user = userService.findById(userId);
            
            Crop crop = new Crop();
            crop.setName((String) request.get("name"));
            crop.setVariety((String) request.get("variety"));
            
            Object areaObj = request.get("area");
            if (areaObj instanceof Number) {
                crop.setArea(((Number) areaObj).doubleValue());
            }

            String plantedDateStr = (String) request.get("plantedDate");
            String harvestDateStr = (String) request.get("harvestDate");
            
            if (plantedDateStr != null) crop.setPlantedDate(LocalDate.parse(plantedDateStr));
            if (harvestDateStr != null) crop.setHarvestDate(LocalDate.parse(harvestDateStr));
            
            String statusStr = (String) request.get("status");
            if (statusStr != null) {
                crop.setStatus(Crop.Status.valueOf(statusStr.toUpperCase()));
            }

            crop.setUser(user);
            
            cropService.saveCrop(crop);
            
            return ResponseEntity.ok(new ApiResponse(true, "Crop added successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCrop(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Crop crop = cropService.getCropById(id);
            
            if (request.containsKey("name")) crop.setName((String) request.get("name"));
            if (request.containsKey("variety")) crop.setVariety((String) request.get("variety"));
            
            if (request.get("area") instanceof Number) {
                crop.setArea(((Number) request.get("area")).doubleValue());
            }

            if (request.containsKey("plantedDate")) {
                crop.setPlantedDate(LocalDate.parse((String) request.get("plantedDate")));
            }
            if (request.containsKey("harvestDate")) {
                crop.setHarvestDate(LocalDate.parse((String) request.get("harvestDate")));
            }
            
            if (request.containsKey("status")) {
                crop.setStatus(Crop.Status.valueOf(((String) request.get("status")).toUpperCase()));
            }

            cropService.saveCrop(crop);
            
            return ResponseEntity.ok(new ApiResponse(true, "Crop updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCrop(@PathVariable Long id) {
        try {
            cropService.deleteCrop(id);
            return ResponseEntity.ok(new ApiResponse(true, "Crop deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    private Long getCurrentUserId() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String authHeader = attrs.getRequest().getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtUtil.extractUsername(token);
                return userService.findByEmail(email).getId();
            } catch (Exception e) {
                throw new RuntimeException("Invalid token");
            }
        }
        throw new RuntimeException("No authentication token provided");
    }
}