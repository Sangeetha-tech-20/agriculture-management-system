package com.agriculture.controller;

import com.agriculture.dto.ApiResponse;
import com.agriculture.entity.Irrigation;
import com.agriculture.entity.User;
import com.agriculture.service.IrrigationService;
import com.agriculture.service.UserService;
import com.agriculture.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/irrigation")
@CrossOrigin(origins = "*")
public class IrrigationController {
    @Autowired
    private IrrigationService irrigationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ApiResponse> scheduleIrrigation(@RequestBody Irrigation irrigation) {
        try {
            // FIX: Attach logged-in user to irrigation
            Long userId = getCurrentUserId();
            User user = userService.findById(userId);
            irrigation.setUser(user);
            
            Irrigation savedIrrigation = irrigationService.scheduleIrrigation(irrigation);
            return ResponseEntity.ok(new ApiResponse(true, "Irrigation scheduled successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Irrigation>> getIrrigationsByUserId(@PathVariable Long userId) {
        List<Irrigation> irrigations = irrigationService.getIrrigationsByUserId(userId);
        return ResponseEntity.ok(irrigations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Irrigation> getIrrigationById(@PathVariable Long id) {
        Optional<Irrigation> irrigation = irrigationService.getIrrigationById(id);
        return irrigation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateIrrigation(@PathVariable Long id, @RequestBody Irrigation irrigation) {
        try {
            irrigation.setId(id);
            Irrigation updatedIrrigation = irrigationService.updateIrrigation(irrigation);
            return ResponseEntity.ok(new ApiResponse(true, "Irrigation updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteIrrigation(@PathVariable Long id) {
        try {
            irrigationService.deleteIrrigation(id);
            return ResponseEntity.ok(new ApiResponse(true, "Irrigation deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/upcoming/{userId}")
    public ResponseEntity<List<Irrigation>> getUpcomingIrrigations(@PathVariable Long userId) {
        List<Irrigation> irrigations = irrigationService.getUpcomingIrrigations(userId);
        return ResponseEntity.ok(irrigations);
    }
    
    // Helper method to get user ID from JWT token (Same as CropController)
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