/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import java.io.IOException;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.Messaging.ExceptionHolderHelper;

public abstract class ExceptionHolder
implements StreamableValue {
    private static final long serialVersionUID = 1L;
    private String[] _truncatable_ids = new String[]{"IDL:omg.org/Messaging/ExceptionHolder:1.0"};
    protected boolean is_system_exception;
    protected boolean byte_order;
    protected byte[] marshaled_exception;

    public abstract void raise_exception() throws UserException;

    public abstract void raise_exception_with_list(TypeCode[] var1) throws UserException;

    @Override
    public void _write(OutputStream os) {
        os.write_boolean(this.is_system_exception);
        os.write_boolean(this.byte_order);
        os.write_long(this.marshaled_exception.length);
        os.write_octet_array(this.marshaled_exception, 0, this.marshaled_exception.length);
    }

    @Override
    public void _read(InputStream os) {
        this.is_system_exception = os.read_boolean();
        this.byte_order = os.read_boolean();
        int _lmarshaled_exception1 = os.read_long();
        try {
            int x = os.available();
            if (x > 0 && _lmarshaled_exception1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lmarshaled_exception1);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.marshaled_exception = new byte[_lmarshaled_exception1];
        os.read_octet_array(this.marshaled_exception, 0, _lmarshaled_exception1);
    }

    @Override
    public String[] _truncatable_ids() {
        return this._truncatable_ids;
    }

    @Override
    public TypeCode _type() {
        return ExceptionHolderHelper.type();
    }
}

