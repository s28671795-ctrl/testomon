/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.fsm;

import java.util.StringTokenizer;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;

public class NameBase {
    private String name;
    private String toStringName;

    private String getClassName() {
        String fqn = this.getClass().getName();
        StringTokenizer st = new StringTokenizer(fqn, ".");
        String token = st.nextToken();
        while (st.hasMoreTokens()) {
            token = st.nextToken();
        }
        return token;
    }

    private String getPreferredClassName() {
        if (this instanceof Action) {
            return "Action";
        }
        if (this instanceof State) {
            return "State";
        }
        if (this instanceof Guard) {
            return "Guard";
        }
        if (this instanceof Input) {
            return "Input";
        }
        return this.getClassName();
    }

    public NameBase(String name) {
        this.name = name;
        this.toStringName = this.getPreferredClassName() + "[" + name + "]";
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.toStringName;
    }
}

