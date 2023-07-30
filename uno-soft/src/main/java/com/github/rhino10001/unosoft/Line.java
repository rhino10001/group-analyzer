package com.github.rhino10001.unosoft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {

    private final List<String> value;

    public Line(String[] words) {
        this.value = new ArrayList<>(Arrays.asList(words));
    }

    public List<String> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
