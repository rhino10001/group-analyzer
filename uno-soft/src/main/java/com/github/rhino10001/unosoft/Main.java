package com.github.rhino10001.unosoft;

import com.github.rhino10001.unosoft.utils.FileUtils;
import com.github.rhino10001.unosoft.utils.RuntimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("Start time: " + LocalDateTime.now());
        System.out.println("...");

        String file = args[0];
        String delimiter = ";";
        String resultFilename = "result.txt";

        List<Line> lines = FileUtils.readUniqueValidLinesFromFile(file, delimiter);

        GroupAnalyzer groupAnalyzer = new GroupAnalyzer();
        List<Group> groups = new ArrayList<>(groupAnalyzer.getGroupList(lines).stream().filter(g -> g.getLines().size() > 1).toList());
        groups.sort(Comparator.comparing(g -> g.getLines().size(), Comparator.reverseOrder()));

        FileUtils.writeGroupsToFile(groups, resultFilename);

        long finish = System.currentTimeMillis();
        System.out.println("Finish time " + LocalDateTime.now());
        System.out.println();
        RuntimeUtils.printMemoryInfo();
        System.out.println("Duration, (s): " + ((finish - start) / 1000.));
    }
}
