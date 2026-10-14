/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.List;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
import org.jboss.com.sun.corba.se.spi.ior.WriteContents;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class EncapsulationUtility {
    private EncapsulationUtility() {
    }

    public static void readIdentifiableSequence(List<Object> container, IdentifiableFactoryFinder finder, InputStream istr) {
        int count = istr.read_long();
        for (int ctr = 0; ctr < count; ++ctr) {
            int id = istr.read_long();
            Identifiable obj = finder.create(id, istr);
            container.add(obj);
        }
    }

    public static void writeIdentifiableSequence(List<Object> container, OutputStream os) {
        os.write_long(container.size());
        for (Identifiable identifiable : container) {
            os.write_long(identifiable.getId());
            identifiable.write(os);
        }
    }

    public static void writeOutputStream(OutputStream dataStream, OutputStream os) {
        byte[] data = ((CDROutputStream)dataStream).toByteArray();
        os.write_long(data.length);
        os.write_octet_array(data, 0, data.length);
    }

    public static InputStream getEncapsulationStream(InputStream is) {
        byte[] data = EncapsulationUtility.readOctets(is);
        EncapsInputStream result = new EncapsInputStream(is.orb(), data, data.length);
        result.consumeEndian();
        return result;
    }

    public static byte[] readOctets(InputStream is) {
        int len = is.read_ulong();
        byte[] data = new byte[len];
        is.read_octet_array(data, 0, len);
        return data;
    }

    public static void writeEncapsulation(WriteContents obj, OutputStream os) {
        EncapsOutputStream out = new EncapsOutputStream((ORB)os.orb());
        out.putEndian();
        obj.writeContents(out);
        EncapsulationUtility.writeOutputStream(out, os);
    }
}

