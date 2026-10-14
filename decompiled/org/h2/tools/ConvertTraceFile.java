/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.StringUtils;
import org.h2.util.Tool;

public class ConvertTraceFile
extends Tool {
    private final HashMap<String, Stat> stats = new HashMap();
    private long timeTotal;

    public static void main(String ... stringArray) throws SQLException {
        new ConvertTraceFile().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = "test.trace.db";
        String string2 = "Test";
        String string3 = "test.sql";
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string4 = stringArray[i];
            if (string4.equals("-traceFile")) {
                string = stringArray[++i];
                continue;
            }
            if (string4.equals("-javaClass")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string4.equals("-script")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string4.equals("-help") || string4.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string4);
        }
        try {
            this.convertFile(string, string2, string3);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, string);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void convertFile(String string, String string2, String string3) throws IOException {
        String string4;
        LineNumberReader lineNumberReader = new LineNumberReader(IOUtils.getReader(FileUtils.newInputStream(string)));
        PrintWriter printWriter = new PrintWriter(IOUtils.getBufferedWriter(FileUtils.newOutputStream(string2 + ".java", false)));
        PrintWriter printWriter2 = new PrintWriter(IOUtils.getBufferedWriter(FileUtils.newOutputStream(string3, false)));
        printWriter.println("import java.io.*;");
        printWriter.println("import java.sql.*;");
        printWriter.println("import java.math.*;");
        printWriter.println("import java.util.Calendar;");
        String string5 = string2.replace('\\', '/');
        int n = string5.lastIndexOf(47);
        if (n > 0) {
            string5 = string5.substring(n + 1);
        }
        printWriter.println("public class " + string5 + " {");
        printWriter.println("    public static void main(String... args) throws Exception {");
        printWriter.println("        Class.forName(\"org.h2.Driver\");");
        while ((string4 = lineNumberReader.readLine()) != null) {
            if (string4.startsWith("/**/")) {
                string4 = "        " + string4.substring(4);
                printWriter.println(string4);
                continue;
            }
            if (!string4.startsWith("/*SQL")) continue;
            int n2 = string4.indexOf("*/");
            Object object = string4.substring(n2 + "*/".length());
            object = StringUtils.javaDecode((String)object);
            if ((string4 = string4.substring("/*SQL".length(), n2)).length() > 0) {
                void object2;
                String string6 = object;
                int n3 = 0;
                long l = 0L;
                if ((string4 = string4.trim()).length() > 0) {
                    StringTokenizer stringTokenizer = new StringTokenizer(string4, " :");
                    while (stringTokenizer.hasMoreElements()) {
                        String string7 = stringTokenizer.nextToken();
                        if ("l".equals(string7)) {
                            int n4 = Integer.parseInt(stringTokenizer.nextToken());
                            String string8 = ((String)object).substring(0, n4) + ";";
                            continue;
                        }
                        if ("#".equals(string7)) {
                            n3 = Integer.parseInt(stringTokenizer.nextToken());
                            continue;
                        }
                        if (!"t".equals(string7)) continue;
                        l = Long.parseLong(stringTokenizer.nextToken());
                    }
                }
                this.addToStats((String)object2, n3, l);
            }
            printWriter2.println((String)object);
        }
        printWriter.println("    }");
        printWriter.println('}');
        lineNumberReader.close();
        printWriter.close();
        if (this.stats.size() > 0) {
            printWriter2.println("-----------------------------------------");
            printWriter2.println("-- SQL Statement Statistics");
            printWriter2.println("-- time: total time in milliseconds (accumulated)");
            printWriter2.println("-- count: how many times the statement ran");
            printWriter2.println("-- result: total update count or row count");
            printWriter2.println("-----------------------------------------");
            printWriter2.println("-- self accu    time   count  result sql");
            int n5 = 0;
            ArrayList<Stat> arrayList = new ArrayList<Stat>(this.stats.values());
            Collections.sort(arrayList);
            if (this.timeTotal == 0L) {
                this.timeTotal = 1L;
            }
            for (Stat stat : arrayList) {
                n5 = (int)((long)n5 + stat.time);
                StringBuilder stringBuilder = new StringBuilder(100);
                stringBuilder.append("-- ").append(ConvertTraceFile.padNumberLeft(100L * stat.time / this.timeTotal, 3)).append("% ").append(ConvertTraceFile.padNumberLeft((long)(100 * n5) / this.timeTotal, 3)).append('%').append(ConvertTraceFile.padNumberLeft(stat.time, 8)).append(ConvertTraceFile.padNumberLeft(stat.executeCount, 8)).append(ConvertTraceFile.padNumberLeft(stat.resultCount, 8)).append(' ').append(ConvertTraceFile.removeNewlines(stat.sql));
                printWriter2.println(stringBuilder.toString());
            }
        }
        printWriter2.close();
    }

    private static String removeNewlines(String string) {
        return string == null ? string : string.replace('\r', ' ').replace('\n', ' ');
    }

    private static String padNumberLeft(long l, int n) {
        return StringUtils.pad(Long.toString(l), n, " ", false);
    }

    private void addToStats(String string, int n, long l) {
        Stat stat = this.stats.get(string);
        if (stat == null) {
            stat = new Stat();
            stat.sql = string;
            this.stats.put(string, stat);
        }
        ++stat.executeCount;
        stat.resultCount += (long)n;
        stat.time += l;
        this.timeTotal += l;
    }

    static class Stat
    implements Comparable<Stat> {
        String sql;
        int executeCount;
        long time;
        long resultCount;

        Stat() {
        }

        @Override
        public int compareTo(Stat stat) {
            if (stat == this) {
                return 0;
            }
            int n = Long.compare(stat.time, this.time);
            if (n == 0 && (n = Integer.compare(stat.executeCount, this.executeCount)) == 0) {
                n = this.sql.compareTo(stat.sql);
            }
            return n;
        }
    }
}

