/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WadlUtils {
    public static final String DETAILED_WADL_QUERY_PARAM = "detail";

    public static <T> T unmarshall(InputStream inputStream2, SAXParserFactory saxParserFactory, Class<T> resultClass) throws JAXBException, ParserConfigurationException, SAXException {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(resultClass);
        }
        catch (JAXBException ex) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_JAXB_CONTEXT(), ex);
        }
        SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXSource source2 = new SAXSource(saxParser.getXMLReader(), new InputSource(inputStream2));
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object result = unmarshaller.unmarshal(source2);
        return resultClass.cast(result);
    }

    public static boolean isDetailedWadlRequested(UriInfo uriInfo) {
        List simple = (List)uriInfo.getQueryParameters().get(DETAILED_WADL_QUERY_PARAM);
        if (simple != null) {
            if (simple.size() == 0) {
                return true;
            }
            String value = ((String)simple.get(0)).trim();
            return value.isEmpty() || value.toUpperCase(Locale.ROOT).equals("TRUE");
        }
        return false;
    }
}

