package com.github.rhino10001.unosoft;

import com.github.rhino10001.unosoft.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationTest {

    @Test
    public void givenEmptyWordInLine_whenIsValid_returnsTrue() {
        String given = "\"\"";
        String[] split = given.split(";");
        Assertions.assertTrue(FileUtils.isValid(split));
    }

    @Test
    public void givenOneWordInLine_whenIsValid_returnsTrue() {
        String given = "\"79220239511\"";
        String[] split = given.split(";");
        Assertions.assertTrue(FileUtils.isValid(split));
    }

    @Test
    public void givenSeveralWordsInLine_whenIsValid_returnsTrue() {
        String given = "\"\";\"79076513686\";\"79499289445\";\"79895211259\";\"79970144607\";\"79460148141\";\"79124811542\";\"79660572200\";\"79245307223\";\"79220239511\"";
        String[] split = given.split(";");
        Assertions.assertTrue(FileUtils.isValid(split));
    }

    @Test
    public void givenOneWordInLine_whenIsValid_returnsFalse() {
        String given = "\"8383\"200000741652251\"";
        String[] split = given.split(";");

        Assertions.assertFalse(FileUtils.isValid(split));
    }

    @Test
    public void givenSeveralWordsInLine_whenIsValid_returnsFalse() {
        String given = "\"79855053897\"83100000580443402\";\"200000133000191\"";
        String[] split = given.split(";");

        Assertions.assertFalse(FileUtils.isValid(split));
    }
}
