/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.InterfaceAttrExtension;
import org.omg.CORBA.InterfaceAttrExtensionHelper;
import org.omg.CORBA.InterfaceAttrExtensionOperations;
import org.omg.CORBA.InterfaceAttrExtensionPOA;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescription;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

public class InterfaceAttrExtensionPOATie
extends InterfaceAttrExtensionPOA {
    private InterfaceAttrExtensionOperations _delegate;
    private POA _poa;

    public InterfaceAttrExtensionPOATie(InterfaceAttrExtensionOperations delegate) {
        this._delegate = delegate;
    }

    public InterfaceAttrExtensionPOATie(InterfaceAttrExtensionOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public InterfaceAttrExtension _this() {
        Object __o = this._this_object();
        InterfaceAttrExtension __r = InterfaceAttrExtensionHelper.narrow(__o);
        return __r;
    }

    @Override
    public InterfaceAttrExtension _this(ORB orb) {
        Object __o = this._this_object(orb);
        InterfaceAttrExtension __r = InterfaceAttrExtensionHelper.narrow(__o);
        return __r;
    }

    public InterfaceAttrExtensionOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(InterfaceAttrExtensionOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }

    @Override
    public ExtFullInterfaceDescription describe_ext_interface() {
        return this._delegate.describe_ext_interface();
    }

    @Override
    public ExtAttributeDef create_ext_attribute(String id, String name, String version, IDLType type, AttributeMode mode, ExceptionDef[] get_exceptions, ExceptionDef[] set_exceptions) {
        return this._delegate.create_ext_attribute(id, name, version, type, mode, get_exceptions, set_exceptions);
    }
}

