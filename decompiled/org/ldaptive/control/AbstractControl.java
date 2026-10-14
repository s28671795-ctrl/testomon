/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractControl
implements Control {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String oid;
    private final boolean criticality;

    public AbstractControl(String id) {
        this.oid = id;
        this.criticality = false;
    }

    public AbstractControl(String id, boolean b) {
        this.oid = id;
        this.criticality = b;
    }

    @Override
    public String getOID() {
        return this.oid;
    }

    @Override
    public boolean getCriticality() {
        return this.criticality;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.criticality + "]";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractControl) {
            AbstractControl v = (AbstractControl)o;
            return LdapUtils.areEqual(this.getOID(), v.getOID()) && LdapUtils.areEqual(this.getCriticality(), v.getCriticality());
        }
        return false;
    }

    public abstract int hashCode();
}

