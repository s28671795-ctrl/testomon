/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JavaObjectSerializer {
    public static byte[] getData(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        return bos.toByteArray();
    }

    public static Object getObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bis);
        return in.readObject();
    }
}

