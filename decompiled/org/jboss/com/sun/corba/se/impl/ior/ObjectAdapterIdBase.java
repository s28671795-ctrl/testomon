/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.omg.CORBA_2_3.portable.OutputStream;

abstract class ObjectAdapterIdBase
implements ObjectAdapterId {
    ObjectAdapterIdBase() {
    }

    public boolean equals(Object other) {
        if (!(other instanceof ObjectAdapterId)) {
            return false;
        }
        ObjectAdapterId theOther = (ObjectAdapterId)other;
        Iterator<String> iter1 = this.iterator();
        Iterator<String> iter2 = theOther.iterator();
        while (iter1.hasNext() && iter2.hasNext()) {
            String str2;
            String str1 = iter1.next();
            if (str1.equals(str2 = iter2.next())) continue;
            return false;
        }
        return iter1.hasNext() == iter2.hasNext();
    }

    public int hashCode() {
        int result = 17;
        Iterator<String> iter = this.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            result = 37 * result + str.hashCode();
        }
        return result;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("ObjectAdapterID[");
        Iterator<String> iter = this.iterator();
        boolean first = true;
        while (iter.hasNext()) {
            String str = iter.next();
            if (first) {
                first = false;
            } else {
                buff.append("/");
            }
            buff.append(str);
        }
        buff.append("]");
        return buff.toString();
    }

    @Override
    public void write(OutputStream os) {
        os.write_long(this.getNumLevels());
        Iterator<String> iter = this.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            os.write_string(str);
        }
    }
}

