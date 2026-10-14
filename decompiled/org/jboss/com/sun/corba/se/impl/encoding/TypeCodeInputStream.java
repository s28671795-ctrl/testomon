/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeReader;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.ORB;
import org.omg.CORBA_2_3.portable.InputStream;

public class TypeCodeInputStream
extends EncapsInputStream
implements TypeCodeReader {
    private Map<Integer, TypeCodeImpl> typeMap = null;
    private InputStream enclosure = null;

    public TypeCodeInputStream(ORB orb, byte[] data, int size) {
        super(orb, data, size);
    }

    public TypeCodeInputStream(ORB orb, byte[] data, int size, boolean littleEndian, GIOPVersion version) {
        super(orb, data, size, littleEndian, version);
    }

    public TypeCodeInputStream(ORB orb, ByteBuffer byteBuffer, int size, boolean littleEndian, GIOPVersion version) {
        super(orb, byteBuffer, size, littleEndian, version);
    }

    @Override
    public void addTypeCodeAtPosition(TypeCodeImpl tc, int position) {
        if (this.typeMap == null) {
            this.typeMap = new HashMap<Integer, TypeCodeImpl>(16);
        }
        this.typeMap.put(position, tc);
    }

    @Override
    public TypeCodeImpl getTypeCodeAtPosition(int position) {
        if (this.typeMap == null) {
            return null;
        }
        return this.typeMap.get(position);
    }

    @Override
    public void setEnclosingInputStream(InputStream enclosure) {
        this.enclosure = enclosure;
    }

    @Override
    public TypeCodeReader getTopLevelStream() {
        if (this.enclosure == null) {
            return this;
        }
        if (this.enclosure instanceof TypeCodeReader) {
            return ((TypeCodeReader)((Object)this.enclosure)).getTopLevelStream();
        }
        return this;
    }

    @Override
    public int getTopLevelPosition() {
        if (this.enclosure != null && this.enclosure instanceof TypeCodeReader) {
            int topPos = ((TypeCodeReader)((Object)this.enclosure)).getTopLevelPosition();
            int pos = topPos - this.getBufferLength() + this.getPosition();
            return pos;
        }
        return this.getPosition();
    }

    public static TypeCodeInputStream readEncapsulation(InputStream is, ORB _orb) {
        int encapLength = is.read_long();
        byte[] encapBuffer = new byte[encapLength];
        is.read_octet_array(encapBuffer, 0, encapBuffer.length);
        TypeCodeInputStream encap = is instanceof CDRInputStream ? new TypeCodeInputStream(_orb, encapBuffer, encapBuffer.length, ((CDRInputStream)is).isLittleEndian(), ((CDRInputStream)is).getGIOPVersion()) : new TypeCodeInputStream(_orb, encapBuffer, encapBuffer.length);
        encap.setEnclosingInputStream(is);
        encap.makeEncapsulation();
        return encap;
    }

    protected void makeEncapsulation() {
        this.consumeEndian();
    }

    @Override
    public void printTypeMap() {
        System.out.println("typeMap = {");
        for (Integer pos : this.typeMap.keySet()) {
            TypeCodeImpl tci = this.typeMap.get(pos);
            System.out.println("  key = " + pos + ", value = " + tci.description());
        }
        System.out.println("}");
    }
}

