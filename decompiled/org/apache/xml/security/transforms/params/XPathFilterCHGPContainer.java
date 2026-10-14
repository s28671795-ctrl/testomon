/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.transforms.params;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class XPathFilterCHGPContainer
extends ElementProxy
implements TransformParam {
    public static final String TRANSFORM_XPATHFILTERCHGP = "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
    private static final String _TAG_INCLUDE_BUT_SEARCH = "IncludeButSearch";
    private static final String _TAG_EXCLUDE_BUT_SEARCH = "ExcludeButSearch";
    private static final String _TAG_EXCLUDE = "Exclude";
    public static final String _TAG_XPATHCHGP = "XPathAlternative";
    public static final String _ATT_INCLUDESLASH = "IncludeSlashPolicy";
    public static final boolean IncludeSlash = true;
    public static final boolean ExcludeSlash = false;

    private XPathFilterCHGPContainer() {
    }

    private XPathFilterCHGPContainer(Document doc, boolean includeSlashPolicy, String includeButSearch, String excludeButSearch, String exclude) {
        super(doc);
        if (includeSlashPolicy) {
            this.setLocalAttribute(_ATT_INCLUDESLASH, "true");
        } else {
            this.setLocalAttribute(_ATT_INCLUDESLASH, "false");
        }
        if (includeButSearch != null && includeButSearch.trim().length() > 0) {
            Element includeButSearchElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), _TAG_INCLUDE_BUT_SEARCH);
            includeButSearchElem.appendChild(this.createText(XPathFilterCHGPContainer.indentXPathText(includeButSearch)));
            this.addReturnToSelf();
            this.appendSelf(includeButSearchElem);
        }
        if (excludeButSearch != null && excludeButSearch.trim().length() > 0) {
            Element excludeButSearchElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), _TAG_EXCLUDE_BUT_SEARCH);
            excludeButSearchElem.appendChild(this.createText(XPathFilterCHGPContainer.indentXPathText(excludeButSearch)));
            this.addReturnToSelf();
            this.appendSelf(excludeButSearchElem);
        }
        if (exclude != null && exclude.trim().length() > 0) {
            Element excludeElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), _TAG_EXCLUDE);
            excludeElem.appendChild(this.createText(XPathFilterCHGPContainer.indentXPathText(exclude)));
            this.addReturnToSelf();
            this.appendSelf(excludeElem);
        }
        this.addReturnToSelf();
    }

    static String indentXPathText(String xp) {
        if (xp.length() > 2 && !Character.isWhitespace(xp.charAt(0))) {
            return "\n" + xp + "\n";
        }
        return xp;
    }

    private XPathFilterCHGPContainer(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    public static XPathFilterCHGPContainer getInstance(Document doc, boolean includeSlashPolicy, String includeButSearch, String excludeButSearch, String exclude) {
        return new XPathFilterCHGPContainer(doc, includeSlashPolicy, includeButSearch, excludeButSearch, exclude);
    }

    public static XPathFilterCHGPContainer getInstance(Element element, String baseURI) throws XMLSecurityException {
        return new XPathFilterCHGPContainer(element, baseURI);
    }

    private String getXStr(String type) {
        if (this.length(this.getBaseNamespace(), type) != 1) {
            return "";
        }
        Element xElem = XMLUtils.selectNode(this.getElement().getFirstChild(), this.getBaseNamespace(), type, 0);
        return XMLUtils.getFullTextChildrenFromNode(xElem);
    }

    public String getIncludeButSearch() {
        return this.getXStr(_TAG_INCLUDE_BUT_SEARCH);
    }

    public String getExcludeButSearch() {
        return this.getXStr(_TAG_EXCLUDE_BUT_SEARCH);
    }

    public String getExclude() {
        return this.getXStr(_TAG_EXCLUDE);
    }

    public boolean getIncludeSlashPolicy() {
        return this.getLocalAttribute(_ATT_INCLUDESLASH).equals("true");
    }

    private Node getHereContextNode(String type) {
        if (this.length(this.getBaseNamespace(), type) != 1) {
            return null;
        }
        return XPathFilterCHGPContainer.selectNodeText(this.getFirstChild(), this.getBaseNamespace(), type, 0);
    }

    private static Text selectNodeText(Node sibling, String uri, String nodeName, int number) {
        Node n = XMLUtils.selectNode(sibling, uri, nodeName, number);
        if (n == null) {
            return null;
        }
        for (n = n.getFirstChild(); n != null && n.getNodeType() != 3; n = n.getNextSibling()) {
        }
        return (Text)n;
    }

    public Node getHereContextNodeIncludeButSearch() {
        return this.getHereContextNode(_TAG_INCLUDE_BUT_SEARCH);
    }

    public Node getHereContextNodeExcludeButSearch() {
        return this.getHereContextNode(_TAG_EXCLUDE_BUT_SEARCH);
    }

    public Node getHereContextNodeExclude() {
        return this.getHereContextNode(_TAG_EXCLUDE);
    }

    @Override
    public final String getBaseLocalName() {
        return _TAG_XPATHCHGP;
    }

    @Override
    public final String getBaseNamespace() {
        return TRANSFORM_XPATHFILTERCHGP;
    }
}

