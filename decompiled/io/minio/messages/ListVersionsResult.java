/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DeleteMarker;
import io.minio.messages.ListObjectsResult;
import io.minio.messages.Version;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ListVersionsResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ListVersionsResult
extends ListObjectsResult {
    @Element(name="KeyMarker", required=false)
    private String keyMarker;
    @Element(name="NextKeyMarker", required=false)
    private String nextKeyMarker;
    @Element(name="VersionIdMarker", required=false)
    private String versionIdMarker;
    @Element(name="NextVersionIdMarker", required=false)
    private String nextVersionIdMarker;
    @ElementList(name="Version", inline=true, required=false)
    private List<Version> contents;
    @ElementList(name="DeleteMarker", inline=true, required=false)
    private List<DeleteMarker> deleteMarkers;

    public String keyMarker() {
        return this.decodeIfNeeded(this.keyMarker);
    }

    public String nextKeyMarker() {
        return this.decodeIfNeeded(this.nextKeyMarker);
    }

    public String versionIdMarker() {
        return this.versionIdMarker;
    }

    public String nextVersionIdMarker() {
        return this.nextVersionIdMarker;
    }

    public List<Version> contents() {
        return this.emptyIfNull(this.contents);
    }

    @Override
    public List<DeleteMarker> deleteMarkers() {
        return this.emptyIfNull(this.deleteMarkers);
    }
}

