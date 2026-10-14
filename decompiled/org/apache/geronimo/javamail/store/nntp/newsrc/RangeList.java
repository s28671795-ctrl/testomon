/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp.newsrc;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.geronimo.javamail.store.nntp.newsrc.Range;

public class RangeList {
    boolean dirty = false;
    ArrayList ranges = new ArrayList();

    public RangeList(String line) {
        if (line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, ",");
            while (tokenizer.hasMoreTokens()) {
                Range range;
                String rangeString = tokenizer.nextToken();
                if ((rangeString = rangeString.trim()).length() == 0 || (range = Range.parse(rangeString)) == null) continue;
                this.insert(range);
            }
        }
        this.dirty = false;
    }

    public void insert(Range newRange) {
        for (int i = 0; i < this.ranges.size(); ++i) {
            Range range = (Range)this.ranges.get(i);
            if (range.contains(newRange)) {
                return;
            }
            if (range.abutts(newRange) || range.overlaps(newRange)) {
                this.dirty = true;
                newRange.merge(range);
                this.ranges.remove(i);
                this.insert(newRange);
                return;
            }
            if (!newRange.lessThan(range)) continue;
            this.dirty = true;
            this.ranges.add(i, newRange);
            return;
        }
        this.dirty = true;
        this.ranges.add(newRange);
    }

    public boolean isMarked(int article) {
        for (int i = 0; i < this.ranges.size(); ++i) {
            Range range = (Range)this.ranges.get(i);
            if (range.contains(article)) {
                return true;
            }
            if (!range.greaterThan(article)) continue;
            return false;
        }
        return false;
    }

    public void setMarked(int article) {
        this.insert(new Range(article, article));
    }

    public void setUnmarked(int article) {
        for (int i = 0; i < this.ranges.size(); ++i) {
            Range range = (Range)this.ranges.get(i);
            if (range.contains(article)) {
                if (range.getStart() == article) {
                    if (range.getEnd() == article) {
                        this.ranges.remove(i);
                    } else {
                        range.setStart(article + 1);
                    }
                } else if (range.getEnd() == article) {
                    range.setEnd(article - 1);
                } else {
                    Range section = range.split(article);
                    this.ranges.add(i + 1, section);
                }
                this.dirty = true;
                return;
            }
            if (!range.greaterThan(article)) continue;
            return;
        }
    }

    public void save(Writer out) throws IOException {
        if (this.ranges.size() == 0) {
            return;
        }
        Range range = (Range)this.ranges.get(0);
        range.save(out);
        for (int i = 1; i < this.ranges.size(); ++i) {
            out.write(",");
            range = (Range)this.ranges.get(i);
            range.save(out);
        }
    }

    public boolean isDirty() {
        return this.dirty;
    }
}

