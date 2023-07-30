package com.github.rhino10001.unosoft;

import java.util.HashSet;
import java.util.Set;

public class Group {

    private final Set<Line> lines = new HashSet<>();

    public Set<Line> getLines() {
        return lines;
    }
}
