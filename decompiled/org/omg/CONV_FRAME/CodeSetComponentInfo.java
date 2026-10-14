/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CONV_FRAME.CodeSetComponent;
import org.omg.CORBA.portable.IDLEntity;

public final class CodeSetComponentInfo
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public CodeSetComponent ForCharData;
    public CodeSetComponent ForWcharData;

    public CodeSetComponentInfo() {
    }

    public CodeSetComponentInfo(CodeSetComponent ForCharData, CodeSetComponent ForWcharData) {
        this.ForCharData = ForCharData;
        this.ForWcharData = ForWcharData;
    }
}

