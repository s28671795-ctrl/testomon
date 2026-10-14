/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import java.util.Enumeration;
import java.util.Vector;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContextData;
import org.omg.CORBA.BAD_PARAM;

public class ServiceContextRegistry {
    private Vector<ServiceContextData> scCollection = new Vector();

    private void dprint(String msg) {
        ORBUtility.dprint(this, msg);
    }

    public ServiceContextRegistry(ORB orb) {
    }

    public void register(Class<?> cls) {
        ServiceContextData scd;
        if (ORB.ORBInitDebug) {
            this.dprint("Registering service context class " + cls);
        }
        if (this.findServiceContextData((scd = new ServiceContextData(cls)).getId()) != null) {
            throw new BAD_PARAM("Tried to register duplicate service context");
        }
        this.scCollection.addElement(scd);
    }

    public ServiceContextData findServiceContextData(int scId) {
        if (ORB.ORBInitDebug) {
            this.dprint("Searching registry for service context id " + scId);
        }
        Enumeration<ServiceContextData> enumeration = this.scCollection.elements();
        while (enumeration.hasMoreElements()) {
            ServiceContextData scd = enumeration.nextElement();
            if (scd.getId() != scId) continue;
            if (ORB.ORBInitDebug) {
                this.dprint("Service context data found: " + scd);
            }
            return scd;
        }
        if (ORB.ORBInitDebug) {
            this.dprint("Service context data not found");
        }
        return null;
    }
}

