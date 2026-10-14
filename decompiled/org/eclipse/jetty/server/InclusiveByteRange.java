/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InclusiveByteRange {
    private static final Logger LOG = LoggerFactory.getLogger(InclusiveByteRange.class);
    private long first;
    private long last;

    public InclusiveByteRange(long first, long last) {
        this.first = first;
        this.last = last;
    }

    public long getFirst() {
        return this.first;
    }

    public long getLast() {
        return this.last;
    }

    private void coalesce(InclusiveByteRange r) {
        this.first = Math.min(this.first, r.first);
        this.last = Math.max(this.last, r.last);
    }

    private boolean overlaps(InclusiveByteRange range) {
        return range.first >= this.first && range.first <= this.last || range.last >= this.first && range.last <= this.last || range.first < this.first && range.last > this.last;
    }

    public long getSize() {
        return this.last - this.first + 1L;
    }

    public String toHeaderRangeString(long size) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes ");
        sb.append(this.first);
        sb.append('-');
        sb.append(this.last);
        sb.append("/");
        sb.append(size);
        return sb.toString();
    }

    public int hashCode() {
        return (int)(this.first ^ this.last);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InclusiveByteRange)) {
            return false;
        }
        return ((InclusiveByteRange)obj).first == this.first && ((InclusiveByteRange)obj).last == this.last;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append(Long.toString(this.first));
        sb.append(":");
        sb.append(Long.toString(this.last));
        return sb.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<InclusiveByteRange> satisfiableRanges(Enumeration<String> headers, long size) {
        ArrayList<InclusiveByteRange> ranges = null;
        long end = size - 1L;
        block4: while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            StringTokenizer tok = new StringTokenizer(header, "=,", false);
            String t2 = null;
            try {
                while (true) {
                    if (!tok.hasMoreTokens()) continue block4;
                    try {
                        t2 = tok.nextToken().trim();
                        if ("bytes".equals(t2)) continue;
                        long first = -1L;
                        long last = -1L;
                        int dash = t2.indexOf(45);
                        if (dash < 0 || t2.indexOf("-", dash + 1) >= 0) {
                            ranges = null;
                            LOG.warn("Bad range format: {}", (Object)t2);
                            continue block4;
                        }
                        if (dash > 0) {
                            first = Long.parseLong(t2.substring(0, dash).trim());
                        }
                        if (dash < t2.length() - 1) {
                            last = Long.parseLong(t2.substring(dash + 1).trim());
                        }
                        if (first == -1L) {
                            if (last == -1L) {
                                ranges = null;
                                LOG.warn("Bad range format: {}", (Object)t2);
                                continue block4;
                            }
                            if (last == 0L) continue;
                            first = Math.max(0L, size - last);
                            last = end;
                        } else {
                            if (first >= size) continue;
                            if (last == -1L) {
                                last = end;
                            } else if (last >= end) {
                                last = end;
                            }
                        }
                        if (last < first) {
                            ranges = null;
                            LOG.warn("Bad range format: {}", (Object)t2);
                            continue block4;
                        }
                        InclusiveByteRange range = new InclusiveByteRange(first, last);
                        if (ranges == null) {
                            ranges = new ArrayList<InclusiveByteRange>();
                        }
                        boolean coalesced = false;
                        ListIterator i = ranges.listIterator();
                        while (i.hasNext()) {
                            InclusiveByteRange r = (InclusiveByteRange)i.next();
                            if (!range.overlaps(r)) continue;
                            coalesced = true;
                            r.coalesce(range);
                            while (i.hasNext()) {
                                InclusiveByteRange r2 = (InclusiveByteRange)i.next();
                                if (!r2.overlaps(r)) continue;
                                r.coalesce(r2);
                                i.remove();
                            }
                        }
                        if (coalesced) continue;
                        ranges.add(range);
                    }
                    catch (NumberFormatException e) {
                        ranges = null;
                        LOG.warn("Bad range format: {}", (Object)t2);
                        LOG.trace("IGNORED", e);
                    }
                }
            }
            catch (Exception e) {
                ranges = null;
                LOG.warn("Bad range format: {}", (Object)t2);
                LOG.trace("IGNORED", e);
                continue;
            }
            break;
        }
        return ranges;
    }

    public static String to416HeaderRangeString(long size) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes */");
        sb.append(size);
        return sb.toString();
    }
}

