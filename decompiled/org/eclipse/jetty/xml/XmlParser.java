/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser {
    private static final Logger LOG = LoggerFactory.getLogger(XmlParser.class);
    private final AutoLock _lock = new AutoLock();
    private Map<String, URL> _redirectMap = new HashMap<String, URL>();
    private SAXParser _parser;
    private Map<String, ContentHandler> _observerMap;
    private Stack<ContentHandler> _observers = new Stack();
    private String _xpath;
    private Object _xpaths;
    private String _dtd;

    public XmlParser() {
        SAXParserFactory factory2 = this.newSAXParserFactory();
        boolean validatingDefault = factory2.getClass().toString().contains("org.apache.xerces.");
        String validatingProp = System.getProperty("org.eclipse.jetty.xml.XmlParser.Validating", validatingDefault ? "true" : "false");
        boolean validating = Boolean.valueOf(validatingProp);
        this.setValidating(validating);
    }

    public XmlParser(boolean validating) {
        this.setValidating(validating);
    }

    AutoLock lock() {
        return this._lock.lock();
    }

    protected SAXParserFactory newSAXParserFactory() {
        return SAXParserFactory.newInstance();
    }

    public void setValidating(boolean validating) {
        try {
            SAXParserFactory factory2 = this.newSAXParserFactory();
            factory2.setValidating(validating);
            this._parser = factory2.newSAXParser();
            try {
                if (validating) {
                    this._parser.getXMLReader().setFeature("http://apache.org/xml/features/validation/schema", validating);
                }
            }
            catch (Exception e) {
                if (validating) {
                    LOG.warn("Schema validation may not be supported: ", e);
                }
                LOG.trace("IGNORED", e);
            }
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/validation", validating);
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/namespaces", true);
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            try {
                if (validating) {
                    this._parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", validating);
                }
            }
            catch (Exception e) {
                LOG.warn(e.getMessage());
            }
        }
        catch (Exception e) {
            LOG.warn("Unable to set validating on XML Parser", e);
            throw new Error(e.toString());
        }
    }

    public boolean isValidating() {
        return this._parser.isValidating();
    }

    public SAXParser getSAXParser() {
        return this._parser;
    }

    public void redirectEntity(String name, URL entity) {
        if (entity != null) {
            try (AutoLock l = this._lock.lock();){
                this._redirectMap.put(name, entity);
            }
        }
    }

    public String getXpath() {
        return this._xpath;
    }

    public void setXpath(String xpath) {
        this._xpath = xpath;
        StringTokenizer tok = new StringTokenizer(xpath, "| ");
        while (tok.hasMoreTokens()) {
            this._xpaths = LazyList.add(this._xpaths, tok.nextToken());
        }
    }

    public String getDTD() {
        return this._dtd;
    }

    public void addContentHandler(String trigger, ContentHandler observer) {
        try (AutoLock l = this._lock.lock();){
            if (this._observerMap == null) {
                this._observerMap = new HashMap<String, ContentHandler>();
            }
            this._observerMap.put(trigger, observer);
        }
    }

    public Node parse(InputSource source2) throws IOException, SAXException {
        try (AutoLock l = this._lock.lock();){
            this._dtd = null;
            Handler handler = new Handler();
            XMLReader reader = this._parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(handler);
            reader.setEntityResolver(handler);
            if (LOG.isDebugEnabled()) {
                LOG.debug("parsing: sid={},pid={}", (Object)source2.getSystemId(), (Object)source2.getPublicId());
            }
            this._parser.parse(source2, (DefaultHandler)handler);
            if (handler._error != null) {
                throw handler._error;
            }
            Node doc = (Node)handler._top.get(0);
            handler.clear();
            Node node = doc;
            return node;
        }
    }

    public Node parse(String url2) throws IOException, SAXException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("parse: {}", (Object)url2);
        }
        return this.parse(new InputSource(url2));
    }

    public Node parse(File file) throws IOException, SAXException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("parse: {}", (Object)file);
        }
        return this.parse(new InputSource(Resource.toURL(file).toString()));
    }

    public Node parse(InputStream in) throws IOException, SAXException {
        return this.parse(new InputSource(in));
    }

    protected InputSource resolveEntity(String pid, String sid) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("resolveEntity({},{})", (Object)pid, (Object)sid);
        }
        if (sid != null && sid.endsWith(".dtd")) {
            this._dtd = sid;
        }
        URL entity = null;
        if (pid != null) {
            entity = this._redirectMap.get(pid);
        }
        if (entity == null) {
            entity = this._redirectMap.get(sid);
        }
        if (entity != null) {
            try {
                InputStream in = entity.openStream();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Redirected entity {}  --> {}", (Object)sid, (Object)entity);
                }
                InputSource is = new InputSource(in);
                is.setSystemId(sid);
                return is;
            }
            catch (IOException e) {
                LOG.trace("IGNORED", e);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entity not found for PID:{} / SID:{}", (Object)pid, (Object)sid);
        }
        return null;
    }

    private class Handler
    extends DefaultHandler {
        Node _top;
        SAXParseException _error;
        private Node _context;
        private NoopHandler _noop;

        Handler() {
            this._context = this._top = new Node(null, null, null);
            this._noop = new NoopHandler(this);
        }

        void clear() {
            this._top = null;
            this._error = null;
            this._context = null;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
            String name = null;
            if (XmlParser.this._parser.isNamespaceAware()) {
                name = localName;
            }
            if (name == null || "".equals(name)) {
                name = qName;
            }
            Node node = new Node(this._context, name, attrs);
            if (XmlParser.this._xpaths != null) {
                String path = node.getPath();
                boolean match = false;
                int i = LazyList.size(XmlParser.this._xpaths);
                while (!match && i-- > 0) {
                    String xpath = (String)LazyList.get(XmlParser.this._xpaths, i);
                    match = path.equals(xpath) || xpath.startsWith(path) && xpath.length() > path.length() && xpath.charAt(path.length()) == '/';
                }
                if (match) {
                    this._context.add(node);
                    this._context = node;
                } else {
                    XmlParser.this._parser.getXMLReader().setContentHandler(this._noop);
                }
            } else {
                this._context.add(node);
                this._context = node;
            }
            ContentHandler observer = null;
            if (XmlParser.this._observerMap != null) {
                observer = XmlParser.this._observerMap.get(name);
            }
            XmlParser.this._observers.push(observer);
            for (int i = 0; i < XmlParser.this._observers.size(); ++i) {
                if (XmlParser.this._observers.get(i) == null) continue;
                ((ContentHandler)XmlParser.this._observers.get(i)).startElement(uri, localName, qName, attrs);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            this._context = this._context._parent;
            for (int i = 0; i < XmlParser.this._observers.size(); ++i) {
                if (XmlParser.this._observers.get(i) == null) continue;
                ((ContentHandler)XmlParser.this._observers.get(i)).endElement(uri, localName, qName);
            }
            XmlParser.this._observers.pop();
        }

        @Override
        public void ignorableWhitespace(char[] buf, int offset, int len) throws SAXException {
            for (int i = 0; i < XmlParser.this._observers.size(); ++i) {
                if (XmlParser.this._observers.get(i) == null) continue;
                ((ContentHandler)XmlParser.this._observers.get(i)).ignorableWhitespace(buf, offset, len);
            }
        }

        @Override
        public void characters(char[] buf, int offset, int len) throws SAXException {
            this._context.add(new String(buf, offset, len));
            for (int i = 0; i < XmlParser.this._observers.size(); ++i) {
                if (XmlParser.this._observers.get(i) == null) continue;
                ((ContentHandler)XmlParser.this._observers.get(i)).characters(buf, offset, len);
            }
        }

        @Override
        public void warning(SAXParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.warn("SAX Parse Issue", ex);
            } else {
                LOG.warn("SAX Parse Issue @{} : {}", (Object)this.getLocationString(ex), (Object)ex.toString());
            }
        }

        @Override
        public void error(SAXParseException ex) throws SAXException {
            if (this._error == null) {
                this._error = ex;
            }
            if (LOG.isDebugEnabled()) {
                LOG.error("SAX Parse Issue", ex);
            } else {
                LOG.error("SAX Parse Issue @{} : {}", (Object)this.getLocationString(ex), (Object)ex.toString());
            }
        }

        @Override
        public void fatalError(SAXParseException ex) throws SAXException {
            this._error = ex;
            if (LOG.isDebugEnabled()) {
                LOG.error("Fatal AX Parse Issue", ex);
            } else {
                LOG.error("Fatal SAX Parse Issue @{} : {}", (Object)this.getLocationString(ex), (Object)ex.toString());
            }
            throw ex;
        }

        private String getLocationString(SAXParseException ex) {
            return ex.getSystemId() + " line:" + ex.getLineNumber() + " col:" + ex.getColumnNumber();
        }

        @Override
        public InputSource resolveEntity(String pid, String sid) {
            return XmlParser.this.resolveEntity(pid, sid);
        }
    }

    public static class Node
    extends AbstractList<Object> {
        Node _parent;
        private ArrayList<Object> _list;
        private String _tag;
        private Attribute[] _attrs;
        private boolean _lastString = false;
        private String _path;

        Node(Node parent, String tag, Attributes attrs) {
            this._parent = parent;
            this._tag = tag;
            if (attrs != null) {
                this._attrs = new Attribute[attrs.getLength()];
                for (int i = 0; i < attrs.getLength(); ++i) {
                    String name = attrs.getLocalName(i);
                    if (name == null || name.equals("")) {
                        name = attrs.getQName(i);
                    }
                    this._attrs[i] = new Attribute(name, attrs.getValue(i));
                }
            }
        }

        public Node getParent() {
            return this._parent;
        }

        public String getTag() {
            return this._tag;
        }

        public String getPath() {
            if (this._path == null) {
                this._path = this.getParent() != null && this.getParent().getTag() != null ? this.getParent().getPath() + "/" + this._tag : "/" + this._tag;
            }
            return this._path;
        }

        public Attribute[] getAttributes() {
            return this._attrs;
        }

        public String getAttribute(String name) {
            return this.getAttribute(name, null);
        }

        public String getAttribute(String name, String dft) {
            if (this._attrs == null || name == null) {
                return dft;
            }
            for (int i = 0; i < this._attrs.length; ++i) {
                if (!name.equals(this._attrs[i].getName())) continue;
                return this._attrs[i].getValue();
            }
            return dft;
        }

        @Override
        public int size() {
            if (this._list != null) {
                return this._list.size();
            }
            return 0;
        }

        @Override
        public Object get(int i) {
            if (this._list != null) {
                return this._list.get(i);
            }
            return null;
        }

        public Node get(String tag) {
            if (this._list != null) {
                for (int i = 0; i < this._list.size(); ++i) {
                    Object o = this._list.get(i);
                    if (!(o instanceof Node)) continue;
                    Node n = (Node)o;
                    if (!tag.equals(n._tag)) continue;
                    return n;
                }
            }
            return null;
        }

        @Override
        public void add(int i, Object o) {
            if (this._list == null) {
                this._list = new ArrayList();
            }
            if (o instanceof String) {
                if (this._lastString) {
                    int last = this._list.size() - 1;
                    this._list.set(last, (String)this._list.get(last) + o);
                } else {
                    this._list.add(i, o);
                }
                this._lastString = true;
            } else {
                this._lastString = false;
                this._list.add(i, o);
            }
        }

        @Override
        public void clear() {
            if (this._list != null) {
                this._list.clear();
            }
            this._list = null;
        }

        public String getString(String tag, boolean tags, boolean trim) {
            Node node = this.get(tag);
            if (node == null) {
                return null;
            }
            String s2 = node.toString(tags);
            if (s2 != null && trim) {
                s2 = s2.trim();
            }
            return s2;
        }

        @Override
        public String toString() {
            return this.toString(true);
        }

        public String toString(boolean tag) {
            StringBuilder buf = new StringBuilder();
            this.toString(buf, tag);
            return buf.toString();
        }

        public String toString(boolean tag, boolean trim) {
            String s2 = this.toString(tag);
            if (s2 != null && trim) {
                s2 = s2.trim();
            }
            return s2;
        }

        private void toString(StringBuilder buf, boolean tag) {
            int i;
            if (tag) {
                buf.append("<");
                buf.append(this._tag);
                if (this._attrs != null) {
                    for (i = 0; i < this._attrs.length; ++i) {
                        buf.append(' ');
                        buf.append(this._attrs[i].getName());
                        buf.append("=\"");
                        buf.append(this._attrs[i].getValue());
                        buf.append("\"");
                    }
                }
            }
            if (this._list != null) {
                if (tag) {
                    buf.append(">");
                }
                for (i = 0; i < this._list.size(); ++i) {
                    Object o = this._list.get(i);
                    if (o == null) continue;
                    if (o instanceof Node) {
                        ((Node)o).toString(buf, tag);
                        continue;
                    }
                    buf.append(o.toString());
                }
                if (tag) {
                    buf.append("</");
                    buf.append(this._tag);
                    buf.append(">");
                }
            } else if (tag) {
                buf.append("/>");
            }
        }

        public Iterator<Node> iterator(final String tag) {
            return new Iterator<Node>(){
                int c = 0;
                Node _node;

                @Override
                public boolean hasNext() {
                    if (this._node != null) {
                        return true;
                    }
                    while (_list != null && this.c < _list.size()) {
                        Object o = _list.get(this.c);
                        if (o instanceof Node) {
                            Node n = (Node)o;
                            if (tag.equals(n._tag)) {
                                this._node = n;
                                return true;
                            }
                        }
                        ++this.c;
                    }
                    return false;
                }

                @Override
                public Node next() {
                    try {
                        if (this.hasNext()) {
                            Node node = this._node;
                            return node;
                        }
                        throw new NoSuchElementException();
                    }
                    finally {
                        this._node = null;
                        ++this.c;
                    }
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Not supported");
                }
            };
        }
    }

    public static class Attribute {
        private String _name;
        private String _value;

        Attribute(String n, String v) {
            this._name = n;
            this._value = v;
        }

        public String getName() {
            return this._name;
        }

        public String getValue() {
            return this._value;
        }
    }

    private class NoopHandler
    extends DefaultHandler {
        Handler _next;
        int _depth;

        NoopHandler(Handler next) {
            this._next = next;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
            ++this._depth;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this._depth == 0) {
                XmlParser.this._parser.getXMLReader().setContentHandler(this._next);
            } else {
                --this._depth;
            }
        }
    }
}

