/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp.newsrc;

import java.io.IOException;
import java.io.Writer;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrc;
import org.apache.geronimo.javamail.store.nntp.newsrc.RangeList;

public class NNTPNewsrcGroup {
    NNTPNewsrc newsrc;
    protected String name;
    protected boolean subscribed;
    protected RangeList ranges;

    public static NNTPNewsrcGroup parse(NNTPNewsrc newsrc, String line) {
        String groupName = null;
        String ranges = null;
        int marker = line.indexOf(58);
        if (marker != -1) {
            groupName = line.substring(0, marker);
            ranges = line.substring(marker + 1);
            return new NNTPNewsrcGroup(newsrc, groupName, ranges, true);
        }
        marker = line.indexOf(33);
        if (marker != -1) {
            groupName = line.substring(0, marker);
            ranges = line.substring(marker + 1);
            return new NNTPNewsrcGroup(newsrc, groupName, ranges, false);
        }
        return null;
    }

    public NNTPNewsrcGroup(NNTPNewsrc newsrc, String name, String newsrcRanges, boolean subscribed) {
        this.newsrc = newsrc;
        this.name = name;
        this.subscribed = subscribed;
        this.ranges = new RangeList(newsrcRanges);
    }

    public String getName() {
        return this.name;
    }

    public boolean isSubscribed() {
        return this.subscribed;
    }

    public void setSubscribed(boolean flag) {
        if (flag && !this.subscribed) {
            this.subscribed = true;
            this.newsrc.setDirty();
        } else if (!flag && this.subscribed) {
            this.subscribed = false;
            this.newsrc.setDirty();
        }
    }

    public boolean isArticleSeen(int article) {
        return this.ranges.isMarked(article);
    }

    public void markArticleSeen(int article) {
        this.ranges.setMarked(article);
        if (this.ranges.isDirty()) {
            this.newsrc.setDirty();
        }
    }

    public void markArticleUnseen(int article) {
        this.ranges.setUnmarked(article);
        if (this.ranges.isDirty()) {
            this.newsrc.setDirty();
        }
    }

    public void save(Writer out) throws IOException {
        out.write(this.name);
        out.write(this.subscribed ? ": " : "! ");
        this.ranges.save(out);
        out.write("\r\n");
    }
}

