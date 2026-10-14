/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInterceptorOperations;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

public interface IORInterceptor_3_0Operations
extends IORInterceptorOperations {
    public void components_established(IORInfo var1);

    public void adapter_manager_state_changed(String var1, short var2);

    public void adapter_state_changed(ObjectReferenceTemplate[] var1, short var2);
}

