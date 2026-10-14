/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSIIOP.AS_ContextSec;
import org.omg.CSIIOP.SAS_ContextSec;
import org.omg.IOP.TaggedComponent;

public final class CompoundSecMech
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_requires;
    public TaggedComponent transport_mech;
    public AS_ContextSec as_context_mech;
    public SAS_ContextSec sas_context_mech;

    public CompoundSecMech() {
    }

    public CompoundSecMech(short target_requires, TaggedComponent transport_mech, AS_ContextSec as_context_mech, SAS_ContextSec sas_context_mech) {
        this.target_requires = target_requires;
        this.transport_mech = transport_mech;
        this.as_context_mech = as_context_mech;
        this.sas_context_mech = sas_context_mech;
    }
}

