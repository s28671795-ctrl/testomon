/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.GuardBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;

public interface Guard {
    public Result evaluate(FSM var1, Input var2);

    public static final class Result {
        private String name;
        public static final Result ENABLED = new Result("ENABLED");
        public static final Result DISABLED = new Result("DISABLED");
        public static final Result DEFERED = new Result("DEFERED");

        private Result(String name) {
            this.name = name;
        }

        public static Result convert(boolean res) {
            return res ? ENABLED : DISABLED;
        }

        public Result complement() {
            if (this == ENABLED) {
                return DISABLED;
            }
            if (this == DISABLED) {
                return ENABLED;
            }
            return DEFERED;
        }

        public String toString() {
            return "Guard.Result[" + this.name + "]";
        }
    }

    public static final class Complement
    extends GuardBase {
        private Guard guard;

        public Complement(GuardBase guard) {
            super("not(" + guard.getName() + ")");
            this.guard = guard;
        }

        @Override
        public Result evaluate(FSM fsm, Input in) {
            return this.guard.evaluate(fsm, in).complement();
        }
    }
}

