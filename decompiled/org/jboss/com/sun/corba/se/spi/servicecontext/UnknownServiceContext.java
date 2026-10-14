/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContext;
import org.omg.CORBA.SystemException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class UnknownServiceContext
extends ServiceContext {
    private int id = -1;
    private byte[] data = null;

    public UnknownServiceContext(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public UnknownServiceContext(int id, InputStream is) {
        this.id = id;
        int len = is.read_long();
        this.data = new byte[len];
        is.read_octet_array(this.data, 0, len);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void writeData(OutputStream os) throws SystemException {
    }

    @Override
    public void write(OutputStream os, GIOPVersion gv) throws SystemException {
        os.write_long(this.id);
        os.write_long(this.data.length);
        os.write_octet_array(this.data, 0, this.data.length);
    }

    public byte[] getData() {
        return this.data;
    }
}

