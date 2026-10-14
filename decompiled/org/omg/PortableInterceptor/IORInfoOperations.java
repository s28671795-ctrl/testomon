/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Policy;
import org.omg.IOP.TaggedComponent;

public interface IORInfoOperations {
    public Policy get_effective_policy(int var1);

    public void add_ior_component(TaggedComponent var1);

    public void add_ior_component_to_profile(TaggedComponent var1, int var2);
}

