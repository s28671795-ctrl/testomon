/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtValueDefPackage.ExtFullValueDescription;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ValueDefOperations;

public interface ExtValueDefOperations
extends ValueDefOperations {
    public ExtInitializer[] ext_initializers();

    public void ext_initializers(ExtInitializer[] var1);

    public ExtFullValueDescription describe_ext_value();

    public ExtAttributeDef create_ext_attribute(String var1, String var2, String var3, IDLType var4, AttributeMode var5, ExceptionDef[] var6, ExceptionDef[] var7);
}

