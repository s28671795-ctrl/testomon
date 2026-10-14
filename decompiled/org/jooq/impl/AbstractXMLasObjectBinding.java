/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.DataBindingException
 *  jakarta.xml.bind.JAXB
 *  jakarta.xml.bind.JAXBContext
 *  jakarta.xml.bind.JAXBElement
 *  jakarta.xml.bind.JAXBException
 *  jakarta.xml.bind.Marshaller
 *  jakarta.xml.bind.annotation.XmlRootElement
 */
package org.jooq.impl;

import jakarta.xml.bind.DataBindingException;
import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.beans.Introspector;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.namespace.QName;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.XML;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.impl.AbstractXMLBinding;

public class AbstractXMLasObjectBinding<T>
extends AbstractXMLBinding<T> {
    private final Converter<XML, T> converter;

    protected AbstractXMLasObjectBinding(Class<T> theType) {
        this.converter = new XMLasObjectConverter<T>(theType);
    }

    @Override
    public final Converter<XML, T> converter() {
        return this.converter;
    }

    private static final class XMLasObjectConverter<T>
    extends AbstractContextConverter<XML, T> {
        XmlRootElement root;
        transient JAXBContext ctx;

        private XMLasObjectConverter(Class<T> type) {
            super(XML.class, type);
            this.root = type.getAnnotation(XmlRootElement.class);
            this.ctx = this.initCtx();
        }

        private final JAXBContext initCtx() {
            try {
                return JAXBContext.newInstance((Class[])new Class[]{this.toType()});
            }
            catch (JAXBException e) {
                throw new DataBindingException((Throwable)e);
            }
        }

        @Override
        public T from(XML t2, ConverterContext scope) {
            if (t2 == null) {
                return null;
            }
            return (T)JAXB.unmarshal((Reader)new StringReader(String.valueOf(t2)), this.toType());
        }

        @Override
        public XML to(T u, ConverterContext scope) {
            if (u == null) {
                return null;
            }
            try {
                StringWriter s2 = new StringWriter();
                Object o = u;
                if (this.root == null) {
                    o = new JAXBElement(new QName(Introspector.decapitalize(this.toType().getSimpleName())), this.toType(), u);
                }
                Marshaller m4 = this.ctx.createMarshaller();
                m4.setProperty("jaxb.fragment", (Object)true);
                m4.marshal(o, (Writer)s2);
                return XML.xml(s2.toString());
            }
            catch (JAXBException e) {
                throw new DataBindingException((Throwable)e);
            }
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            oos.defaultWriteObject();
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            ois.defaultReadObject();
            this.ctx = this.initCtx();
        }
    }
}

