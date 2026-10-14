/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.IRObjectOperations;
import org.omg.CORBA.Repository;

public interface ContainedOperations
extends IRObjectOperations {
    public String id();

    public void id(String var1);

    public String name();

    public void name(String var1);

    public String version();

    public void version(String var1);

    public Container defined_in();

    public String absolute_name();

    public Repository containing_repository();

    public Description describe();

    public void move(Container var1, String var2, String var3);
}

