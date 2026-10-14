/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import javax.wsdl.extensions.soap.SOAPBinding;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;

public class WSDLBasedServiceConfiguration
extends AbstractServiceConfiguration {
    private BindingInfo bi;

    public WSDLBasedServiceConfiguration(BindingInfo bi) {
        this.bi = bi;
    }

    @Override
    public String getStyle() {
        SOAPBinding sb = this.bi.getExtensor(SOAPBinding.class);
        return sb == null ? null : sb.getStyle();
    }
}

