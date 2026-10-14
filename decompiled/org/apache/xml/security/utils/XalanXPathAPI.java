/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.xml.utils.PrefixResolver
 *  org.apache.xml.utils.PrefixResolverDefault
 *  org.apache.xpath.Expression
 *  org.apache.xpath.XPath
 *  org.apache.xpath.XPathContext
 *  org.apache.xpath.compiler.FunctionTable
 *  org.apache.xpath.objects.XObject
 */
package org.apache.xml.security.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xml.security.transforms.implementations.FuncHere;
import org.apache.xml.security.utils.XPathAPI;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.PrefixResolverDefault;
import org.apache.xpath.Expression;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.compiler.FunctionTable;
import org.apache.xpath.objects.XObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XalanXPathAPI
implements XPathAPI {
    private static final Logger LOG = LoggerFactory.getLogger(XalanXPathAPI.class);
    private String xpathStr;
    private XPath xpath;
    private static FunctionTable funcTable;
    private static boolean installed;
    private XPathContext context;

    @Override
    public NodeList selectNodeList(Node contextNode, Node xpathnode, String str, Node namespaceNode) throws TransformerException {
        XObject list = this.eval(contextNode, xpathnode, str, namespaceNode);
        return list.nodelist();
    }

    @Override
    public boolean evaluate(Node contextNode, Node xpathnode, String str, Node namespaceNode) throws TransformerException {
        XObject object = this.eval(contextNode, xpathnode, str, namespaceNode);
        return object.bool();
    }

    @Override
    public void clear() {
        this.xpathStr = null;
        this.xpath = null;
        this.context = null;
    }

    public static synchronized boolean isInstalled() {
        return installed;
    }

    private XObject eval(Node contextNode, Node xpathnode, String str, Node namespaceNode) throws TransformerException {
        if (this.context == null) {
            this.context = new XPathContext((Object)xpathnode);
            this.context.setSecureProcessing(true);
        }
        Node resolverNode = namespaceNode.getNodeType() == 9 ? ((Document)namespaceNode).getDocumentElement() : namespaceNode;
        PrefixResolverDefault prefixResolver = new PrefixResolverDefault(resolverNode);
        if (!str.equals(this.xpathStr)) {
            if (str.indexOf("here()") > 0) {
                this.context.reset();
            }
            this.xpath = this.createXPath(str, (PrefixResolver)prefixResolver);
            this.xpathStr = str;
        }
        int ctxtNode = this.context.getDTMHandleFromNode(contextNode);
        return this.xpath.execute(this.context, ctxtNode, (PrefixResolver)prefixResolver);
    }

    private XPath createXPath(String str, PrefixResolver prefixResolver) throws TransformerException {
        XPath xpath = null;
        Class[] classes = new Class[]{String.class, SourceLocator.class, PrefixResolver.class, Integer.TYPE, ErrorListener.class, FunctionTable.class};
        Object[] objects = new Object[]{str, null, prefixResolver, 0, null, funcTable};
        try {
            Constructor constructor = XPath.class.getConstructor(classes);
            xpath = (XPath)constructor.newInstance(objects);
        }
        catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        if (xpath == null) {
            xpath = new XPath(str, null, prefixResolver, 0, null);
        }
        return xpath;
    }

    private static synchronized void fixupFunctionTable() {
        Object[] params;
        Method installFunction;
        Class[] args2;
        installed = false;
        if (new FunctionTable().functionAvailable("here")) {
            LOG.debug("Here function already registered");
            installed = true;
            return;
        }
        LOG.debug("Registering Here function");
        try {
            args2 = new Class[]{String.class, Expression.class};
            installFunction = FunctionTable.class.getMethod("installFunction", args2);
            if ((installFunction.getModifiers() & 8) != 0) {
                params = new Object[]{"here", new FuncHere()};
                installFunction.invoke(null, params);
                installed = true;
            }
        }
        catch (Exception ex) {
            LOG.debug("Error installing function using the static installFunction method", ex);
        }
        if (!installed) {
            try {
                funcTable = new FunctionTable();
                args2 = new Class[]{String.class, Class.class};
                installFunction = FunctionTable.class.getMethod("installFunction", args2);
                params = new Object[]{"here", FuncHere.class};
                installFunction.invoke((Object)funcTable, params);
                installed = true;
            }
            catch (Exception ex) {
                LOG.debug("Error installing function using the static installFunction method", ex);
            }
        }
        if (installed) {
            LOG.debug("Registered class {} for XPath function 'here()' function in internal table", (Object)FuncHere.class.getName());
        } else {
            LOG.debug("Unable to register class {} for XPath function 'here()' function in internal table", (Object)FuncHere.class.getName());
        }
    }

    static {
        XalanXPathAPI.fixupFunctionTable();
    }
}

