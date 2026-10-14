/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;

public abstract class ServerRequest {
    public String op_name() {
        return this.operation();
    }

    public String operation() {
        throw new NO_IMPLEMENT();
    }

    public abstract Context ctx();

    public void params(NVList params) {
        this.arguments(params);
    }

    public void arguments(NVList nv) {
        throw new NO_IMPLEMENT();
    }

    public void result(Any result) {
        this.set_result(result);
    }

    public void set_result(Any result) {
        throw new NO_IMPLEMENT();
    }

    public void except(Any except) {
        this.set_exception(except);
    }

    public void set_exception(Any except) {
        throw new NO_IMPLEMENT();
    }
}

