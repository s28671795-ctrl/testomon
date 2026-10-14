/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.portable.InputStream;

public class ApplicationException
extends Exception {
    private String _id;
    private InputStream _is;

    public ApplicationException(String id, InputStream is) {
        this._id = id;
        this._is = is;
    }

    public String getId() {
        return this._id;
    }

    public InputStream getInputStream() {
        return this._is;
    }
}

