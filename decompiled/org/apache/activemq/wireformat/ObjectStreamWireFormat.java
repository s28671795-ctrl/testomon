/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.wireformat;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.ClassLoadingAwareObjectInputStream;
import org.apache.activemq.wireformat.WireFormat;

public class ObjectStreamWireFormat
implements WireFormat {
    @Override
    public ByteSequence marshal(Object command) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(baos);
        this.marshal(command, ds);
        ds.close();
        return baos.toByteSequence();
    }

    @Override
    public Object unmarshal(ByteSequence packet) throws IOException {
        return this.unmarshal(new DataInputStream(new ByteArrayInputStream(packet)));
    }

    @Override
    public void marshal(Object command, DataOutput ds) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream((OutputStream)((Object)ds));
        out.writeObject(command);
        out.flush();
        out.reset();
    }

    @Override
    public Object unmarshal(DataInput ds) throws IOException {
        try {
            ClassLoadingAwareObjectInputStream in = new ClassLoadingAwareObjectInputStream((InputStream)((Object)ds));
            Object command = in.readObject();
            in.close();
            return command;
        }
        catch (ClassNotFoundException e) {
            throw (IOException)new IOException("unmarshal failed: " + e).initCause(e);
        }
    }

    @Override
    public void setVersion(int version) {
    }

    @Override
    public int getVersion() {
        return 0;
    }
}

