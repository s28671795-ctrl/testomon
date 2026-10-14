/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.CopyObjectResult;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="CopyPartResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class CopyPartResult
extends CopyObjectResult {
}

