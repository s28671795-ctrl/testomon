/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.NVList;
import org.omg.CORBA.ORB;

public final class ContextImpl
extends Context {
    private ORBUtilSystemException wrapper;

    public ContextImpl(ORB orb) {
        this.wrapper = ORBUtilSystemException.get((org.jboss.com.sun.corba.se.spi.orb.ORB)orb, "rpc.presentation");
    }

    public ContextImpl(Context parent) {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public String context_name() {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public Context parent() {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public Context create_child(String name) {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public void set_one_value(String propName, Any propValue) {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public void set_values(NVList values2) {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public void delete_values(String propName) {
        throw this.wrapper.contextNotImplemented();
    }

    @Override
    public NVList get_values(String startScope, int opFlags, String propName) {
        throw this.wrapper.contextNotImplemented();
    }
}

