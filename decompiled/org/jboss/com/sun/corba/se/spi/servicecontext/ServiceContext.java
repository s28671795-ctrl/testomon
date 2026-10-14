/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class ServiceContext {
    protected InputStream in = null;

    protected ServiceContext() {
    }

    protected ServiceContext(InputStream s2, GIOPVersion gv) throws SystemException {
        this.in = s2;
    }

    public abstract int getId();

    public void write(OutputStream s2, GIOPVersion gv) throws SystemException {
        EncapsOutputStream os = new EncapsOutputStream((ORB)s2.orb(), gv);
        os.putEndian();
        this.writeData(os);
        byte[] data = os.toByteArray();
        s2.write_long(this.getId());
        s2.write_long(data.length);
        s2.write_octet_array(data, 0, data.length);
    }

    protected abstract void writeData(OutputStream var1);

    public String toString() {
        return "ServiceContext[ id=" + this.getId() + " ]";
    }
}

