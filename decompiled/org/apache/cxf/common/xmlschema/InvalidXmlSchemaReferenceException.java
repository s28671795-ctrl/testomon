/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.xmlschema;

public class InvalidXmlSchemaReferenceException
extends RuntimeException {
    private static final long serialVersionUID = -2053958361555768873L;

    public InvalidXmlSchemaReferenceException() {
    }

    public InvalidXmlSchemaReferenceException(String message) {
        super(message);
    }

    public InvalidXmlSchemaReferenceException(Throwable cause) {
        super(cause);
    }

    public InvalidXmlSchemaReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

