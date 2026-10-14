/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContext;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class UEInfoServiceContext
extends ServiceContext {
    public static final int SERVICE_CONTEXT_ID = 9;
    private Throwable unknown = null;

    public UEInfoServiceContext(Throwable ex) {
        this.unknown = ex;
    }

    public UEInfoServiceContext(InputStream is, GIOPVersion gv) {
        super(is, gv);
        try {
            this.unknown = (Throwable)this.in.read_value();
        }
        catch (ThreadDeath d) {
            throw d;
        }
        catch (Throwable e) {
            this.unknown = new UNKNOWN(0, CompletionStatus.COMPLETED_MAYBE);
        }
    }

    @Override
    public int getId() {
        return 9;
    }

    @Override
    public void writeData(OutputStream os) throws SystemException {
        os.write_value(this.unknown);
    }

    public Throwable getUE() {
        return this.unknown;
    }

    @Override
    public String toString() {
        return "UEInfoServiceContext[ unknown=" + this.unknown.toString() + " ]";
    }
}

