/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.io.Serializable;
import javax.rmi.CORBA.ValueHandler;
import org.omg.CORBA.portable.OutputStream;

public interface ValueHandlerMultiFormat
extends ValueHandler {
    public byte getMaximumStreamFormatVersion();

    public void writeValue(OutputStream var1, Serializable var2, byte var3);
}

