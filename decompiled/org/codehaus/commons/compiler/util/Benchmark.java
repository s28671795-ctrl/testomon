/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import java.util.Stack;
import org.codehaus.commons.nullanalysis.Nullable;

public class Benchmark {
    private final Stack<Long> beginTimes = new Stack();
    private final boolean reportingEnabled;
    private final Reporter reporter;
    private static final String PAD = "                       ";

    public void begin() {
        this.beginTimes.push(System.currentTimeMillis());
    }

    public long end() {
        return System.currentTimeMillis() - this.beginTimes.pop();
    }

    public Benchmark(boolean reportingEnabled) {
        this.reportingEnabled = reportingEnabled;
        this.reporter = new Reporter(){

            @Override
            public void report(String message) {
                System.out.println(message);
            }
        };
    }

    public Benchmark(boolean reportingEnabled, Reporter reporter) {
        this.reportingEnabled = reportingEnabled;
        this.reporter = reporter;
    }

    public void beginReporting() {
        if (!this.reportingEnabled) {
            return;
        }
        this.reportIndented("Beginning...");
        this.begin();
    }

    public void beginReporting(String message) {
        if (!this.reportingEnabled) {
            return;
        }
        this.reportIndented(message + "...");
        this.begin();
    }

    public void endReporting() {
        if (!this.reportingEnabled) {
            return;
        }
        this.reportIndented("... took " + this.end() + " ms");
    }

    public void endReporting(String message) {
        if (!this.reportingEnabled) {
            return;
        }
        this.reportIndented("... took " + this.end() + " ms: " + message);
    }

    public void report(String message) {
        if (!this.reportingEnabled) {
            return;
        }
        this.reportIndented(message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void report(@Nullable String title, @Nullable Object o) {
        String prefix;
        if (!this.reportingEnabled) {
            return;
        }
        String string = title == null ? "" : (prefix = title + ": " + (title.length() < PAD.length() ? PAD.substring(title.length()) : ""));
        if (o == null) {
            this.reportIndented(prefix + "(undefined)");
        } else if (o.getClass().isArray()) {
            Object[] oa = (Object[])o;
            if (oa.length == 0) {
                this.reportIndented(prefix + "(empty)");
            } else if (oa.length == 1) {
                this.reportIndented(prefix + oa[0].toString());
            } else {
                this.reportIndented(title == null ? "Array:" : title + ':');
                this.begin();
                try {
                    for (Object o2 : oa) {
                        this.report(null, o2);
                    }
                }
                finally {
                    this.end();
                }
            }
        } else {
            this.reportIndented(prefix + o.toString());
        }
    }

    private void reportIndented(String message) {
        StringBuilder sb = new StringBuilder();
        for (int i = this.beginTimes.size(); i > 0; --i) {
            sb.append("  ");
        }
        sb.append(message);
        this.reporter.report(sb.toString());
    }

    public static interface Reporter {
        public void report(String var1);
    }
}

