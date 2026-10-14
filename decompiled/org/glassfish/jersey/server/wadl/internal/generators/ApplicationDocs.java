/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators;

import com.sun.research.ws.wadl.Doc;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="applicationDocs", propOrder={"docs"})
@XmlRootElement(name="applicationDocs")
public class ApplicationDocs {
    @XmlElement(name="doc")
    protected List<Doc> docs;

    public List<Doc> getDocs() {
        if (this.docs == null) {
            this.docs = new ArrayList<Doc>();
        }
        return this.docs;
    }
}

