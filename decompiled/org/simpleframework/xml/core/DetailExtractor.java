/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.core.ContactList;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.DetailScanner;
import org.simpleframework.xml.core.FieldScanner;
import org.simpleframework.xml.core.MethodScanner;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class DetailExtractor {
    private final Cache<ContactList> methods = new ConcurrentCache<ContactList>();
    private final Cache<ContactList> fields = new ConcurrentCache<ContactList>();
    private final Cache<Detail> details = new ConcurrentCache<Detail>();
    private final DefaultType override;
    private final Support support;

    public DetailExtractor(Support support) {
        this(support, null);
    }

    public DetailExtractor(Support support, DefaultType override) {
        this.override = override;
        this.support = support;
    }

    public Detail getDetail(Class type) {
        Detail detail = this.details.fetch(type);
        if (detail == null) {
            detail = new DetailScanner(type, this.override);
            this.details.cache(type, detail);
        }
        return detail;
    }

    public ContactList getFields(Class type) throws Exception {
        Detail detail;
        ContactList list = this.fields.fetch(type);
        if (list == null && (detail = this.getDetail(type)) != null) {
            list = this.getFields(type, detail);
        }
        return list;
    }

    private ContactList getFields(Class type, Detail detail) throws Exception {
        FieldScanner list = new FieldScanner(detail, this.support);
        if (detail != null) {
            this.fields.cache(type, list);
        }
        return list;
    }

    public ContactList getMethods(Class type) throws Exception {
        Detail detail;
        ContactList list = this.methods.fetch(type);
        if (list == null && (detail = this.getDetail(type)) != null) {
            list = this.getMethods(type, detail);
        }
        return list;
    }

    private ContactList getMethods(Class type, Detail detail) throws Exception {
        MethodScanner list = new MethodScanner(detail, this.support);
        if (detail != null) {
            this.methods.cache(type, list);
        }
        return list;
    }
}

