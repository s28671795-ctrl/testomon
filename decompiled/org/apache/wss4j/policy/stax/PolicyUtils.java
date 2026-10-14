/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax;

import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.policy.model.XPath;

public final class PolicyUtils {
    private PolicyUtils() {
    }

    public static List<QName> getElementPath(XPath xPath) {
        LinkedList<QName> elements = new LinkedList<QName>();
        String[] xPathElements = xPath.getXPath().split("/");
        for (int j = 0; j < xPathElements.length; ++j) {
            String xPathElement = xPathElements[j];
            if (xPathElement == null || "".equals(xPathElement)) continue;
            String[] elementParts = xPathElement.split(":");
            if (elementParts.length == 2) {
                String ns = xPath.getPrefixNamespaceMap().get(elementParts[0]);
                if (ns == null) {
                    throw new IllegalArgumentException("Namespace not declared");
                }
                elements.add(new QName(ns, elementParts[1]));
                continue;
            }
            elements.add(new QName(elementParts[0]));
        }
        return elements;
    }
}

