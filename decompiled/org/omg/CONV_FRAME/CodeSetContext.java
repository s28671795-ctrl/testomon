/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CORBA.portable.IDLEntity;

public final class CodeSetContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int char_data;
    public int wchar_data;

    public CodeSetContext() {
    }

    public CodeSetContext(int char_data, int wchar_data) {
        this.char_data = char_data;
        this.wchar_data = wchar_data;
    }
}

