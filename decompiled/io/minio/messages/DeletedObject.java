/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Deleted", strict=false)
public class DeletedObject {
    @Element(name="Key")
    private String name;
    @Element(name="VersionId", required=false)
    private String versionId;
    @Element(name="DeleteMarker", required=false)
    private boolean deleteMarker;
    @Element(name="DeleteMarkerVersionId", required=false)
    private String deleteMarkerVersionId;

    public String name() {
        return this.name;
    }

    public String versionId() {
        return this.versionId;
    }

    public boolean deleteMarker() {
        return this.deleteMarker;
    }

    public String deleteMarkerVersionId() {
        return this.deleteMarkerVersionId;
    }
}

