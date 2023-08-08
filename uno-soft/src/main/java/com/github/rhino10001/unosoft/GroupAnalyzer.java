package com.github.rhino10001.unosoft;

import java.util.*;

public class GroupAnalyzer {

    private static final String EMPTY_QUOTES = "\"\"";

    public List<Group> getGroupList(List<Line> lines) {

        List<Group> groupList = new ArrayList<>();

        List<Map<String, Integer>> crossPointsByColumns = new ArrayList<>();
        Map<Integer, Integer> mergeHistoryChain = new HashMap<>();

        for (Line line : lines) {

            TreeSet<Integer> indexesOfGroupsToMerge = new TreeSet<>();
            Map<Integer, String> newCrossPoints = new HashMap<>();

            String[] split = line.value().split(";");
            for (int i = 0; i < split.length; i++) {
                String word = split[i];
                if (crossPointsByColumns.size() == i) crossPointsByColumns.add(new HashMap<>());
                if (word.equals(EMPTY_QUOTES) || word.isBlank()) continue;

                Map<String, Integer> currentColumnCrossPoints = crossPointsByColumns.get(i);
                Integer rootGroupIndex = currentColumnCrossPoints.get(word);
                if (rootGroupIndex != null) {
                    while (mergeHistoryChain.containsKey(rootGroupIndex)) {
                        rootGroupIndex = mergeHistoryChain.get(rootGroupIndex);
                    }
                    indexesOfGroupsToMerge.add(rootGroupIndex);
                } else {
                    newCrossPoints.put(i, word);
                }
            }

            int destGroupIndex;
            if (indexesOfGroupsToMerge.isEmpty()) {
                groupList.add(new Group());
                destGroupIndex = groupList.size() - 1;
            } else {
                destGroupIndex = indexesOfGroupsToMerge.first();
            }

            indexesOfGroupsToMerge.stream()
                    .filter(i -> i != destGroupIndex)
                    .forEach((i) -> {
                        mergeHistoryChain.put(i, destGroupIndex);
                        groupList.get(destGroupIndex).getLines().addAll(groupList.get(i).getLines());
                        groupList.set(i, null);
                    });

            newCrossPoints.forEach((i, s) -> {
                crossPointsByColumns.get(i).put(s, destGroupIndex);
            });

            groupList.get(destGroupIndex).getLines().add(line);
        }

        groupList.removeAll(Collections.singleton(null));
        return groupList;
    }
}
