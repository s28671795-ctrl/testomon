/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaEnumerationFacet;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaFractionDigitsFacet;
import org.apache.ws.commons.schema.XmlSchemaLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxExclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaMinExclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMinInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMinLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaPatternFacet;
import org.apache.ws.commons.schema.XmlSchemaTotalDigitsFacet;
import org.apache.ws.commons.schema.XmlSchemaWhiteSpaceFacet;
import org.w3c.dom.Element;

public abstract class XmlSchemaFacet
extends XmlSchemaAnnotated {
    boolean fixed;
    Object value;

    protected XmlSchemaFacet() {
    }

    protected XmlSchemaFacet(Object value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public static XmlSchemaFacet construct(Element el) {
        XmlSchemaFacet facet;
        String name = el.getLocalName();
        boolean fixed = false;
        if (el.getAttribute("fixed").equals("true")) {
            fixed = true;
        }
        if ("enumeration".equals(name)) {
            facet = new XmlSchemaEnumerationFacet();
        } else if ("fractionDigits".equals(name)) {
            facet = new XmlSchemaFractionDigitsFacet();
        } else if ("length".equals(name)) {
            facet = new XmlSchemaLengthFacet();
        } else if ("maxExclusive".equals(name)) {
            facet = new XmlSchemaMaxExclusiveFacet();
        } else if ("maxInclusive".equals(name)) {
            facet = new XmlSchemaMaxInclusiveFacet();
        } else if ("maxLength".equals(name)) {
            facet = new XmlSchemaMaxLengthFacet();
        } else if ("minLength".equals(name)) {
            facet = new XmlSchemaMinLengthFacet();
        } else if ("minExclusive".equals(name)) {
            facet = new XmlSchemaMinExclusiveFacet();
        } else if ("minInclusive".equals(name)) {
            facet = new XmlSchemaMinInclusiveFacet();
        } else if ("pattern".equals(name)) {
            facet = new XmlSchemaPatternFacet();
        } else if ("totalDigits".equals(name)) {
            facet = new XmlSchemaTotalDigitsFacet();
        } else if ("whiteSpace".equals(name)) {
            facet = new XmlSchemaWhiteSpaceFacet();
        } else {
            throw new XmlSchemaException("Incorrect facet with name \"" + name + "\" found.");
        }
        if (el.hasAttribute("id")) {
            facet.setId(el.getAttribute("id"));
        }
        facet.setFixed(fixed);
        facet.setValue(el.getAttribute("value"));
        return facet;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

