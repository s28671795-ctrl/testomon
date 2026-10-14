/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Logging;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.FactoryBeanListener;

public class OldLoggingFactoryBeanListener
implements FactoryBeanListener {
    static final Logger LOG = LogUtils.getL7dLogger(OldLoggingFactoryBeanListener.class);

    @Override
    public void handleEvent(FactoryBeanListener.Event ev, AbstractServiceFactoryBean factory2, Object ... args2) {
        switch (ev) {
            case ENDPOINT_SELECTED: {
                Class cls = (Class)args2[2];
                Endpoint ep = (Endpoint)args2[1];
                Bus bus = factory2.getBus();
                if (cls == null) {
                    return;
                }
                this.addLoggingSupport(ep, bus, cls.getAnnotation(Logging.class));
                break;
            }
            case SERVER_CREATED: {
                Class cls = (Class)args2[2];
                if (cls == null) {
                    return;
                }
                Server server = (Server)args2[0];
                Bus bus = factory2.getBus();
                this.addLoggingSupport(server.getEndpoint(), bus, cls.getAnnotation(Logging.class));
                break;
            }
        }
    }

    private void addLoggingSupport(Endpoint endpoint, Bus bus, Logging annotation) {
        if (annotation != null) {
            LoggingFeature lf = new LoggingFeature(annotation);
            LOG.warning("Deprecated logging interceptors being used, switch to cxf-rt-ext-logging based logging.");
            lf.initialize(endpoint, bus);
        }
    }
}

