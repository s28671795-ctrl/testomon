/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="CreateBucketConfiguration")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class CreateBucketConfiguration {
    @Element(name="LocationConstraint")
    private String locationConstraint;

    public CreateBucketConfiguration(String locationConstraint) {
        this.locationConstraint = locationConstraint;
    }
}

