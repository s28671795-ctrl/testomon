/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContext;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContextData;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContextRegistry;
import org.jboss.com.sun.corba.se.spi.servicecontext.UnknownServiceContext;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class ServiceContexts {
    private static final int JAVAIDL_ALIGN_SERVICE_ID = -1106033203;
    private org.jboss.com.sun.corba.se.spi.orb.ORB orb;
    private Map<Integer, Object> scMap;
    private boolean addAlignmentOnWrite;
    private CodeBase codeBase;
    private GIOPVersion giopVersion;
    private ORBUtilSystemException wrapper;

    private static boolean isDebugging(OutputStream os) {
        org.jboss.com.sun.corba.se.spi.orb.ORB orb = (org.jboss.com.sun.corba.se.spi.orb.ORB)os.orb();
        if (orb == null) {
            return false;
        }
        return orb.serviceContextDebugFlag;
    }

    private void dprint(String msg) {
        ORBUtility.dprint(this, msg);
    }

    public static void writeNullServiceContext(OutputStream os) {
        if (ServiceContexts.isDebugging(os)) {
            ORBUtility.dprint("ServiceContexts", "Writing null service context");
        }
        os.write_long(0);
    }

    private void createMapFromInputStream(InputStream is) {
        this.orb = (org.jboss.com.sun.corba.se.spi.orb.ORB)is.orb();
        if (this.orb.serviceContextDebugFlag) {
            this.dprint("Constructing ServiceContexts from input stream");
        }
        int numValid = is.read_long();
        if (this.orb.serviceContextDebugFlag) {
            this.dprint("Number of service contexts = " + numValid);
        }
        for (int ctr = 0; ctr < numValid; ++ctr) {
            int scId = is.read_long();
            if (this.orb.serviceContextDebugFlag) {
                this.dprint("Reading service context id " + scId);
            }
            byte[] data = OctetSeqHelper.read(is);
            if (this.orb.serviceContextDebugFlag) {
                this.dprint("Service context" + scId + " length: " + data.length);
            }
            this.scMap.put(new Integer(scId), data);
        }
    }

    public ServiceContexts(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
        this.addAlignmentOnWrite = false;
        this.scMap = new HashMap<Integer, Object>();
        this.giopVersion = orb.getORBData().getGIOPVersion();
        this.codeBase = null;
    }

    public ServiceContexts(InputStream s2) {
        this((org.jboss.com.sun.corba.se.spi.orb.ORB)s2.orb());
        this.codeBase = ((CDRInputStream)s2).getCodeBase();
        this.createMapFromInputStream(s2);
        this.giopVersion = ((CDRInputStream)s2).getGIOPVersion();
    }

    private ServiceContext unmarshal(Integer scId, byte[] data) {
        ServiceContextRegistry scr = this.orb.getServiceContextRegistry();
        ServiceContextData scd = scr.findServiceContextData(scId);
        ServiceContext sc = null;
        if (scd == null) {
            if (this.orb.serviceContextDebugFlag) {
                this.dprint("Could not find ServiceContextData for " + scId + " using UnknownServiceContext");
            }
            sc = new UnknownServiceContext((int)scId, data);
        } else {
            if (this.orb.serviceContextDebugFlag) {
                this.dprint("Found " + scd);
            }
            EncapsInputStream eis = new EncapsInputStream((ORB)this.orb, data, data.length, this.giopVersion, this.codeBase);
            eis.consumeEndian();
            sc = scd.makeServiceContext(eis, this.giopVersion);
            if (sc == null) {
                throw this.wrapper.svcctxUnmarshalError(CompletionStatus.COMPLETED_MAYBE);
            }
        }
        return sc;
    }

    public void addAlignmentPadding() {
        this.addAlignmentOnWrite = true;
    }

    public void write(OutputStream os, GIOPVersion gv) {
        if (ServiceContexts.isDebugging(os)) {
            this.dprint("Writing service contexts to output stream");
            Utility.printStackTrace();
        }
        int numsc = this.scMap.size();
        if (this.addAlignmentOnWrite) {
            if (ServiceContexts.isDebugging(os)) {
                this.dprint("Adding alignment padding");
            }
            ++numsc;
        }
        if (ServiceContexts.isDebugging(os)) {
            this.dprint("Service context has " + numsc + " components");
        }
        os.write_long(numsc);
        this.writeServiceContextsInOrder(os, gv);
        if (this.addAlignmentOnWrite) {
            if (ServiceContexts.isDebugging(os)) {
                this.dprint("Writing alignment padding");
            }
            os.write_long(-1106033203);
            os.write_long(4);
            os.write_octet((byte)0);
            os.write_octet((byte)0);
            os.write_octet((byte)0);
            os.write_octet((byte)0);
        }
        if (ServiceContexts.isDebugging(os)) {
            this.dprint("Service context writing complete");
        }
    }

    private void writeServiceContextsInOrder(OutputStream os, GIOPVersion gv) {
        Integer ueInfoId = new Integer(9);
        Object unknownExceptionInfo = this.scMap.remove(ueInfoId);
        for (Integer id : this.scMap.keySet()) {
            this.writeMapEntry(os, id, this.scMap.get(id), gv);
        }
        if (unknownExceptionInfo != null) {
            this.writeMapEntry(os, ueInfoId, unknownExceptionInfo, gv);
            this.scMap.put(ueInfoId, unknownExceptionInfo);
        }
    }

    private void writeMapEntry(OutputStream os, Integer id, Object scObj, GIOPVersion gv) {
        if (scObj instanceof byte[]) {
            if (ServiceContexts.isDebugging(os)) {
                this.dprint("Writing service context bytes for id " + id);
            }
            OctetSeqHelper.write(os, (byte[])scObj);
        } else {
            ServiceContext sc = (ServiceContext)scObj;
            if (ServiceContexts.isDebugging(os)) {
                this.dprint("Writing service context " + sc);
            }
            sc.write(os, gv);
        }
    }

    public void put(ServiceContext sc) {
        Integer id = new Integer(sc.getId());
        this.scMap.put(id, sc);
    }

    public void delete(int scId) {
        this.delete(new Integer(scId));
    }

    public void delete(Integer id) {
        this.scMap.remove(id);
    }

    public ServiceContext get(int scId) {
        return this.get(new Integer(scId));
    }

    public ServiceContext get(Integer id) {
        Object result = this.scMap.get(id);
        if (result == null) {
            return null;
        }
        if (result instanceof byte[]) {
            ServiceContext sc = this.unmarshal(id, (byte[])result);
            this.scMap.put(id, sc);
            return sc;
        }
        return (ServiceContext)result;
    }
}

