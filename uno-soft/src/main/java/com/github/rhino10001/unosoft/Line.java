package com.github.rhino10001.unosoft;

public record Line(String value) {

    @Override
    public String toString() {
        return value;
    }
}
