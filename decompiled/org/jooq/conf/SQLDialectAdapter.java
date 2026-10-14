/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.adapters.XmlAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.jooq.SQLDialect;

public class SQLDialectAdapter
extends XmlAdapter<String, SQLDialect> {
    public SQLDialect unmarshal(String v) throws Exception {
        return SQLDialect.valueOf(v);
    }

    public String marshal(SQLDialect v) throws Exception {
        return v.name();
    }
}

