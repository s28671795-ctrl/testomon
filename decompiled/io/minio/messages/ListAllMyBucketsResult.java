/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Bucket;
import io.minio.messages.Owner;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ListAllMyBucketsResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ListAllMyBucketsResult {
    @Element(name="Owner")
    private Owner owner;
    @ElementList(name="Buckets")
    private List<Bucket> buckets;

    public Owner owner() {
        return this.owner;
    }

    public List<Bucket> buckets() {
        if (this.buckets == null) {
            return Collections.unmodifiableList(new LinkedList());
        }
        return Collections.unmodifiableList(this.buckets);
    }
}

