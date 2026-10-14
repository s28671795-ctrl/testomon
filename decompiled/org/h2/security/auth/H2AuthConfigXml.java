/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.h2.security.auth.H2AuthConfig;
import org.h2.security.auth.HasConfigProperties;
import org.h2.security.auth.PropertyConfig;
import org.h2.security.auth.RealmConfig;
import org.h2.security.auth.UserToRolesMapperConfig;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class H2AuthConfigXml
extends DefaultHandler {
    private H2AuthConfig result;
    private HasConfigProperties lastConfigProperties;

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        switch (string3) {
            case "h2Auth": {
                this.result = new H2AuthConfig();
                this.result.setAllowUserRegistration("true".equals(H2AuthConfigXml.getAttributeValueOr("allowUserRegistration", attributes, "false")));
                this.result.setCreateMissingRoles("true".equals(H2AuthConfigXml.getAttributeValueOr("createMissingRoles", attributes, "true")));
                break;
            }
            case "realm": {
                RealmConfig realmConfig = new RealmConfig();
                realmConfig.setName(H2AuthConfigXml.getMandatoryAttributeValue("name", attributes));
                realmConfig.setValidatorClass(H2AuthConfigXml.getMandatoryAttributeValue("validatorClass", attributes));
                this.result.getRealms().add(realmConfig);
                this.lastConfigProperties = realmConfig;
                break;
            }
            case "userToRolesMapper": {
                UserToRolesMapperConfig userToRolesMapperConfig = new UserToRolesMapperConfig();
                userToRolesMapperConfig.setClassName(H2AuthConfigXml.getMandatoryAttributeValue("className", attributes));
                this.result.getUserToRolesMappers().add(userToRolesMapperConfig);
                this.lastConfigProperties = userToRolesMapperConfig;
                break;
            }
            case "property": {
                if (this.lastConfigProperties == null) {
                    throw new SAXException("property element in the wrong place");
                }
                this.lastConfigProperties.getProperties().add(new PropertyConfig(H2AuthConfigXml.getMandatoryAttributeValue("name", attributes), H2AuthConfigXml.getMandatoryAttributeValue("value", attributes)));
                break;
            }
            default: {
                throw new SAXException("unexpected element " + string3);
            }
        }
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        if (this.lastConfigProperties != null && !string3.equals("property")) {
            this.lastConfigProperties = null;
        }
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws IOException, SAXException {
        return new InputSource(new StringReader(""));
    }

    private static String getMandatoryAttributeValue(String string, Attributes attributes) throws SAXException {
        String string2 = attributes.getValue(string);
        if (string2 == null || string2.trim().equals("")) {
            throw new SAXException("missing attribute " + string);
        }
        return string2;
    }

    private static String getAttributeValueOr(String string, Attributes attributes, String string2) {
        String string3 = attributes.getValue(string);
        if (string3 == null || string3.trim().equals("")) {
            return string2;
        }
        return string3;
    }

    public H2AuthConfig getResult() {
        return this.result;
    }

    public static H2AuthConfig parseFrom(URL uRL) throws SAXException, IOException, ParserConfigurationException {
        try (InputStream inputStream2 = uRL.openStream();){
            H2AuthConfig h2AuthConfig = H2AuthConfigXml.parseFrom(inputStream2);
            return h2AuthConfig;
        }
    }

    public static H2AuthConfig parseFrom(InputStream inputStream2) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
        sAXParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        sAXParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        sAXParserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        sAXParserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        sAXParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser sAXParser = sAXParserFactory.newSAXParser();
        H2AuthConfigXml h2AuthConfigXml = new H2AuthConfigXml();
        sAXParser.parse(inputStream2, (DefaultHandler)h2AuthConfigXml);
        return h2AuthConfigXml.getResult();
    }
}

