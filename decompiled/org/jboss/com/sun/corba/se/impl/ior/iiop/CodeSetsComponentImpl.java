/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.CodeSetsComponent;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class CodeSetsComponentImpl
extends TaggedComponentBase
implements CodeSetsComponent {
    CodeSetComponentInfo csci;

    public boolean equals(Object obj) {
        if (!(obj instanceof CodeSetsComponentImpl)) {
            return false;
        }
        CodeSetsComponentImpl other = (CodeSetsComponentImpl)obj;
        return this.csci.equals(other.csci);
    }

    public int hashCode() {
        return this.csci.hashCode();
    }

    public String toString() {
        return "CodeSetsComponentImpl[csci=" + this.csci + "]";
    }

    public CodeSetsComponentImpl() {
        this.csci = new CodeSetComponentInfo();
    }

    public CodeSetsComponentImpl(InputStream is) {
        this.csci = new CodeSetComponentInfo();
        this.csci.read((MarshalInputStream)((Object)is));
    }

    public CodeSetsComponentImpl(ORB orb) {
        this.csci = orb == null ? new CodeSetComponentInfo() : orb.getORBData().getCodeSetComponentInfo();
    }

    @Override
    public CodeSetComponentInfo getCodeSetComponentInfo() {
        return this.csci;
    }

    @Override
    public void writeContents(OutputStream os) {
        this.csci.write((MarshalOutputStream)((Object)os));
    }

    @Override
    public int getId() {
        return 1;
    }
}

