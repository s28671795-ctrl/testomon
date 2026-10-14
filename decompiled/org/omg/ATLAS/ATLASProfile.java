/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.ATLASLocator;
import org.omg.CORBA.portable.IDLEntity;

public final class ATLASProfile
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ATLASLocator the_locator;
    public byte[] the_cache_id;

    public ATLASProfile() {
    }

    public ATLASProfile(ATLASLocator the_locator, byte[] the_cache_id) {
        this.the_locator = the_locator;
        this.the_cache_id = the_cache_id;
    }
}

