package com.github.rhino10001.unosoft.utils;

import com.github.rhino10001.unosoft.Group;
import com.github.rhino10001.unosoft.Line;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class FileUtils {

    public static List<Line> readUniqueValidLinesFromFile(String filename, String delimiter) {
        List<Line> data = new ArrayList<>();
        Set<String> cache = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.ready()) {
                String string = reader.readLine();
                if (!cache.contains(string)) {
                    cache.add(string);
                    String[] split = string.split(delimiter);
                    if (isValid(split)) {
                        Line line = new Line(string);
                        data.add(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean isValid(String[] stringArr) {
        for (String s : stringArr) {
            if (s.isEmpty()) continue;
            if (!Pattern.matches("^\"[^\"]*\"$", s)) {
                return false;
            }
        }
        return true;
    }

    public static void writeGroupsToFile(List<Group> groups, String filename) {
        StringBuilder builder = new StringBuilder();
        builder.append("Количество групп: ").append(groups.size()).append("\n\n");

        for (int i = 0; i < groups.size(); i++) {
            if (i > 0) builder.append("\n\n");
            builder.append("Группа ").append(i + 1);

            for (Line line : groups.get(i).getLines()) {
                builder.append("\n\n");
                builder.append(line.value());
            }
        }
        builder.append("\n");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
