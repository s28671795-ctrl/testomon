/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name="LocationConstraint", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class LocationConstraint {
    @Text(required=false)
    private String location = "";

    public String location() {
        return this.location;
    }
}

