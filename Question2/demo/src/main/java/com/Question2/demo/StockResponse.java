package com.Question2.demo;

import java.util.List;

public class StockResponse {
    private double averageStockPrice;
    private List<PricePoint> priceHistory;

    public double getAverageStockPrice() {
        return averageStockPrice;
    }

    public void setAverageStockPrice(double averageStockPrice) {
        this.averageStockPrice = averageStockPrice;
    }

    public List<PricePoint> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PricePoint> priceHistory) {
        this.priceHistory = priceHistory;
    }
}
