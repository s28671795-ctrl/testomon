/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcLob;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public final class JdbcSQLXML
extends JdbcLob
implements SQLXML {
    private static final Map<String, Boolean> secureFeatureMap = new HashMap<String, Boolean>();
    private static final EntityResolver NOOP_ENTITY_RESOLVER = (string, string2) -> new InputSource(new StringReader(""));
    private static final URIResolver NOOP_URI_RESOLVER = (string, string2) -> new StreamSource(new StringReader(""));
    private DOMResult domResult;
    private Closeable closable;

    public JdbcSQLXML(JdbcConnection jdbcConnection, Value value, JdbcLob.State state, int n) {
        super(jdbcConnection, value, state, 17, n);
    }

    @Override
    void checkReadable() throws SQLException, IOException {
        this.checkClosed();
        if (this.state == JdbcLob.State.SET_CALLED) {
            if (this.domResult != null) {
                Node node = this.domResult.getNode();
                this.domResult = null;
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                try {
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource dOMSource = new DOMSource(node);
                    StringWriter stringWriter = new StringWriter();
                    StreamResult streamResult = new StreamResult(stringWriter);
                    transformer.transform(dOMSource, streamResult);
                    this.completeWrite(this.conn.createClob(new StringReader(stringWriter.toString()), -1L));
                }
                catch (Exception exception) {
                    throw this.logAndConvert(exception);
                }
                return;
            }
            if (this.closable != null) {
                this.closable.close();
                this.closable = null;
                return;
            }
            throw DbException.getUnsupportedException("Stream setter is not yet closed.");
        }
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return super.getBinaryStream();
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return super.getCharacterStream();
    }

    @Override
    public <T extends Source> T getSource(Class<T> clazz) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getSource(" + (clazz != null ? clazz.getSimpleName() + ".class" : "null") + ')');
            }
            this.checkReadable();
            if (clazz == null || clazz == DOMSource.class) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                for (Map.Entry<String, Boolean> entry : secureFeatureMap.entrySet()) {
                    try {
                        documentBuilderFactory.setFeature(entry.getKey(), entry.getValue());
                    }
                    catch (Exception exception) {}
                }
                documentBuilderFactory.setXIncludeAware(false);
                documentBuilderFactory.setExpandEntityReferences(false);
                documentBuilderFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "");
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                documentBuilder.setEntityResolver(NOOP_ENTITY_RESOLVER);
                return (T)new DOMSource(documentBuilder.parse(new InputSource(this.value.getInputStream())));
            }
            if (clazz == SAXSource.class) {
                SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
                for (Map.Entry<String, Boolean> entry : secureFeatureMap.entrySet()) {
                    try {
                        sAXParserFactory.setFeature(entry.getKey(), entry.getValue());
                    }
                    catch (Exception exception) {}
                }
                XMLReader xMLReader = sAXParserFactory.newSAXParser().getXMLReader();
                xMLReader.setEntityResolver(NOOP_ENTITY_RESOLVER);
                return (T)new SAXSource(xMLReader, new InputSource(this.value.getInputStream()));
            }
            if (clazz == StAXSource.class) {
                XMLInputFactory xMLInputFactory = XMLInputFactory.newInstance();
                xMLInputFactory.setProperty("javax.xml.stream.supportDTD", false);
                xMLInputFactory.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
                xMLInputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
                return (T)new StAXSource(xMLInputFactory.createXMLStreamReader(this.value.getInputStream()));
            }
            if (clazz == StreamSource.class) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
                transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
                transformerFactory.setURIResolver(NOOP_URI_RESOLVER);
                transformerFactory.newTransformer().transform(new StreamSource(this.value.getInputStream()), new SAXResult(new DefaultHandler()));
                return (T)new StreamSource(this.value.getInputStream());
            }
            throw this.unsupported(clazz.getName());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getString() throws SQLException {
        try {
            this.debugCodeCall("getString");
            this.checkReadable();
            return this.value.getString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public OutputStream setBinaryStream() throws SQLException {
        try {
            this.debugCodeCall("setBinaryStream");
            this.checkEditable();
            this.state = JdbcLob.State.SET_CALLED;
            return new BufferedOutputStream(this.setClobOutputStreamImpl());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Writer setCharacterStream() throws SQLException {
        try {
            this.debugCodeCall("setCharacterStream");
            this.checkEditable();
            this.state = JdbcLob.State.SET_CALLED;
            return this.setCharacterStreamImpl();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public <T extends Result> T setResult(Class<T> clazz) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setResult(" + (clazz != null ? clazz.getSimpleName() + ".class" : "null") + ')');
            }
            this.checkEditable();
            if (clazz == null || clazz == DOMResult.class) {
                this.domResult = new DOMResult();
                this.state = JdbcLob.State.SET_CALLED;
                return (T)this.domResult;
            }
            if (clazz == SAXResult.class) {
                SAXTransformerFactory sAXTransformerFactory = (SAXTransformerFactory)TransformerFactory.newInstance();
                TransformerHandler transformerHandler = sAXTransformerFactory.newTransformerHandler();
                Writer writer = this.setCharacterStreamImpl();
                transformerHandler.setResult(new StreamResult(writer));
                SAXResult sAXResult = new SAXResult(transformerHandler);
                this.closable = writer;
                this.state = JdbcLob.State.SET_CALLED;
                return (T)sAXResult;
            }
            if (clazz == StAXResult.class) {
                XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
                Writer writer = this.setCharacterStreamImpl();
                StAXResult stAXResult = new StAXResult(xMLOutputFactory.createXMLStreamWriter(writer));
                this.closable = writer;
                this.state = JdbcLob.State.SET_CALLED;
                return (T)stAXResult;
            }
            if (StreamResult.class.equals(clazz)) {
                Writer writer = this.setCharacterStreamImpl();
                StreamResult streamResult = new StreamResult(writer);
                this.closable = writer;
                this.state = JdbcLob.State.SET_CALLED;
                return (T)streamResult;
            }
            throw this.unsupported(clazz.getName());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setString(String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCodeCall("getSource", string);
            }
            this.checkEditable();
            this.completeWrite(this.conn.createClob(new StringReader(string), -1L));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    static {
        secureFeatureMap.put("http://javax.xml.XMLConstants/feature/secure-processing", true);
        secureFeatureMap.put("http://apache.org/xml/features/disallow-doctype-decl", true);
        secureFeatureMap.put("http://xml.org/sax/features/external-general-entities", false);
        secureFeatureMap.put("http://xml.org/sax/features/external-parameter-entities", false);
        secureFeatureMap.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }
}

