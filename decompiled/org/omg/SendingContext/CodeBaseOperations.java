/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.Repository;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.SendingContext.RunTimeOperations;

public interface CodeBaseOperations
extends RunTimeOperations {
    public Repository get_ir();

    public String implementation(String var1);

    public String implementationx(String var1);

    public String[] implementations(String[] var1);

    public FullValueDescription meta(String var1);

    public FullValueDescription[] metas(String[] var1);

    public String[] bases(String var1);
}

