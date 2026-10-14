/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.builder.jaxb;

import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.neethi.Assertion;
import org.apache.neethi.PolicyComponent;

public class JaxbAssertion<T>
extends PrimitiveAssertion {
    private JAXBContext context;
    private Set<Class<?>> classes;
    private T data;

    public JaxbAssertion() {
    }

    public JaxbAssertion(QName qn, boolean optional) {
        super(qn, optional);
    }

    public JaxbAssertion(QName qn, boolean optional, boolean ignorable) {
        super(qn, optional, ignorable);
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        if (!super.equal(policyComponent)) {
            return false;
        }
        JaxbAssertion a = (JaxbAssertion)policyComponent;
        return this.data.equals(a.getData());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof JaxbAssertion) {
            return super.equals(o) && this.equal((PolicyComponent)o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int i = super.hashCode();
        if (this.data != null) {
            i ^= this.data.hashCode();
        }
        return i;
    }

    public void setData(T d) {
        this.data = d;
    }

    public T getData() {
        return this.data;
    }

    @Override
    protected Assertion clone(boolean optional) {
        JaxbAssertion<T> a = new JaxbAssertion<T>(this.getName(), optional, this.ignorable);
        a.setData(this.data);
        return a;
    }

    public static <T> JaxbAssertion<T> cast(Assertion a) {
        return (JaxbAssertion)a;
    }

    public static <T> JaxbAssertion<T> cast(Assertion a, Class<T> type) {
        return (JaxbAssertion)a;
    }

    private synchronized JAXBContext getContext() throws JAXBException {
        if (this.context == null || this.classes == null) {
            JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(this.data.getClass());
            this.classes = ccs.getClasses();
            this.context = ccs.getContext();
        }
        return this.context;
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        try {
            JAXBContext ctx = this.getContext();
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
            marshaller.marshal(this.data, writer);
        }
        catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
}

