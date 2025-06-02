package com.Question2.demo;
import java.time.Instant;
import java.util.*;
public class PricePoint {
    private double price;
    private Instant lastUpdatedAt;

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

