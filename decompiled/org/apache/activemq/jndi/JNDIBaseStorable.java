/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Properties;
import javax.naming.NamingException;
import javax.naming.Reference;
import org.apache.activemq.jndi.JNDIReferenceFactory;
import org.apache.activemq.jndi.JNDIStorableInterface;

public abstract class JNDIBaseStorable
implements JNDIStorableInterface,
Externalizable {
    private Properties properties;

    protected abstract void buildFromProperties(Properties var1);

    protected abstract void populateProperties(Properties var1);

    @Override
    public synchronized void setProperties(Properties props) {
        this.properties = props;
        this.buildFromProperties(props);
    }

    @Override
    public synchronized Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        this.populateProperties(this.properties);
        return this.properties;
    }

    @Override
    public Reference getReference() throws NamingException {
        return JNDIReferenceFactory.createReference(this.getClass().getName(), this);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Properties props = (Properties)in.readObject();
        if (props != null) {
            this.setProperties(props);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getProperties());
    }
}

