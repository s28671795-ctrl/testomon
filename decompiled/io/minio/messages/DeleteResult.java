/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DeleteError;
import io.minio.messages.DeletedObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="DeleteResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class DeleteResult {
    @ElementList(name="Deleted", inline=true, required=false)
    private List<DeletedObject> objectList;
    @ElementList(name="Error", inline=true, required=false)
    private List<DeleteError> errorList;

    public DeleteResult() {
    }

    public DeleteResult(DeleteError error) {
        this.errorList = new LinkedList<DeleteError>();
        this.errorList.add(error);
    }

    public List<DeletedObject> objectList() {
        if (this.objectList == null) {
            return Collections.unmodifiableList(new LinkedList());
        }
        return Collections.unmodifiableList(this.objectList);
    }

    public List<DeleteError> errorList() {
        if (this.errorList == null) {
            return Collections.unmodifiableList(new LinkedList());
        }
        return Collections.unmodifiableList(this.errorList);
    }
}

