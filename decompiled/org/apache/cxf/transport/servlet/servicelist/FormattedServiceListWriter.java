/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.servlet.servicelist;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.commons_text.StringEscapeUtils;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.servlet.servicelist.ServiceListWriter;

public class FormattedServiceListWriter
implements ServiceListWriter {
    private String styleSheetPath;
    private String title;
    private Map<String, String> atomMap;
    private boolean showForeignContexts;
    private Bus bus;

    public FormattedServiceListWriter(String styleSheetPath, String title, boolean showForeignContexts, Bus bus) {
        this.styleSheetPath = StringEscapeUtils.escapeHtml4(styleSheetPath);
        this.title = title;
        this.showForeignContexts = showForeignContexts;
        this.bus = bus;
        if (this.bus != null) {
            this.atomMap = CastUtils.cast((Map)this.bus.getProperty("org.apache.cxf.extensions.logging.atom.pull"));
        }
    }

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void writeServiceList(PrintWriter writer, String basePath, AbstractDestination[] soapDestinations, AbstractDestination[] restDestinations) throws IOException {
        writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        writer.write("<HTML><HEAD>");
        writer.write("<LINK type=\"text/css\" rel=\"stylesheet\" href=\"" + this.styleSheetPath + "\">");
        writer.write("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        if (this.title != null) {
            writer.write("<title>" + this.title + "</title>");
        } else {
            writer.write("<title>CXF - Service list</title>");
        }
        writer.write("</head><body>");
        if (soapDestinations.length > 0 || restDestinations.length > 0) {
            if (soapDestinations.length > 0) {
                this.writeSOAPEndpoints(writer, basePath, soapDestinations);
            }
            if (restDestinations.length > 0) {
                this.writeRESTfulEndpoints(writer, basePath, restDestinations);
            }
        } else {
            writer.write("<span class=\"heading\">No services have been found.</span>");
        }
        writer.write("</body></html>");
    }

    private void writeSOAPEndpoints(PrintWriter writer, String basePath, AbstractDestination[] destinations) throws IOException {
        writer.write("<span class=\"heading\">Available SOAP services:</span><br/>");
        writer.write("<table " + (this.styleSheetPath.endsWith("stylesheet=1") ? "cellpadding=\"1\" cellspacing=\"1\" border=\"1\" width=\"100%\"" : "") + ">");
        for (AbstractDestination sd : destinations) {
            this.writerSoapEndpoint(writer, basePath, sd);
        }
        writer.write("</table><br/><br/>");
    }

    private void writerSoapEndpoint(PrintWriter writer, String basePath, AbstractDestination sd) {
        String absoluteURL = this.getAbsoluteAddress(basePath, sd);
        if (absoluteURL == null) {
            return;
        }
        writer.write("<tr><td>");
        writer.write("<span class=\"porttypename\">" + sd.getEndpointInfo().getInterface().getName().getLocalPart() + "</span>");
        writer.write("<ul>");
        for (OperationInfo oi : sd.getEndpointInfo().getInterface().getOperations()) {
            if (Boolean.TRUE.equals(oi.getProperty("operation.is.synthetic"))) continue;
            writer.write("<li>" + oi.getName().getLocalPart() + "</li>");
        }
        writer.write("</ul>");
        writer.write("</td><td>");
        absoluteURL = StringEscapeUtils.escapeHtml4(absoluteURL);
        writer.write("<span class=\"field\">Endpoint address:</span> <span class=\"value\">" + absoluteURL + "</span>");
        writer.write("<br/><span class=\"field\">WSDL :</span> <a href=\"" + absoluteURL + "?wsdl\">" + sd.getEndpointInfo().getService().getName() + "</a>");
        writer.write("<br/><span class=\"field\">Target namespace:</span> <span class=\"value\">" + sd.getEndpointInfo().getService().getTargetNamespace() + "</span>");
        FormattedServiceListWriter.addAtomLinkIfNeeded(absoluteURL, this.atomMap, writer);
        writer.write("</td></tr>");
    }

    private String getAbsoluteAddress(String basePath, AbstractDestination d) {
        String endpointAddress = (String)d.getEndpointInfo().getProperty("publishedEndpointUrl");
        if (endpointAddress != null) {
            return endpointAddress;
        }
        endpointAddress = d.getEndpointInfo().getAddress();
        if (endpointAddress.startsWith("http://") || endpointAddress.startsWith("https://")) {
            if (endpointAddress.startsWith(basePath) || this.showForeignContexts) {
                return endpointAddress;
            }
            return null;
        }
        String address = basePath;
        if (address.endsWith("/") && endpointAddress.startsWith("/")) {
            address = address.substring(0, address.length() - 1);
        }
        return address + endpointAddress;
    }

    private void writeRESTfulEndpoints(PrintWriter writer, String basePath, AbstractDestination[] restfulDests) throws IOException {
        writer.write("<span class=\"heading\">Available RESTful services:</span><br/>");
        writer.write("<table " + (this.styleSheetPath.endsWith("stylesheet=1") ? "cellpadding=\"1\" cellspacing=\"1\" border=\"1\" width=\"100%\"" : "") + ">");
        for (AbstractDestination sd : restfulDests) {
            this.writeRESTfulEndpoint(writer, basePath, sd);
        }
        writer.write("</table>");
    }

    private void writeRESTfulEndpoint(PrintWriter writer, String basePath, AbstractDestination sd) {
        String absoluteURL = this.getAbsoluteAddress(basePath, sd);
        if (absoluteURL == null) {
            return;
        }
        absoluteURL = StringEscapeUtils.escapeHtml4(absoluteURL);
        writer.write("<tr><td>");
        writer.write("<span class=\"field\">Endpoint address:</span> <span class=\"value\">" + absoluteURL + "</span>");
        Bus sb = this.bus;
        if (sd instanceof AbstractHTTPDestination) {
            sb = ((AbstractHTTPDestination)sd).getBus();
        }
        FormattedServiceListWriter.addWadlIfNeeded(absoluteURL, sb, writer);
        FormattedServiceListWriter.addOpenApiIfNeeded(absoluteURL, sb, writer);
        FormattedServiceListWriter.addSwaggerIfNeeded(absoluteURL, sb, writer);
        FormattedServiceListWriter.addAtomLinkIfNeeded(absoluteURL, this.atomMap, writer);
        writer.write("</td></tr>");
    }

    private static void addWadlIfNeeded(String absoluteURL, Bus sb, PrintWriter writer) {
        if (sb != null && PropertyUtils.isTrue(sb.getProperty("wadl.service.description.available"))) {
            writer.write("<br/><span class=\"field\">WADL :</span> <a href=\"" + absoluteURL + "?_wadl\">" + absoluteURL + "?_wadl</a>");
        }
    }

    private static void addSwaggerIfNeeded(String absoluteURL, Bus sb, PrintWriter writer) {
        if (sb != null && PropertyUtils.isTrue(sb.getProperty("swagger.service.description.available"))) {
            FormattedServiceListWriter.writeApiSpec(absoluteURL, sb, writer, "swagger.json", "Swagger");
        }
    }

    private static void addOpenApiIfNeeded(String absoluteURL, Bus sb, PrintWriter writer) {
        if (sb != null && PropertyUtils.isTrue(sb.getProperty("openapi.service.description.available"))) {
            FormattedServiceListWriter.writeApiSpec(absoluteURL, sb, writer, "openapi.json", "OpenAPI");
        }
    }

    private static void writeApiSpec(String absoluteURL, Bus sb, PrintWriter writer, String specPath, String specName) {
        if (PropertyUtils.isTrue(sb.getProperty("swagger.service.ui.available"))) {
            URI uri = URI.create(absoluteURL);
            String schemePath = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() == -1 ? "" : ":" + uri.getPort());
            String relPath = absoluteURL.substring(schemePath.length());
            if (!relPath.endsWith("/")) {
                relPath = relPath + "/";
            }
            specPath = "api-docs?url=" + relPath + specPath;
        }
        if (!absoluteURL.endsWith("/")) {
            specPath = "/" + specPath;
        }
        specPath = StringEscapeUtils.escapeHtml4(specPath);
        writer.write("<br/><span class=\"field\">" + specName + " :</span> <a href=\"" + absoluteURL + specPath + "\">" + absoluteURL + specPath + "</a>");
    }

    private static void addAtomLinkIfNeeded(String address, Map<String, String> extMap, PrintWriter pw) {
        String atomAddress = FormattedServiceListWriter.getExtensionEndpointAddress(address, extMap);
        if (atomAddress != null) {
            atomAddress = StringEscapeUtils.escapeHtml4(atomAddress);
            pw.write("<br/><span class=\"field\">Atom Log Feed :</span> <a href=\"" + atomAddress + "\">" + atomAddress + "</a>");
        }
    }

    private static String getExtensionEndpointAddress(String endpointAddress, Map<String, String> extMap) {
        if (extMap != null) {
            for (Map.Entry<String, String> entry : extMap.entrySet()) {
                if (!endpointAddress.endsWith(entry.getKey())) continue;
                endpointAddress = endpointAddress.substring(0, endpointAddress.length() - entry.getKey().length());
                endpointAddress = endpointAddress + entry.getValue();
                return endpointAddress;
            }
        }
        return null;
    }
}

