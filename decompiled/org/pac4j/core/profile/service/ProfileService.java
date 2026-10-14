/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.service;

import org.pac4j.core.profile.CommonProfile;

public interface ProfileService<U extends CommonProfile> {
    public void create(U var1, String var2);

    public void update(U var1, String var2);

    public void remove(U var1);

    public void removeById(String var1);

    public U findById(String var1);

    public U findByLinkedId(String var1);
}

