package com.github.rhino10001.unosoft.utils;

public class RuntimeUtils {

    public static void printTotalMemory() {
        System.out.println("Total memory, (MB): " + Runtime.getRuntime().totalMemory() / 1024 / 1024);
    }

    public static void printMaxMemory() {
        System.out.println("Max memory, (MB): " + Runtime.getRuntime().maxMemory() / 1024 / 1024);
    }

    public static void printFreeMemory() {
        System.out.println("Free memory, (MB): " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }

    public static void printMemoryUsage() {
        System.out.println("Memory usage, (MB): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
    }

    public static void printMemoryInfo() {
        printTotalMemory();
        printMaxMemory();
        printFreeMemory();
        printMemoryUsage();
    }
}
