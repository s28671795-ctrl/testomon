/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.CosNamingLocator;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.IDLEntity;

public final class ATLASLocator
implements IDLEntity {
    private int discriminator;
    private CosNamingLocator naming_locator;
    private String the_url;
    private AuthTokenDispenser the_dispenser;

    public int discriminator() {
        return this.discriminator;
    }

    public CosNamingLocator naming_locator() {
        if (this.discriminator != 1) {
            throw new BAD_OPERATION();
        }
        return this.naming_locator;
    }

    public void naming_locator(CosNamingLocator _x) {
        this.discriminator = 1;
        this.naming_locator = _x;
    }

    public String the_url() {
        if (this.discriminator != 2) {
            throw new BAD_OPERATION();
        }
        return this.the_url;
    }

    public void the_url(String _x) {
        this.discriminator = 2;
        this.the_url = _x;
    }

    public AuthTokenDispenser the_dispenser() {
        if (this.discriminator != 3) {
            throw new BAD_OPERATION();
        }
        return this.the_dispenser;
    }

    public void the_dispenser(AuthTokenDispenser _x) {
        this.discriminator = 3;
        this.the_dispenser = _x;
    }

    public void __default() {
        this.discriminator = 0;
    }

    public void __default(int _discriminator) {
        if (_discriminator == 1 || _discriminator == 2 || _discriminator == 3) {
            throw new BAD_PARAM("Illegal value is used in __default method", 34, CompletionStatus.COMPLETED_NO);
        }
        this.discriminator = _discriminator;
    }
}

