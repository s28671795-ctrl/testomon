/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class AssociationStatus
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecAssocSuccess = 0;
    public static final AssociationStatus SecAssocSuccess = new AssociationStatus(0);
    public static final int _SecAssocFailure = 1;
    public static final AssociationStatus SecAssocFailure = new AssociationStatus(1);
    public static final int _SecAssocContinue = 2;
    public static final AssociationStatus SecAssocContinue = new AssociationStatus(2);

    public int value() {
        return this.value;
    }

    public static AssociationStatus from_int(int value) {
        switch (value) {
            case 0: {
                return SecAssocSuccess;
            }
            case 1: {
                return SecAssocFailure;
            }
            case 2: {
                return SecAssocContinue;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecAssocSuccess";
            }
            case 1: {
                return "SecAssocFailure";
            }
            case 2: {
                return "SecAssocContinue";
            }
        }
        throw new BAD_PARAM();
    }

    protected AssociationStatus(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return AssociationStatus.from_int(this.value());
    }
}

