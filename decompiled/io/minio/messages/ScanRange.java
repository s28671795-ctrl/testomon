/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ScanRange")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class ScanRange {
    @Element(name="Start", required=false)
    private Long start;
    @Element(name="End", required=false)
    private Long end;

    public ScanRange(Long start2, Long end) {
        this.start = start2;
        this.end = end;
    }
}

