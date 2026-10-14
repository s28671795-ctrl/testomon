/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeInputStream;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class TypeCodeOutputStream
extends EncapsOutputStream {
    private OutputStream enclosure = null;
    private Map<String, Integer> typeMap = null;
    private boolean isEncapsulation = false;

    public TypeCodeOutputStream(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        super(orb, false);
    }

    public TypeCodeOutputStream(org.jboss.com.sun.corba.se.spi.orb.ORB orb, boolean littleEndian) {
        super(orb, littleEndian);
    }

    @Override
    public InputStream create_input_stream() {
        TypeCodeInputStream tcis = new TypeCodeInputStream(this.orb(), this.getByteBuffer(), this.getIndex(), this.isLittleEndian(), this.getGIOPVersion());
        return tcis;
    }

    public void setEnclosingOutputStream(OutputStream enclosure) {
        this.enclosure = enclosure;
    }

    public TypeCodeOutputStream getTopLevelStream() {
        if (this.enclosure == null) {
            return this;
        }
        if (this.enclosure instanceof TypeCodeOutputStream) {
            return ((TypeCodeOutputStream)this.enclosure).getTopLevelStream();
        }
        return this;
    }

    public int getTopLevelPosition() {
        if (this.enclosure != null && this.enclosure instanceof TypeCodeOutputStream) {
            int pos = ((TypeCodeOutputStream)this.enclosure).getTopLevelPosition() + this.getPosition();
            if (this.isEncapsulation) {
                pos += 4;
            }
            return pos;
        }
        return this.getPosition();
    }

    public void addIDAtPosition(String id, int position) {
        if (this.typeMap == null) {
            this.typeMap = new HashMap<String, Integer>(16);
        }
        this.typeMap.put(id, position);
    }

    public int getPositionForID(String id) {
        if (this.typeMap == null) {
            throw this.wrapper.refTypeIndirType(CompletionStatus.COMPLETED_NO);
        }
        return this.typeMap.get(id);
    }

    public void writeRawBuffer(org.omg.CORBA.portable.OutputStream s2, int firstLong) {
        s2.write_long(firstLong);
        ByteBuffer byteBuffer = this.getByteBuffer();
        if (byteBuffer.hasArray()) {
            s2.write_octet_array(byteBuffer.array(), 4, this.getIndex() - 4);
        } else {
            byte[] buf = new byte[byteBuffer.limit()];
            for (int i = 0; i < buf.length; ++i) {
                buf[i] = byteBuffer.get(i);
            }
            s2.write_octet_array(buf, 4, this.getIndex() - 4);
        }
    }

    public TypeCodeOutputStream createEncapsulation(ORB _orb) {
        TypeCodeOutputStream encap = new TypeCodeOutputStream((org.jboss.com.sun.corba.se.spi.orb.ORB)_orb, this.isLittleEndian());
        encap.setEnclosingOutputStream(this);
        encap.makeEncapsulation();
        return encap;
    }

    protected void makeEncapsulation() {
        this.putEndian();
        this.isEncapsulation = true;
    }

    public static TypeCodeOutputStream wrapOutputStream(OutputStream os) {
        boolean littleEndian = os instanceof CDROutputStream ? ((CDROutputStream)os).isLittleEndian() : false;
        TypeCodeOutputStream tos = new TypeCodeOutputStream((org.jboss.com.sun.corba.se.spi.orb.ORB)os.orb(), littleEndian);
        tos.setEnclosingOutputStream(os);
        return tos;
    }

    public int getPosition() {
        return this.getIndex();
    }

    @Override
    public int getRealIndex(int index) {
        int topPos = this.getTopLevelPosition();
        return topPos;
    }

    public byte[] getTypeCodeBuffer() {
        ByteBuffer theBuffer = this.getByteBuffer();
        byte[] tcBuffer = new byte[this.getIndex() - 4];
        for (int i = 0; i < tcBuffer.length; ++i) {
            tcBuffer[i] = theBuffer.get(i + 4);
        }
        return tcBuffer;
    }

    public void printTypeMap() {
        System.out.println("typeMap = {");
        for (String id : this.typeMap.keySet()) {
            Integer pos = this.typeMap.get(id);
            System.out.println("  key = " + id + ", value = " + pos);
        }
        System.out.println("}");
    }
}

