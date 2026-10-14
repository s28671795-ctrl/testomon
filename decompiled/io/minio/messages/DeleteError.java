/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ErrorResponse;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="Error", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class DeleteError
extends ErrorResponse {
    private static final long serialVersionUID = 1905162041950251407L;
}

