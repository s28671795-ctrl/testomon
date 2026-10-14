/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.Scanner;

class Caller {
    private final Function commit;
    private final Function validate;
    private final Function persist;
    private final Function complete;
    private final Function replace;
    private final Function resolve;
    private final Context context;

    public Caller(Scanner schema, Context context) {
        this.validate = schema.getValidate();
        this.complete = schema.getComplete();
        this.replace = schema.getReplace();
        this.resolve = schema.getResolve();
        this.persist = schema.getPersist();
        this.commit = schema.getCommit();
        this.context = context;
    }

    public Object replace(Object source2) throws Exception {
        if (this.replace != null) {
            return this.replace.call(this.context, source2);
        }
        return source2;
    }

    public Object resolve(Object source2) throws Exception {
        if (this.resolve != null) {
            return this.resolve.call(this.context, source2);
        }
        return source2;
    }

    public void commit(Object source2) throws Exception {
        if (this.commit != null) {
            this.commit.call(this.context, source2);
        }
    }

    public void validate(Object source2) throws Exception {
        if (this.validate != null) {
            this.validate.call(this.context, source2);
        }
    }

    public void persist(Object source2) throws Exception {
        if (this.persist != null) {
            this.persist.call(this.context, source2);
        }
    }

    public void complete(Object source2) throws Exception {
        if (this.complete != null) {
            this.complete.call(this.context, source2);
        }
    }
}

