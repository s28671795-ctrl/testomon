/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;
import org.omg.PortableInterceptor.ObjectReferenceTemplateSeqHelper;

public final class ObjectReferenceTemplateSeqHolder
implements Streamable {
    public ObjectReferenceTemplate[] value;

    public ObjectReferenceTemplateSeqHolder() {
    }

    public ObjectReferenceTemplateSeqHolder(ObjectReferenceTemplate[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectReferenceTemplateSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ObjectReferenceTemplateSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ObjectReferenceTemplateSeqHelper.write(out, this.value);
    }
}

