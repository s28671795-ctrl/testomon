/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.RequestPartitioningComponent;
import org.omg.CORBA_2_3.portable.OutputStream;

public class RequestPartitioningComponentImpl
extends TaggedComponentBase
implements RequestPartitioningComponent {
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("oa.ior");
    private int partitionToUse;

    public boolean equals(Object obj) {
        if (!(obj instanceof RequestPartitioningComponentImpl)) {
            return false;
        }
        RequestPartitioningComponentImpl other = (RequestPartitioningComponentImpl)obj;
        return this.partitionToUse == other.partitionToUse;
    }

    public int hashCode() {
        return this.partitionToUse;
    }

    public String toString() {
        return "RequestPartitioningComponentImpl[partitionToUse=" + this.partitionToUse + "]";
    }

    public RequestPartitioningComponentImpl() {
        this.partitionToUse = 0;
    }

    public RequestPartitioningComponentImpl(int thePartitionToUse) {
        if (thePartitionToUse < 0 || thePartitionToUse > 63) {
            throw wrapper.invalidRequestPartitioningComponentValue(new Integer(thePartitionToUse), new Integer(0), new Integer(63));
        }
        this.partitionToUse = thePartitionToUse;
    }

    @Override
    public int getRequestPartitioningId() {
        return this.partitionToUse;
    }

    @Override
    public void writeContents(OutputStream os) {
        os.write_ulong(this.partitionToUse);
    }

    @Override
    public int getId() {
        return 1398099457;
    }
}

