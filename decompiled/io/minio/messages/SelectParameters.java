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
import io.minio.messages.SelectObjectContentRequestBase;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Root;

@Root(name="SelectParameters")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class SelectParameters
extends SelectObjectContentRequestBase {
    public SelectParameters(@Nonnull String expression, @Nonnull InputSerialization is, @Nonnull OutputSerialization os) {
        super(expression, is, os);
    }
}

