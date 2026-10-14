/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Initiator;
import io.minio.messages.Owner;
import io.minio.messages.Part;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ListPartsResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ListPartsResult {
    @Element(name="Bucket")
    private String bucketName;
    @Element(name="Key")
    private String objectName;
    @Element(name="Initiator")
    private Initiator initiator;
    @Element(name="Owner")
    private Owner owner;
    @Element(name="StorageClass")
    private String storageClass;
    @Element(name="PartNumberMarker")
    private int partNumberMarker;
    @Element(name="NextPartNumberMarker")
    private int nextPartNumberMarker;
    @Element(name="MaxParts")
    private int maxParts;
    @Element(name="IsTruncated")
    private boolean isTruncated;
    @ElementList(name="Part", inline=true, required=false)
    private List<Part> partList;

    public String bucketName() {
        return this.bucketName;
    }

    public String objectName() {
        return this.objectName;
    }

    public String storageClass() {
        return this.storageClass;
    }

    public Initiator initiator() {
        return this.initiator;
    }

    public Owner owner() {
        return this.owner;
    }

    public int maxParts() {
        return this.maxParts;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public int partNumberMarker() {
        return this.partNumberMarker;
    }

    public int nextPartNumberMarker() {
        return this.nextPartNumberMarker;
    }

    public List<Part> partList() {
        if (this.partList == null) {
            return Collections.unmodifiableList(new LinkedList());
        }
        return Collections.unmodifiableList(this.partList);
    }
}

