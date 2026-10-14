/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import liquibase.exception.LiquibaseException;
import liquibase.util.StringUtil;

public class TableOutput {
    public static void formatUnwrappedOutput(List<List<String>> table, boolean leftJustifiedRows, Writer writer) throws LiquibaseException {
        TableOutput.formatOutput(table, TableOutput.computeMaxWidths(table), leftJustifiedRows, writer);
    }

    public static void formatOutput(List<List<String>> table, List<Integer> maxWidths, boolean leftJustifiedRows, Writer writer) throws LiquibaseException {
        TableOutput.formatOutput((String[][])table.stream().map(u -> u.toArray(new String[0])).toArray(x$0 -> new String[x$0][]), maxWidths, leftJustifiedRows, writer);
    }

    public static void formatOutput(List<List<String>> table, int[] maxWidths, boolean leftJustifiedRows, Writer writer) throws LiquibaseException {
        TableOutput.formatOutput((String[][])table.stream().map(u -> u.toArray(new String[0])).toArray(x$0 -> new String[x$0][]), maxWidths, leftJustifiedRows, writer);
    }

    public static void formatOutput(String[][] table, int[] maxWidths, boolean leftJustifiedRows, Writer writer) throws LiquibaseException {
        TableOutput.formatOutput(table, IntStream.of(maxWidths).boxed().collect(Collectors.toList()), leftJustifiedRows, writer);
    }

    public static void formatOutput(String[][] table, List<Integer> maxWidths, boolean leftJustifiedRows, Writer writer) throws LiquibaseException {
        int defaultMaxWidth = 30;
        if (table[0].length != maxWidths.size()) {
            throw new RuntimeException("Table and maximum widths arrays must be the same length");
        }
        for (int i = 0; i < maxWidths.size(); ++i) {
            if (maxWidths.get(i) != 0) continue;
            maxWidths.set(i, defaultMaxWidth);
        }
        StringBuilder outputLines = new StringBuilder();
        ArrayList tableList = new ArrayList(Arrays.asList(table));
        ArrayList<String[]> finalTableList = new ArrayList<String[]>();
        for (String[] row : tableList) {
            for (int i = 0; i < row.length; ++i) {
                row[i] = TableOutput.padColumn(row[i], maxWidths.get(i));
            }
            boolean isMultiLine = false;
            int multiLine = 0;
            boolean endLineAdded = false;
            do {
                isMultiLine = false;
                String[] newRow = new String[row.length];
                for (int i = 0; i < row.length; ++i) {
                    if (row[i] == null || row[i].length() < maxWidths.get(i)) {
                        if (multiLine == 0) {
                            newRow[i] = row[i];
                            continue;
                        }
                        newRow[i] = "";
                        endLineAdded = true;
                        continue;
                    }
                    if (row[i].length() > multiLine * maxWidths.get(i)) {
                        int end = Math.min(row[i].length(), multiLine * maxWidths.get(i) + maxWidths.get(i));
                        newRow[i] = row[i].substring(multiLine * maxWidths.get(i), end);
                        isMultiLine = true;
                        continue;
                    }
                    newRow[i] = "";
                    endLineAdded = true;
                }
                finalTableList.add(newRow);
                if (!isMultiLine) continue;
                ++multiLine;
            } while (isMultiLine);
            if (endLineAdded) continue;
            String[] emptyCells = Collections.nCopies(row.length, "").toArray(new String[row.length]);
            finalTableList.add(emptyCells);
        }
        String[][] finalTable = new String[finalTableList.size()][((String[])finalTableList.get(0)).length];
        for (int i = 0; i < finalTable.length; ++i) {
            finalTable[i] = (String[])finalTableList.get(i);
        }
        List<Integer> columnLengths = TableOutput.computeMaxWidths(finalTable);
        StringBuilder formatString = new StringBuilder();
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.forEach(value -> formatString.append("| %" + flag + value + "s "));
        formatString.append("|\n");
        StringBuilder builder = new StringBuilder();
        for (Integer columnLength : columnLengths) {
            builder.append("+-");
            builder.append(String.join((CharSequence)"", Collections.nCopies(columnLength, "-")));
            builder.append("-");
        }
        String line = builder.append("+\n").toString();
        outputLines.append(line);
        for (String[] strings : finalTable) {
            if (TableOutput.allEmptyStrings(strings)) {
                outputLines.append(line);
                continue;
            }
            outputLines.append(String.format(formatString.toString(), strings));
        }
        try {
            writer.append(outputLines.toString());
            writer.flush();
        }
        catch (IOException ioe) {
            throw new LiquibaseException(ioe);
        }
    }

    public static List<Integer> computeMaxWidths(List<List<String>> rows) {
        return TableOutput.computeMaxWidths((String[][])rows.stream().map(row -> row.toArray(new String[0])).toArray(x$0 -> new String[x$0][]));
    }

    public static List<Integer> computeMaxWidths(String[][] rows) {
        if (rows.length == 0) {
            return Collections.emptyList();
        }
        int columnCount = rows[0].length;
        ArrayList<Integer> widths = new ArrayList<Integer>(Collections.nCopies(columnCount, 0));
        for (String[] row : rows) {
            if (row.length != columnCount) {
                throw new RuntimeException(String.format("could not compute table width: heterogeneous tables are not supported. Expected each row to have %d column(s), found %d", columnCount, row.length));
            }
            for (int i = 0; i < row.length; ++i) {
                String column = row[i];
                if (column == null || column.length() <= (Integer)widths.get(i)) continue;
                widths.set(i, column.length());
            }
        }
        return widths;
    }

    private static boolean allEmptyStrings(String[] strings) {
        return Arrays.stream(strings).allMatch(StringUtil::isEmpty);
    }

    private static String padColumn(String col, int maxWidth) {
        if (col == null || col.length() <= maxWidth && !col.contains(System.lineSeparator())) {
            return col;
        }
        String[] parts = col.split(" ");
        int runningWidth = 0;
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            String[] lineSplitParts = part.split(System.lineSeparator());
            if (lineSplitParts.length > 1) {
                for (int i = 0; i < lineSplitParts.length; ++i) {
                    String lineSplitPart = lineSplitParts[i];
                    runningWidth = TableOutput.doAppend(runningWidth, lineSplitPart, maxWidth, result);
                    if (i == lineSplitParts.length - 1) continue;
                    runningWidth = TableOutput.fillLineWithSpaces(runningWidth, maxWidth, result);
                }
                continue;
            }
            runningWidth = TableOutput.doAppend(runningWidth, part, maxWidth, result);
        }
        return result.toString();
    }

    private static int doAppend(int runningWidth, String part, int maxWidth, StringBuilder result) {
        int spaceWidth;
        if (runningWidth > maxWidth) {
            runningWidth %= maxWidth;
        }
        int n = spaceWidth = runningWidth > 0 ? 1 : 0;
        if (runningWidth + (part.length() + spaceWidth) > maxWidth) {
            runningWidth = TableOutput.fillLineWithSpaces(runningWidth, maxWidth, result);
        }
        if (runningWidth > 0) {
            result.append(" ");
            ++runningWidth;
        }
        result.append(part);
        return runningWidth += part.length();
    }

    private static int fillLineWithSpaces(int runningWidth, int maxWidth, StringBuilder result) {
        for (int i = 0; i < maxWidth - runningWidth; ++i) {
            result.append(" ");
        }
        return 0;
    }
}

