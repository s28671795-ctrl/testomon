/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CORBA.portable.IDLEntity;

public final class CodeSetComponent
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int native_code_set;
    public int[] conversion_code_sets;

    public CodeSetComponent() {
    }

    public CodeSetComponent(int native_code_set, int[] conversion_code_sets) {
        this.native_code_set = native_code_set;
        this.conversion_code_sets = conversion_code_sets;
    }
}

