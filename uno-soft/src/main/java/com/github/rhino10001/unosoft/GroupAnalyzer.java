package com.github.rhino10001.unosoft;

import java.util.*;
import java.util.stream.Collectors;

public class GroupAnalyzer {

    private final String empty = "\"\"";

    public List<Group> getGroupList(List<Line> dataMatrix) {

        int maxLineSize = dataMatrix.stream()
                .map((line -> line.getValue().size()))
                .max(Comparator.naturalOrder())
                .orElse(Integer.MIN_VALUE);

        List<Map<Line, CrossPoint>> crossPointsByColumns = getCrossPointsForByAllColumns(maxLineSize, dataMatrix);
        return getGroups(crossPointsByColumns);
    }

    private List<Map<Line, CrossPoint>> getCrossPointsForByAllColumns(int size, List<Line> dataMatrix) {
        List<Map<Line, CrossPoint>> crossPointsByColumns = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Line> sortedList = sortByColumnNumberAndSize(dataMatrix, i);
            Map<Line, CrossPoint> columnCrossPoints = getCrossPointsForColumn(sortedList, i);
            crossPointsByColumns.add(columnCrossPoints);
        }
        return crossPointsByColumns;
    }

    private List<Line> sortByColumnNumberAndSize(List<Line> data, int columnNumber) {
        return data.stream()
                .filter((l) -> l.getValue().size() > columnNumber)
                .sorted(Comparator.comparing(l -> l.getValue().get(columnNumber)))
                .collect(Collectors.toList());
    }

    private Map<Line, CrossPoint> getCrossPointsForColumn(List<Line> sortedData, int columnNumber) {
        Map<Line, CrossPoint> crossPointMap = new HashMap<>();
        CrossPoint crossPoint = new CrossPoint(sortedData.get(0));

        for (int i = 1; i < sortedData.size(); i++) {
            Line previousLine = sortedData.get(i - 1);
            Line currentLine = sortedData.get(i);
            if (previousLine.getValue().get(columnNumber).equals(currentLine.getValue().get(columnNumber))
                && !previousLine.getValue().get(columnNumber).equals(empty)
                && !previousLine.getValue().get(columnNumber).isBlank()) {
                crossPoint.getLines().add(currentLine);
            } else {
                Set<Line> crossingLines = crossPoint.getLines();
                if (crossingLines.size() > 1) {
                    for (Line l : crossingLines) {
                        crossPointMap.put(l, crossPoint);
                    }
                }
                crossPoint = new CrossPoint(currentLine);
            }
        }
        Set<Line> crossingLines = crossPoint.getLines();
        if (crossingLines.size() > 1) {
            for (Line l : crossingLines) {
                crossPointMap.put(l, crossPoint);
            }
        }
        return crossPointMap;
    }

    private List<Group> getGroups(List<Map<Line, CrossPoint>> crossPointsByColumns) {

        Set<Line> allLines = crossPointsByColumns
                .stream()
                .flatMap((m) -> m.keySet().stream())
                .collect(Collectors.toSet());

        List<Group> groups = new ArrayList<>();
        Set<Line> checked = new HashSet<>();
        for (Line line : allLines) {
            if (checked.contains(line)) continue;

            Group group = new Group();
            Deque<Line> crawlDeque = new LinkedList<>();
            Set<Line> dequeCache = new HashSet<>();
            crawlDeque.add(line);

            while (!crawlDeque.isEmpty()) {
                Line current = crawlDeque.pollFirst();
                if (checked.contains(current)) continue;
                checked.add(current);
                dequeCache.add(current);

                for (int i = 0; i < current.getValue().size(); i++) {
                    Map<Line, CrossPoint> map = crossPointsByColumns.get(i);
                    if (map.containsKey(current)) {
                        CrossPoint crossPoint = crossPointsByColumns.get(i).get(current);
                        group.getLines().addAll(crossPoint.getLines());
                    }
                }

                if (crawlDeque.isEmpty()) {
                    Set<Line> forAdd = new HashSet<>(group.getLines());
                    forAdd.removeIf(dequeCache::contains);
                    crawlDeque.addAll(forAdd);
                }
            }

            groups.add(group);
        }
        return groups;
    }
}
