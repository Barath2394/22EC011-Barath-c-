package com.Barath._EC011;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/numbers")
public class NumbersController {

    private final NumberService numberService;

    public NumbersController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("/{numberId}")
    public ResponseEntity<?> getNumbers(@PathVariable String numberId) {
        try {
            NumbersResponse response = numberService.fetchAndCalculate(numberId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

