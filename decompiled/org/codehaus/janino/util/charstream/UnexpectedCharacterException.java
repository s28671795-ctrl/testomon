/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util.charstream;

import java.io.IOException;

public class UnexpectedCharacterException
extends IOException {
    private static final long serialVersionUID = 1L;

    public UnexpectedCharacterException() {
    }

    public UnexpectedCharacterException(String message) {
        super(message);
    }
}

