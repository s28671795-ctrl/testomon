/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Object;
import org.omg.Security.Right;
import org.omg.Security.RightsCombinator;
import org.omg.Security.RightsCombinatorHolder;
import org.omg.Security.RightsListHolder;

public interface RequiredRightsOperations {
    public void get_required_rights(Object var1, String var2, String var3, RightsListHolder var4, RightsCombinatorHolder var5);

    public void set_required_rights(String var1, String var2, Right[] var3, RightsCombinator var4);
}

