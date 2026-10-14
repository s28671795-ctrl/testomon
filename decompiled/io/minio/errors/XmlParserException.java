/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class XmlParserException
extends MinioException {
    private static final long serialVersionUID = -3877568719271880309L;

    public XmlParserException(Exception e) {
        super(e.toString());
    }
}

