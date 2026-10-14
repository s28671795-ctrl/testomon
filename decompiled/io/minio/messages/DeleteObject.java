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

@Root(name="Object")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class DeleteObject {
    @Element(name="Key")
    private String name;
    @Element(name="VersionId", required=false)
    private String versionId;

    public DeleteObject(String name) {
        this.name = name;
    }

    public DeleteObject(String name, String versionId) {
        this.name = name;
        this.versionId = versionId;
    }
}

