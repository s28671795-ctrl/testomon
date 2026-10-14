/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.wss4j.common.util.NSStack;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class DOM2Writer {
    public static final char NL = '\n';
    public static final String LS = System.getProperty("line.separator", Character.valueOf('\n').toString());

    private DOM2Writer() {
    }

    public static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        DOM2Writer.serializeAsXML(node, sw, true);
        return sw.toString();
    }

    public static String nodeToString(Node node, boolean omitXMLDecl) {
        StringWriter sw = new StringWriter();
        DOM2Writer.serializeAsXML(node, sw, omitXMLDecl);
        return sw.toString();
    }

    public static void serializeAsXML(Node node, Writer writer, boolean omitXMLDecl) {
        DOM2Writer.serializeAsXML(node, writer, omitXMLDecl, false);
    }

    public static void serializeAsXML(Node node, Writer writer, boolean omitXMLDecl, boolean pretty) {
        PrintWriter out = new PrintWriter(writer);
        if (!omitXMLDecl) {
            out.print("<?xml version=\"1.0\" encoding=UTF-8 ?>");
        }
        NSStack namespaceStack = new NSStack();
        DOM2Writer.print(node, namespaceStack, out, pretty, 0);
        out.flush();
    }

    private static void print(Node node, NSStack namespaceStack, PrintWriter out, boolean pretty, int indent) {
        if (node == null) {
            return;
        }
        boolean hasChildren = false;
        short type = node.getNodeType();
        switch (type) {
            case 9: {
                Node child;
                for (child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                    DOM2Writer.print(child, namespaceStack, out, pretty, indent);
                }
                break;
            }
            case 1: {
                NamedNodeMap attrs;
                namespaceStack.push();
                if (pretty) {
                    for (int i = 0; i < indent; ++i) {
                        out.print(' ');
                    }
                }
                out.print('<' + node.getNodeName());
                String elPrefix = node.getPrefix();
                String elNamespaceURI = node.getNamespaceURI();
                if (elPrefix != null && elNamespaceURI != null && elPrefix.length() > 0) {
                    boolean prefixIsDeclared = false;
                    try {
                        String namespaceURI = namespaceStack.getNamespaceURI(elPrefix);
                        if (elNamespaceURI.equals(namespaceURI)) {
                            prefixIsDeclared = true;
                        }
                    }
                    catch (IllegalArgumentException namespaceURI) {
                        // empty catch block
                    }
                    if (!prefixIsDeclared) {
                        DOM2Writer.printNamespaceDecl(node, namespaceStack, out);
                    }
                }
                int len = (attrs = node.getAttributes()) != null ? attrs.getLength() : 0;
                for (int i = 0; i < len; ++i) {
                    Attr attr = (Attr)attrs.item(i);
                    out.print(' ' + attr.getNodeName() + "=\"");
                    DOM2Writer.normalize(attr.getValue(), out);
                    out.print('\"');
                    String attrPrefix = attr.getPrefix();
                    String attrNamespaceURI = attr.getNamespaceURI();
                    if (attrPrefix == null || attrNamespaceURI == null) continue;
                    boolean prefixIsDeclared = false;
                    try {
                        String namespaceURI = namespaceStack.getNamespaceURI(attrPrefix);
                        if (attrNamespaceURI.equals(namespaceURI)) {
                            prefixIsDeclared = true;
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        // empty catch block
                    }
                    if (prefixIsDeclared) continue;
                    DOM2Writer.printNamespaceDecl(attr, namespaceStack, out);
                }
                Node child = node.getFirstChild();
                if (child != null) {
                    hasChildren = true;
                    out.print('>');
                    if (pretty) {
                        out.print(LS);
                    }
                    while (child != null) {
                        DOM2Writer.print(child, namespaceStack, out, pretty, indent + 1);
                        child = child.getNextSibling();
                    }
                } else {
                    hasChildren = false;
                    out.print("/>");
                    if (pretty) {
                        out.print(LS);
                    }
                }
                namespaceStack.pop();
                break;
            }
            case 5: {
                out.print('&');
                out.print(node.getNodeName());
                out.print(';');
                break;
            }
            case 4: {
                out.print("<![CDATA[");
                out.print(node.getNodeValue());
                out.print("]]>");
                break;
            }
            case 3: {
                DOM2Writer.normalize(node.getNodeValue(), out);
                break;
            }
            case 8: {
                out.print("<!--");
                out.print(node.getNodeValue());
                out.print("-->");
                if (!pretty) break;
                out.print(LS);
                break;
            }
            case 7: {
                out.print("<?");
                out.print(node.getNodeName());
                String data = node.getNodeValue();
                if (data != null && data.length() > 0) {
                    out.print(' ');
                    out.print(data);
                }
                out.println("?>");
                if (!pretty) break;
                out.print(LS);
            }
        }
        if (type == 1 && hasChildren) {
            if (pretty) {
                for (int i = 0; i < indent; ++i) {
                    out.print(' ');
                }
            }
            out.print("</");
            out.print(node.getNodeName());
            out.print('>');
            if (pretty) {
                out.print(LS);
            }
            hasChildren = false;
        }
    }

    private static void printNamespaceDecl(Node node, NSStack namespaceStack, PrintWriter out) {
        switch (node.getNodeType()) {
            case 2: {
                DOM2Writer.printNamespaceDecl(((Attr)node).getOwnerElement(), node, namespaceStack, out);
                break;
            }
            case 1: {
                DOM2Writer.printNamespaceDecl((Element)node, node, namespaceStack, out);
            }
        }
    }

    private static void printNamespaceDecl(Element owner, Node node, NSStack namespaceStack, PrintWriter out) {
        String namespaceURI = node.getNamespaceURI();
        String prefix = node.getPrefix();
        if (!(namespaceURI.equals("http://www.w3.org/2000/xmlns/") && "xmlns".equals(prefix) || namespaceURI.equals("http://www.w3.org/XML/1998/namespace") && "xml".equals(prefix))) {
            if (XMLUtils.getNamespace(prefix, owner) == null) {
                out.print(" xmlns:" + prefix + "=\"" + namespaceURI + '\"');
            }
        } else {
            prefix = node.getLocalName();
            namespaceURI = node.getNodeValue();
        }
        namespaceStack.add(namespaceURI, prefix);
    }

    public static void normalize(String s2, PrintWriter fOut) {
        int len = s2 != null ? s2.length() : 0;
        block6: for (int i = 0; i < len; ++i) {
            char c = s2.charAt(i);
            switch (c) {
                case '<': {
                    fOut.print("&lt;");
                    continue block6;
                }
                case '>': {
                    fOut.print("&gt;");
                    continue block6;
                }
                case '&': {
                    fOut.print("&amp;");
                    continue block6;
                }
                case '\"': {
                    fOut.print("&quot;");
                    continue block6;
                }
                default: {
                    fOut.print(c);
                }
            }
        }
    }
}

