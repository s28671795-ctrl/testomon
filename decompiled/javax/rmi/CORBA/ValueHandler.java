/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.io.Serializable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SendingContext.RunTime;

public interface ValueHandler {
    public void writeValue(OutputStream var1, Serializable var2);

    public Serializable readValue(InputStream var1, int var2, Class<?> var3, String var4, RunTime var5);

    public String getRMIRepositoryID(Class<?> var1);

    public boolean isCustomMarshaled(Class<?> var1);

    public RunTime getRunTimeCodeBase();

    public Serializable writeReplace(Serializable var1);
}

