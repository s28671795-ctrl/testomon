/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public interface NamingContextOperations {
    public void bind(NameComponent[] var1, Object var2) throws NotFound, AlreadyBound, CannotProceed, InvalidName;

    public void rebind(NameComponent[] var1, Object var2) throws NotFound, CannotProceed, InvalidName;

    public void bind_context(NameComponent[] var1, NamingContext var2) throws NotFound, AlreadyBound, CannotProceed, InvalidName;

    public void rebind_context(NameComponent[] var1, NamingContext var2) throws NotFound, CannotProceed, InvalidName;

    public Object resolve(NameComponent[] var1) throws NotFound, CannotProceed, InvalidName;

    public void unbind(NameComponent[] var1) throws NotFound, CannotProceed, InvalidName;

    public NamingContext new_context();

    public NamingContext bind_new_context(NameComponent[] var1) throws NotFound, AlreadyBound, CannotProceed, InvalidName;

    public void destroy() throws NotEmpty;

    public void list(int var1, BindingListHolder var2, BindingIteratorHolder var3);
}

