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

        List<List<CrossPoint>> crossPointsMatrix = getCrossPointsForAllColumns(maxLineSize, dataMatrix);
        return getGroups(dataMatrix, crossPointsMatrix);
    }

    private List<List<CrossPoint>> getCrossPointsForAllColumns(int size, List<Line> dataMatrix) {
        List<List<CrossPoint>> crossPointsMatrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Line> sortedList = sortByColumnNumberAndSize(dataMatrix, i);
            List<CrossPoint> columnCrossPoints = getCrossPointsForColumn(sortedList, i);
            crossPointsMatrix.add(columnCrossPoints);
        }
        return crossPointsMatrix;
    }

    private List<Line> sortByColumnNumberAndSize(List<Line> data, int columnNumber) {
        return data.stream()
                .filter((l) -> l.getValue().size() > columnNumber)
                .sorted(Comparator.comparing(l -> l.getValue().get(columnNumber)))
                .collect(Collectors.toList());
    }

    private List<CrossPoint> getCrossPointsForColumn(List<Line> sortedData, int columnNumber) {
        List<CrossPoint> crossPointList = new ArrayList<>();
        CrossPoint crossPoint = new CrossPoint(sortedData.get(0));
        for (int i = 1; i < sortedData.size(); i++) {
            Line previousLine = sortedData.get(i - 1);
            Line currentLine = sortedData.get(i);
            if (previousLine.getValue().get(columnNumber).equals(currentLine.getValue().get(columnNumber))
                && !previousLine.getValue().get(columnNumber).equals(empty)) {
                crossPoint.getLines().add(currentLine);
            } else {
                if (crossPoint.getLines().size() > 1) {
                    crossPointList.add(crossPoint);
                }
                crossPoint = new CrossPoint(currentLine);
            }
        }
        if (crossPoint.getLines().size() > 1) {
            crossPointList.add(crossPoint);
        }
        return crossPointList;
    }

    private List<Group> getGroups(List<Line> dataMatrix, List<List<CrossPoint>> crossPointsMatrix) {
        List<Group> groups = new ArrayList<>();
        Set<Line> checked = new HashSet<>();
        for (Line line : dataMatrix) {
            Group group = new Group();
            Deque<Line> crawlDeque = new LinkedList<>();
            crawlDeque.add(line);
            while (!crawlDeque.isEmpty()) {
                Line current = crawlDeque.pollFirst();
                if (checked.contains(current)) continue;
                checked.add(current);
                for (int i = 0; i < current.getValue().size(); i++) {
                    for (CrossPoint crossPoint : crossPointsMatrix.get(i)) {
                        if (crossPoint.getLines().contains(current)) {
                            group.getLines().addAll(crossPoint.getLines());
                            crawlDeque.addAll(crossPoint.getLines());
                        }
                    }
                }
            }

            if (group.getLines().size() > 0) groups.add(group);
        }
        return groups;
    }
}
