/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.spring;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.staxutils.StaxUtils;

public final class JAXBBeanFactory {
    private JAXBBeanFactory() {
    }

    public static <T> T createJAXBBean(JAXBContext context, String s2, Class<T> c) {
        StringReader reader = new StringReader(s2);
        XMLStreamReader data = StaxUtils.createXMLStreamReader(reader);
        try {
            T obj = null;
            if (c != null) {
                obj = JAXBUtils.unmarshall(context, data, c).getValue();
            } else {
                Object o = JAXBUtils.unmarshall(context, data);
                if (o instanceof JAXBElement) {
                    JAXBElement el = (JAXBElement)o;
                    Object ot = el.getValue();
                    obj = ot;
                }
            }
            T t2 = obj;
            return t2;
        }
        catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                StaxUtils.close(data);
            }
            catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

