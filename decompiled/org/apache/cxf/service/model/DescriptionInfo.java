/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractDescriptionElement;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.NamedItem;

public class DescriptionInfo
extends AbstractPropertiesHolder
implements NamedItem {
    QName name;
    String uri;
    List<AbstractDescriptionElement> described = new ArrayList<AbstractDescriptionElement>();

    public void setName(QName n) {
        this.name = n;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    public void setBaseURI(String u) {
        this.uri = u;
    }

    public String getBaseURI() {
        return this.uri;
    }

    public List<AbstractDescriptionElement> getDescribed() {
        return this.described;
    }
}

