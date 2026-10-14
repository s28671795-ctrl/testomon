/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ORBPackage;

import org.omg.CORBA.ORBPackage.InconsistentTypeCodeHelper;
import org.omg.CORBA.UserException;

public final class InconsistentTypeCode
extends UserException {
    public InconsistentTypeCode() {
        super(InconsistentTypeCodeHelper.id());
    }

    public InconsistentTypeCode(String reason_str) {
        super(InconsistentTypeCodeHelper.id() + " " + reason_str);
    }
}

