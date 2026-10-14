/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class QOP
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecQOPNoProtection = 0;
    public static final QOP SecQOPNoProtection = new QOP(0);
    public static final int _SecQOPIntegrity = 1;
    public static final QOP SecQOPIntegrity = new QOP(1);
    public static final int _SecQOPConfidentiality = 2;
    public static final QOP SecQOPConfidentiality = new QOP(2);
    public static final int _SecQOPIntegrityAndConfidentiality = 3;
    public static final QOP SecQOPIntegrityAndConfidentiality = new QOP(3);

    public int value() {
        return this.value;
    }

    public static QOP from_int(int value) {
        switch (value) {
            case 0: {
                return SecQOPNoProtection;
            }
            case 1: {
                return SecQOPIntegrity;
            }
            case 2: {
                return SecQOPConfidentiality;
            }
            case 3: {
                return SecQOPIntegrityAndConfidentiality;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecQOPNoProtection";
            }
            case 1: {
                return "SecQOPIntegrity";
            }
            case 2: {
                return "SecQOPConfidentiality";
            }
            case 3: {
                return "SecQOPIntegrityAndConfidentiality";
            }
        }
        throw new BAD_PARAM();
    }

    protected QOP(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return QOP.from_int(this.value());
    }
}

