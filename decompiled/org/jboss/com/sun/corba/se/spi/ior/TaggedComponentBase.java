/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableBase;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponent;
import org.omg.CORBA.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.IOP.TaggedComponentHelper;

public abstract class TaggedComponentBase
extends IdentifiableBase
implements TaggedComponent {
    @Override
    public org.omg.IOP.TaggedComponent getIOPComponent(ORB orb) {
        EncapsOutputStream os = new EncapsOutputStream((org.jboss.com.sun.corba.se.spi.orb.ORB)orb);
        this.write(os);
        InputStream is = (InputStream)os.create_input_stream();
        return TaggedComponentHelper.read(is);
    }
}

