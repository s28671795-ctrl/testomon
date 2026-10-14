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

@Root(name="RequestProgress", strict=false)
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class RequestProgress {
    @Element(name="Enabled")
    private boolean enabled = true;
}

