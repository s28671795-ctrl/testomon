/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.ArrayList;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.FreezableList;
import org.jboss.com.sun.corba.se.impl.ior.IORImpl;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class IORTemplateListImpl
extends FreezableList
implements IORTemplateList {
    @Override
    public Object set(int index, Object element) {
        if (element instanceof IORTemplate) {
            return super.set(index, element);
        }
        if (element instanceof IORTemplateList) {
            Object result = this.remove(index);
            this.add(index, element);
            return result;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void add(int index, Object element) {
        if (element instanceof IORTemplate) {
            super.add(index, element);
        } else if (element instanceof IORTemplateList) {
            IORTemplateList tl = (IORTemplateList)element;
            this.addAll(index, tl);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public IORTemplateListImpl() {
        super(new ArrayList<Object>());
    }

    public IORTemplateListImpl(InputStream is) {
        this();
        int size = is.read_long();
        for (int ctr = 0; ctr < size; ++ctr) {
            IORTemplate iortemp = IORFactories.makeIORTemplate(is);
            this.add(iortemp);
        }
        this.makeImmutable();
    }

    @Override
    public void makeImmutable() {
        this.makeElementsImmutable();
        super.makeImmutable();
    }

    @Override
    public void write(OutputStream os) {
        os.write_long(this.size());
        for (IORTemplate iortemp : this) {
            iortemp.write(os);
        }
    }

    @Override
    public IOR makeIOR(ORB orb, String typeid, ObjectId oid) {
        return new IORImpl(orb, typeid, this, oid);
    }

    @Override
    public boolean isEquivalent(IORFactory other) {
        if (!(other instanceof IORTemplateList)) {
            return false;
        }
        IORTemplateList list = (IORTemplateList)other;
        Iterator thisIterator = this.iterator();
        Iterator listIterator = list.iterator();
        while (thisIterator.hasNext() && listIterator.hasNext()) {
            IORTemplate listTemplate;
            IORTemplate thisTemplate = (IORTemplate)thisIterator.next();
            if (thisTemplate.isEquivalent(listTemplate = (IORTemplate)listIterator.next())) continue;
            return false;
        }
        return thisIterator.hasNext() == listIterator.hasNext();
    }
}

