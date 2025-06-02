package com.Question2.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<?> getAverageStockPrice(
            @PathVariable String ticker,
            @RequestParam int minutes,
            @RequestParam String aggregation) {

        if (!"average".equalsIgnoreCase(aggregation)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Only 'average' aggregation is supported"));
        }

        StockResponse response = stockService.getAveragePrice(ticker, minutes);

        return ResponseEntity.ok(response);
    }
}

