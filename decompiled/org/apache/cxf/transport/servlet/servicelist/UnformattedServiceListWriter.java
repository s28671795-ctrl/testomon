/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.servlet.servicelist;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.commons_text.StringEscapeUtils;
import org.apache.cxf.transport.servlet.ServletDestination;
import org.apache.cxf.transport.servlet.servicelist.ServiceListWriter;

public class UnformattedServiceListWriter
implements ServiceListWriter {
    boolean renderWsdlList;
    Bus bus;

    public UnformattedServiceListWriter(boolean renderWsdlList, Bus bus) {
        this.renderWsdlList = renderWsdlList;
        this.bus = bus;
    }

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void writeServiceList(PrintWriter writer, String basePath, AbstractDestination[] soapDestinations, AbstractDestination[] restDestinations) throws IOException {
        if (soapDestinations.length > 0 || restDestinations.length > 0) {
            if (soapDestinations.length > 0) {
                this.writeUnformattedSOAPEndpoints(writer, basePath, soapDestinations);
            }
            if (restDestinations.length > 0) {
                this.writeUnformattedRESTfulEndpoints(writer, basePath, restDestinations);
            }
        } else {
            writer.write("No services have been found.");
        }
    }

    private void writeUnformattedSOAPEndpoints(PrintWriter writer, String baseAddress, AbstractDestination[] destinations) throws IOException {
        for (AbstractDestination sd : destinations) {
            String address = this.getAbsoluteAddress(baseAddress, sd);
            address = StringEscapeUtils.escapeHtml4(address);
            writer.write(address);
            if (this.renderWsdlList) {
                writer.write("?wsdl");
            }
            writer.write(10);
        }
        writer.write(10);
    }

    private void writeUnformattedRESTfulEndpoints(PrintWriter writer, String baseAddress, AbstractDestination[] destinations) throws IOException {
        for (AbstractDestination sd : destinations) {
            boolean openApiAvailable;
            String address = this.getAbsoluteAddress(baseAddress, sd);
            address = StringEscapeUtils.escapeHtml4(address);
            boolean wadlAvailable = this.bus != null && PropertyUtils.isTrue(this.bus.getProperty("wadl.service.descrition.available"));
            boolean swaggerAvailable = this.bus != null && PropertyUtils.isTrue(this.bus.getProperty("swagger.service.descrition.available"));
            boolean bl = openApiAvailable = this.bus != null && PropertyUtils.isTrue(this.bus.getProperty("openapi.service.descrition.available"));
            if (!wadlAvailable && !swaggerAvailable) {
                writer.write(address + "\n");
                return;
            }
            if (wadlAvailable) {
                writer.write(address + "?_wadl\n");
            }
            if (swaggerAvailable) {
                writer.write(address + "/swagger.json\n");
            }
            if (!openApiAvailable) continue;
            writer.write(address + "/openapi.json\n");
        }
    }

    private String getAbsoluteAddress(String basePath, AbstractDestination d) {
        String endpointAddress = (String)d.getEndpointInfo().getProperty("publishedEndpointUrl");
        if (endpointAddress != null) {
            return endpointAddress;
        }
        endpointAddress = d.getEndpointInfo().getAddress();
        if (d instanceof ServletDestination && (endpointAddress.startsWith("http://") || endpointAddress.startsWith("https://"))) {
            String path = ((ServletDestination)d).getPath();
            return basePath + path;
        }
        if (basePath == null || endpointAddress.startsWith(basePath)) {
            return endpointAddress;
        }
        return basePath + endpointAddress;
    }
}

