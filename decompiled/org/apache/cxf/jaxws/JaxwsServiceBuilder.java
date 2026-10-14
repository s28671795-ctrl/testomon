/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.io.File;
import java.net.URI;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.frontend.AbstractServiceFactory;
import org.apache.cxf.jaxws.binding.soap.JaxWsSoapBindingConfiguration;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;

public class JaxwsServiceBuilder
extends AbstractServiceFactory {
    final JaxWsServiceFactoryBean serviceFactory = new JaxWsServiceFactoryBean();

    public JaxwsServiceBuilder() {
        this.serviceFactory.setPopulateFromClass(true);
        this.setServiceFactory(this.serviceFactory);
        this.setBindingConfig(new JaxWsSoapBindingConfiguration(this.serviceFactory));
    }

    @Override
    public File getOutputFile() {
        JaxWsImplementorInfo jaxwsImpl = this.serviceFactory.getJaxWsImplementorInfo();
        String wsdlLocation = jaxwsImpl.getWsdlLocation();
        if (!StringUtils.isEmpty(wsdlLocation)) {
            try {
                File f;
                URI uri = new URI(wsdlLocation);
                if (("file".equals(uri.getScheme()) || StringUtils.isEmpty(uri.getScheme())) && (f = new File(uri)).exists()) {
                    return f;
                }
            }
            catch (Exception uri) {
                // empty catch block
            }
            File f = new File(wsdlLocation);
            if (f.exists()) {
                return f;
            }
        }
        return super.getOutputFile();
    }
}

