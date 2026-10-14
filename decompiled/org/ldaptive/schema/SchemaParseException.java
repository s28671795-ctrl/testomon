/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import org.ldaptive.LdapException;

public class SchemaParseException
extends LdapException {
    private static final long serialVersionUID = -5214120370570326233L;

    public SchemaParseException(String msg) {
        super(msg);
    }

    public SchemaParseException(Throwable e) {
        super(e);
    }

    public SchemaParseException(String msg, Throwable e) {
        super(msg, e);
    }
}

