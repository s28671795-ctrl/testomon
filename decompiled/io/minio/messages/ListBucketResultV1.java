/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Contents;
import io.minio.messages.ListObjectsResult;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ListBucketResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ListBucketResultV1
extends ListObjectsResult {
    @Element(name="Marker", required=false)
    private String marker;
    @Element(name="NextMarker", required=false)
    private String nextMarker;
    @ElementList(name="Contents", inline=true, required=false)
    private List<Contents> contents;

    public String marker() {
        return this.decodeIfNeeded(this.marker);
    }

    public String nextMarker() {
        return this.decodeIfNeeded(this.nextMarker);
    }

    public List<Contents> contents() {
        return this.emptyIfNull(this.contents);
    }
}

