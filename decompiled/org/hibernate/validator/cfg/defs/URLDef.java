/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.URL;

public class URLDef
extends ConstraintDef<URLDef, URL> {
    public URLDef() {
        super(URL.class);
    }

    public URLDef protocol(String protocol) {
        this.addParameter("protocol", protocol);
        return this;
    }

    public URLDef host(String host2) {
        this.addParameter("host", host2);
        return this;
    }

    public URLDef port(int port) {
        this.addParameter("port", port);
        return this;
    }

    public URLDef regexp(String regexp) {
        this.addParameter("regexp", regexp);
        return this;
    }

    public URLDef flags(Pattern.Flag ... flags) {
        this.addParameter("flags", flags);
        return this;
    }
}

