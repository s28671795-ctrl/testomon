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
public class ListBucketResultV2
extends ListObjectsResult {
    @Element(name="KeyCount", required=false)
    private int keyCount;
    @Element(name="StartAfter", required=false)
    private String startAfter;
    @Element(name="ContinuationToken", required=false)
    private String continuationToken;
    @Element(name="NextContinuationToken", required=false)
    private String nextContinuationToken;
    @ElementList(name="Contents", inline=true, required=false)
    private List<Contents> contents;

    public int keyCount() {
        return this.keyCount;
    }

    public String startAfter() {
        return this.decodeIfNeeded(this.startAfter);
    }

    public String continuationToken() {
        return this.continuationToken;
    }

    public String nextContinuationToken() {
        return this.nextContinuationToken;
    }

    public List<Contents> contents() {
        return this.emptyIfNull(this.contents);
    }
}

