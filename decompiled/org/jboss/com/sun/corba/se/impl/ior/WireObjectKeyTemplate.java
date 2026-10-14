/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.logging.IORSystemException;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class WireObjectKeyTemplate
implements ObjectKeyTemplate {
    private IORSystemException wrapper;

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof WireObjectKeyTemplate;
    }

    public int hashCode() {
        return 53;
    }

    private byte[] getId(InputStream is) {
        CDRInputStream cis = (CDRInputStream)is;
        int len = cis.getBufferLength();
        byte[] result = new byte[len];
        cis.read_octet_array(result, 0, len);
        return result;
    }

    public WireObjectKeyTemplate(ORB orb) {
        this.initORB(orb);
    }

    public WireObjectKeyTemplate(InputStream is, OctetSeqHolder osh) {
        osh.value = this.getId(is);
        this.initORB((ORB)is.orb());
    }

    private void initORB(ORB orb) {
        this.wrapper = IORSystemException.get(orb, "oa.ior");
    }

    @Override
    public void write(ObjectId id, OutputStream os) {
        byte[] key = id.getId();
        os.write_octet_array(key, 0, key.length);
    }

    @Override
    public void write(OutputStream os) {
    }

    @Override
    public int getSubcontractId() {
        return 2;
    }

    @Override
    public int getServerId() {
        return -1;
    }

    @Override
    public String getORBId() {
        throw this.wrapper.orbIdNotAvailable();
    }

    @Override
    public ObjectAdapterId getObjectAdapterId() {
        throw this.wrapper.objectAdapterIdNotAvailable();
    }

    @Override
    public byte[] getAdapterId() {
        throw this.wrapper.adapterIdNotAvailable();
    }

    @Override
    public ORBVersion getORBVersion() {
        return ORBVersionFactory.getFOREIGN();
    }

    @Override
    public CorbaServerRequestDispatcher getServerRequestDispatcher(ORB orb, ObjectId id) {
        byte[] bid = id.getId();
        String str = new String(bid);
        return orb.getRequestDispatcherRegistry().getServerRequestDispatcher(str);
    }
}

