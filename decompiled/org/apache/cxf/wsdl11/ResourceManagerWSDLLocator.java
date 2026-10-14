/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.io.InputStream;
import java.net.URL;
import javax.wsdl.xml.WSDLLocator;
import org.apache.cxf.Bus;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.wsdl11.AbstractWrapperWSDLLocator;
import org.apache.cxf.wsdl11.CatalogWSDLLocator;
import org.xml.sax.InputSource;

public class ResourceManagerWSDLLocator
extends AbstractWrapperWSDLLocator {
    Bus bus;

    public ResourceManagerWSDLLocator(String wsdlUrl, WSDLLocator parent, Bus bus) {
        super(wsdlUrl, parent);
        this.bus = bus;
    }

    public ResourceManagerWSDLLocator(String wsdlUrl, Bus bus) {
        super(wsdlUrl, new CatalogWSDLLocator(wsdlUrl, bus));
        this.bus = bus;
    }

    @Override
    public InputSource getInputSource() {
        InputSource src = this.getInputSource(null, this.wsdlUrl);
        this.baseUri = src.getPublicId();
        return src;
    }

    @Override
    public InputSource getInputSource(String parentLocation, String importLocation) {
        InputStream ins = this.bus.getExtension(ResourceManager.class).getResourceAsStream(importLocation);
        InputSource is = new InputSource(ins);
        is.setSystemId(importLocation);
        is.setPublicId(importLocation);
        URL url2 = this.bus.getExtension(ResourceManager.class).resolveResource(importLocation, URL.class);
        if (url2 != null) {
            is.setSystemId(url2.toString());
            is.setPublicId(url2.toString());
        }
        return is;
    }
}

