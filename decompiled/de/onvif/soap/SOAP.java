/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap;

import de.onvif.soap.OnvifDevice;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class SOAP {
    private static final Logger log = LoggerFactory.getLogger(SOAP.class);
    private final OnvifDevice onvifDevice;

    public SOAP(OnvifDevice onvifDevice) {
        this.onvifDevice = onvifDevice;
    }

    public <TRequest, TResponse> TResponse createDeviceRequest(TRequest request, TResponse response, boolean includeAuth) {
        return this.createSOAPRequestWrapper(request, response, this.onvifDevice.getDeviceUri(), includeAuth);
    }

    public <TRequest, TResponse> TResponse createPtzRequest(TRequest request, TResponse response, boolean requireResponse) {
        return this.createSOAPRequestWrapper(request, response, this.onvifDevice.getPtzUri(), requireResponse);
    }

    public <TRequest, TResponse> TResponse createMediaRequest(TRequest request, TResponse response, boolean includeAuth) {
        return this.createSOAPRequestWrapper(request, response, this.onvifDevice.getMediaUri(), includeAuth);
    }

    public <TRequest, TResponse> TResponse createImagingRequest(TRequest request, TResponse response, boolean includeAuth) {
        return this.createSOAPRequestWrapper(request, response, this.onvifDevice.getImagingUri(), includeAuth);
    }

    public <TRequest, TResponse> TResponse createEventsRequest(TRequest request, TResponse response, boolean includeAuth) {
        return this.createSOAPRequestWrapper(request, response, this.onvifDevice.getEventsUri(), includeAuth);
    }

    private <TRequest, TResponse> TResponse createSOAPRequestWrapper(TRequest soapRequestElem, TResponse soapResponseElem, String soapUri, boolean needsAuthentification) {
        try {
            return (TResponse)this.createSOAPRequest(soapRequestElem, soapResponseElem, soapUri, needsAuthentification);
        }
        catch (Exception e) {
            log.error("Exception occurred while creating SOAP request: ", e);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object createSOAPRequest(Object soapRequestElem, Object soapResponseElem, String soapUri, boolean needsAuthentification) throws ConnectException, SOAPException {
        SOAPConnection soapConnection = null;
        SOAPMessage soapResponse = null;
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapMessage = this.createSoapMessage(soapRequestElem, needsAuthentification);
            log.debug("Request SOAP Message (" + soapRequestElem.getClass().getSimpleName() + "): ");
            if (log.isDebugEnabled()) {
                soapMessage.writeTo(System.out);
            }
            soapResponse = soapConnection.call(soapMessage, soapUri);
            log.debug("Response SOAP Message (" + soapResponseElem.getClass().getSimpleName() + "): ");
            if (log.isDebugEnabled()) {
                soapResponse.writeTo(System.out);
            }
            Unmarshaller unmarshaller = JAXBContext.newInstance(soapResponseElem.getClass()).createUnmarshaller();
            try {
                try {
                    soapResponseElem = unmarshaller.unmarshal(soapResponse.getSOAPBody().extractContentAsDocument());
                }
                catch (SOAPException e) {
                    soapResponseElem = unmarshaller.unmarshal(soapResponse.getSOAPBody().extractContentAsDocument());
                }
            }
            catch (UnmarshalException e) {
                log.warn("Could not unmarshal, ended in SOAP fault.");
            }
            Object object = soapResponseElem;
            return object;
        }
        catch (SocketException e) {
            throw new ConnectException(e.getMessage());
        }
        catch (SOAPException e) {
            log.error("Unexpected response. Response should be from class " + soapResponseElem.getClass() + ", but response is: " + soapResponse);
            throw e;
        }
        catch (IOException | JAXBException | ParserConfigurationException e) {
            log.error("Unhandled exception: " + e.getMessage());
            e.printStackTrace();
            Object var8_12 = null;
            return var8_12;
        }
        finally {
            try {
                soapConnection.close();
            }
            catch (SOAPException sOAPException) {}
        }
    }

    protected SOAPMessage createSoapMessage(Object soapRequestElem, boolean needAuthentification) throws SOAPException, ParserConfigurationException, JAXBException {
        MessageFactory messageFactory = MessageFactory.newInstance("SOAP 1.2 Protocol");
        SOAPMessage soapMessage = messageFactory.createMessage();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Marshaller marshaller = JAXBContext.newInstance(soapRequestElem.getClass()).createMarshaller();
        marshaller.marshal(soapRequestElem, document);
        soapMessage.getSOAPBody().addDocument(document);
        this.createSoapHeader(soapMessage);
        soapMessage.saveChanges();
        return soapMessage;
    }

    protected void createSoapHeader(SOAPMessage soapMessage) throws SOAPException {
        this.onvifDevice.createNonce();
        String encrypedPassword = this.onvifDevice.getEncryptedPassword();
        if (encrypedPassword != null && this.onvifDevice.getUsername() != null) {
            SOAPPart sp2 = soapMessage.getSOAPPart();
            SOAPEnvelope se = sp2.getEnvelope();
            SOAPHeader header = soapMessage.getSOAPHeader();
            se.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
            se.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
            SOAPElement securityElem = header.addChildElement("Security", "wsse");
            SOAPElement usernameTokenElem = securityElem.addChildElement("UsernameToken", "wsse");
            SOAPElement usernameElem = usernameTokenElem.addChildElement("Username", "wsse");
            usernameElem.setTextContent(this.onvifDevice.getUsername());
            SOAPElement passwordElem = usernameTokenElem.addChildElement("Password", "wsse");
            passwordElem.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
            passwordElem.setTextContent(encrypedPassword);
            SOAPElement nonceElem = usernameTokenElem.addChildElement("Nonce", "wsse");
            nonceElem.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
            nonceElem.setTextContent(this.onvifDevice.getEncryptedNonce());
            SOAPElement createdElem = usernameTokenElem.addChildElement("Created", "wsu");
            createdElem.setTextContent(this.onvifDevice.getLastUTCTime());
        }
    }
}

