/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.util.StringTokenizer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class QNameSupport {
    private QNameSupport() {
    }

    @Nonnull
    public static QName constructQName(@Nonnull Element owningElement, @Nonnull @NotEmpty String qname) {
        String name;
        String nsPrefix;
        Constraint.isNotNull(owningElement, "Owning element cannot be null");
        String trimmedName = Constraint.isNotNull(StringSupport.trimOrNull(qname), "QName cannot be null");
        if (trimmedName.indexOf(":") > -1) {
            StringTokenizer qnameTokens = new StringTokenizer(trimmedName, ":");
            nsPrefix = StringSupport.trim(qnameTokens.nextToken());
            name = qnameTokens.nextToken();
        } else {
            nsPrefix = null;
            name = trimmedName;
        }
        String nsURI = owningElement.lookupNamespaceURI(nsPrefix);
        return QNameSupport.constructQName(nsURI, name, nsPrefix);
    }

    @Nonnull
    public static QName constructQName(@Nullable String namespaceURI, @Nonnull @NotEmpty String localName, @Nullable String prefix) {
        String trimmedLocalName = Constraint.isNotNull(StringSupport.trimOrNull(localName), "Local name cannot be null or empty");
        String trimmedPrefix = StringSupport.trimOrNull(prefix);
        if (trimmedPrefix == null) {
            return new QName(StringSupport.trimOrNull(namespaceURI), trimmedLocalName);
        }
        return new QName(StringSupport.trimOrNull(namespaceURI), trimmedLocalName, trimmedPrefix);
    }

    @Nullable
    public static QName getNodeQName(@Nullable Node domNode) {
        if (domNode == null) {
            return null;
        }
        return QNameSupport.constructQName(domNode.getNamespaceURI(), domNode.getLocalName(), domNode.getPrefix());
    }

    @Nonnull
    public static String qnameToContentString(@Nonnull QName qname) {
        Constraint.isNotNull(qname, "QName may not be null");
        StringBuffer buf = new StringBuffer();
        String s2 = StringSupport.trimOrNull(qname.getPrefix());
        if (s2 != null) {
            buf.append(qname.getPrefix());
            buf.append(":");
        }
        buf.append(qname.getLocalPart());
        return buf.toString();
    }
}

