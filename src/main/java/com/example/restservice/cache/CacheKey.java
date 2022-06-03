package com.example.restservice.cache;

import java.util.Objects;

public class CacheKey {
    private final Integer number;
    private final String side;

    public CacheKey(Integer number, String side) {
        this.number = number;
        this.side = side;
    }

    public Integer getNumber() {
        return number;
    }

    public String getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "CacheKey{" +
                "number=" + number +
                ", side=" + side +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return Objects.equals(number, cacheKey.number) && Objects.equals(side, cacheKey.side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, side);
    }
}
