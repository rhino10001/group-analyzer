package com.github.rhino10001.unosoft;

import java.util.HashSet;
import java.util.Set;

public class CrossPoint {

    private final Set<Line> lines = new HashSet<>();

    public CrossPoint(Line line) {
        lines.add(line);
    }

    public Set<Line> getLines() {
        return lines;
    }
}
