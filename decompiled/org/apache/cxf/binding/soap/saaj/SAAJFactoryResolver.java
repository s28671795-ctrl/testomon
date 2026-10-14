/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.saaj;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.util.SystemPropertyAction;

public final class SAAJFactoryResolver {
    public static final String MESSAGE_FACTORY_KEY = "org.apache.cxf.binding.soap.messageFactoryClassName";
    public static final String SOAP_FACTORY_KEY = "org.apache.cxf.binding.soap.soapFactoryClassName";

    private SAAJFactoryResolver() {
    }

    public static MessageFactory createMessageFactory(SoapVersion version) throws SOAPException {
        MessageFactory messageFactory;
        String messageFactoryClassName = SystemPropertyAction.getPropertyOrNull(MESSAGE_FACTORY_KEY);
        if (messageFactoryClassName != null) {
            messageFactory = SAAJFactoryResolver.newInstanceCxfSAAJFactory(messageFactoryClassName, MessageFactory.class);
        } else if (version instanceof Soap11) {
            try {
                messageFactory = MessageFactory.newInstance("SOAP 1.1 Protocol");
            }
            catch (Throwable t2) {
                messageFactory = MessageFactory.newInstance();
            }
        } else if (version instanceof Soap12) {
            try {
                messageFactory = MessageFactory.newInstance("SOAP 1.2 Protocol");
            }
            catch (Throwable t3) {
                messageFactory = MessageFactory.newInstance();
            }
        } else {
            messageFactory = MessageFactory.newInstance();
        }
        return messageFactory;
    }

    public static SOAPFactory createSOAPFactory(SoapVersion version) throws SOAPException {
        SOAPFactory soapFactory;
        String soapFactoryClassName = SystemPropertyAction.getPropertyOrNull(SOAP_FACTORY_KEY);
        if (soapFactoryClassName != null) {
            soapFactory = SAAJFactoryResolver.newInstanceCxfSAAJFactory(soapFactoryClassName, SOAPFactory.class);
        } else if (version instanceof Soap11) {
            try {
                soapFactory = SOAPFactory.newInstance("SOAP 1.1 Protocol");
            }
            catch (Throwable t2) {
                soapFactory = SOAPFactory.newInstance();
            }
        } else if (version instanceof Soap12) {
            try {
                soapFactory = SOAPFactory.newInstance("SOAP 1.2 Protocol");
            }
            catch (Throwable t3) {
                soapFactory = SOAPFactory.newInstance();
            }
        } else {
            soapFactory = SOAPFactory.newInstance();
        }
        return soapFactory;
    }

    private static <T> T newInstanceCxfSAAJFactory(String factoryName, Class<T> cls) throws SOAPException {
        try {
            Class<?> klass = Class.forName(factoryName);
            return cls.cast(klass.newInstance());
        }
        catch (ClassNotFoundException cnfe) {
            throw new SOAPException("Provider " + factoryName + " could not be instantiated: " + cnfe, cnfe);
        }
        catch (InstantiationException ie) {
            throw new SOAPException("Provider " + factoryName + " could not be instantiated: " + ie, ie);
        }
        catch (IllegalAccessException iae) {
            throw new SOAPException("Provider " + factoryName + " could not be instantiated: " + iae, iae);
        }
    }
}

