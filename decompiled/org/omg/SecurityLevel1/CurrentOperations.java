/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel1;

import org.omg.Security.AttributeType;
import org.omg.Security.SecAttribute;

public interface CurrentOperations
extends org.omg.CORBA.CurrentOperations {
    public SecAttribute[] get_attributes(AttributeType[] var1);
}

