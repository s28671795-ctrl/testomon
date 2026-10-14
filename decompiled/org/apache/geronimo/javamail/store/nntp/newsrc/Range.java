/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp.newsrc;

import java.io.IOException;
import java.io.Writer;

public class Range {
    int start;
    int end;

    public Range(int spot) {
        this(spot, spot);
    }

    public Range(int start2, int end) {
        this.start = start2;
        this.end = end;
    }

    public static Range parse(String range) {
        block4: {
            int marker = range.indexOf(45);
            try {
                if (marker != -1) {
                    String rangeStart = range.substring(0, marker).trim();
                    String rangeEnd = range.substring(marker + 1).trim();
                    int start2 = Integer.parseInt(rangeStart);
                    int end = Integer.parseInt(rangeEnd);
                    if (start2 >= 0 && end >= 0) {
                        return new Range(start2, end);
                    }
                    break block4;
                }
                int start3 = Integer.parseInt(range);
                return new Range(start3, start3);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start2) {
        this.start = start2;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean contains(int target) {
        return target >= this.start && target <= this.end;
    }

    public boolean contains(Range other) {
        return this.contains(other.getStart()) && this.contains(other.getEnd());
    }

    public boolean overlaps(Range other) {
        return other.contains(this.start) || other.contains(this.end) || this.contains(other.getStart()) || this.contains(other.getEnd());
    }

    public boolean abutts(Range other) {
        return other.getStart() == this.end + 1 || other.getEnd() == this.start - 1;
    }

    public boolean abutts(int article) {
        return article == this.start - 1 || article == this.end + 1;
    }

    public boolean lessThan(int article) {
        return this.end < article;
    }

    public boolean lessThan(Range other) {
        return this.end < other.start;
    }

    public boolean greaterThan(int article) {
        return this.start > article;
    }

    public boolean greaterThan(Range other) {
        return this.start > other.end;
    }

    public void merge(Range other) {
        if (other.start < this.start) {
            this.start = other.start;
        }
        if (other.end > this.end) {
            this.end = other.end;
        }
    }

    public Range split(int location) {
        int newEnd = this.end;
        this.end = location - 1;
        return new Range(location + 1, newEnd);
    }

    public void save(Writer out) throws IOException {
        if (this.start == this.end) {
            out.write(Integer.toString(this.start));
        } else {
            out.write(Integer.toString(this.start));
            out.write("-");
            out.write(Integer.toString(this.end));
        }
    }

    public String toString() {
        if (this.start == this.end) {
            return Integer.toString(this.start);
        }
        return Integer.toString(this.start) + "-" + Integer.toString(this.end);
    }
}

