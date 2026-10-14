/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.InputSerialization;
import io.minio.messages.OutputSerialization;
import io.minio.messages.RequestProgress;
import io.minio.messages.ScanRange;
import io.minio.messages.SelectObjectContentRequestBase;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="SelectObjectContentRequest")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class SelectObjectContentRequest
extends SelectObjectContentRequestBase {
    @Element(name="RequestProgress", required=false)
    private RequestProgress requestProgress;
    @Element(name="ScanRange", required=false)
    private ScanRange scanRange;

    public SelectObjectContentRequest(@Nonnull String expression, boolean requestProgress, @Nonnull InputSerialization is, @Nonnull OutputSerialization os, @Nullable Long scanStartRange, @Nullable Long scanEndRange) {
        super(expression, is, os);
        if (requestProgress) {
            this.requestProgress = new RequestProgress();
        }
        if (scanStartRange != null || scanEndRange != null) {
            this.scanRange = new ScanRange(scanStartRange, scanEndRange);
        }
    }
}

