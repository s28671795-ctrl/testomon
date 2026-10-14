/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdArray;
import org.jboss.com.sun.corba.se.impl.logging.IORSystemException;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class ObjectKeyTemplateBase
implements ObjectKeyTemplate {
    public static final String JIDL_ORB_ID = "";
    private static final String[] JIDL_OAID_STRINGS = new String[]{"TransientObjectAdapter"};
    public static final ObjectAdapterId JIDL_OAID = new ObjectAdapterIdArray(JIDL_OAID_STRINGS);
    protected IORSystemException wrapper;
    private ORBVersion version;
    private int magic;
    private int scid;
    private int serverid;
    private String orbid;
    private ObjectAdapterId oaid;
    private byte[] adapterId;

    @Override
    public byte[] getAdapterId() {
        return (byte[])this.adapterId.clone();
    }

    private byte[] computeAdapterId() {
        ByteBuffer buff = new ByteBuffer();
        buff.append(this.getServerId());
        buff.append(this.orbid);
        buff.append(this.oaid.getNumLevels());
        Iterator<String> iter = this.oaid.iterator();
        while (iter.hasNext()) {
            String comp = iter.next();
            buff.append(comp);
        }
        buff.trimToSize();
        return buff.toArray();
    }

    public ObjectKeyTemplateBase(ORB orb, int magic, int scid, int serverid, String orbid, ObjectAdapterId oaid) {
        this.wrapper = IORSystemException.get(orb, "oa.ior");
        this.magic = magic;
        this.scid = scid;
        this.serverid = serverid;
        this.orbid = orbid;
        this.oaid = oaid;
        this.adapterId = this.computeAdapterId();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectKeyTemplateBase)) {
            return false;
        }
        ObjectKeyTemplateBase other = (ObjectKeyTemplateBase)obj;
        return this.magic == other.magic && this.scid == other.scid && this.serverid == other.serverid && this.version.equals(other.version) && this.orbid.equals(other.orbid) && this.oaid.equals(other.oaid);
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.magic;
        result = 37 * result + this.scid;
        result = 37 * result + this.serverid;
        result = 37 * result + this.version.hashCode();
        result = 37 * result + this.orbid.hashCode();
        result = 37 * result + this.oaid.hashCode();
        return result;
    }

    @Override
    public int getSubcontractId() {
        return this.scid;
    }

    @Override
    public int getServerId() {
        return this.serverid;
    }

    @Override
    public String getORBId() {
        return this.orbid;
    }

    @Override
    public ObjectAdapterId getObjectAdapterId() {
        return this.oaid;
    }

    @Override
    public void write(ObjectId objectId, OutputStream os) {
        this.writeTemplate(os);
        objectId.write(os);
    }

    @Override
    public void write(OutputStream os) {
        this.writeTemplate(os);
    }

    protected abstract void writeTemplate(OutputStream var1);

    protected int getMagic() {
        return this.magic;
    }

    public void setORBVersion(ORBVersion version) {
        this.version = version;
    }

    @Override
    public ORBVersion getORBVersion() {
        return this.version;
    }

    protected byte[] readObjectKey(InputStream is) {
        int len = is.read_long();
        byte[] result = new byte[len];
        is.read_octet_array(result, 0, len);
        return result;
    }

    @Override
    public CorbaServerRequestDispatcher getServerRequestDispatcher(ORB orb, ObjectId id) {
        return orb.getRequestDispatcherRegistry().getServerRequestDispatcher(this.scid);
    }
}

