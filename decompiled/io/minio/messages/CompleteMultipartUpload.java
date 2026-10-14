/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Part;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="CompleteMultipartUpload")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class CompleteMultipartUpload {
    @ElementList(name="Part", inline=true)
    private List<Part> partList;

    public CompleteMultipartUpload(Part[] parts) throws IllegalArgumentException {
        if (parts == null || parts.length == 0) {
            throw new IllegalArgumentException("null or empty parts");
        }
        this.partList = Collections.unmodifiableList(Arrays.asList(parts));
    }
}

