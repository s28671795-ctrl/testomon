/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.DeleteObject;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="Delete")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class DeleteRequest {
    @Element(name="Quiet", required=false)
    private boolean quiet;
    @ElementList(name="Object", inline=true)
    private List<DeleteObject> objectList;

    public DeleteRequest(List<DeleteObject> objectList, boolean quiet) {
        this.objectList = Collections.unmodifiableList(objectList);
        this.quiet = quiet;
    }
}

