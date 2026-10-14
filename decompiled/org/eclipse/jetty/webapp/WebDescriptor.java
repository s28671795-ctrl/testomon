/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Descriptor;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.xml.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class WebDescriptor
extends Descriptor {
    private static final Logger LOG = LoggerFactory.getLogger(WebDescriptor.class);
    public static XmlParser __nonValidatingStaticParser = WebDescriptor.newParser(false);
    protected MetaData.Complete _metaDataComplete;
    protected int _majorVersion = 4;
    protected int _minorVersion = 0;
    protected ArrayList<String> _classNames = new ArrayList();
    protected boolean _distributable;
    protected boolean _isOrdered = false;
    protected List<String> _ordering = new ArrayList<String>();

    public static boolean isMetaDataComplete(WebDescriptor d) {
        return d != null && d.getMetaDataComplete() == MetaData.Complete.True;
    }

    public static XmlParser getParser(boolean validating) {
        if (!validating) {
            return __nonValidatingStaticParser;
        }
        return WebDescriptor.newParser(true);
    }

    public static XmlParser newParser(boolean validating) {
        XmlParser xmlParser = new XmlParser(validating){
            boolean mapped = false;

            @Override
            protected InputSource resolveEntity(String pid, String sid) {
                if (!this.mapped) {
                    this.mapResources();
                    this.mapped = true;
                }
                InputSource is = super.resolveEntity(pid, sid);
                return is;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            void mapResources() {
                URL dtd22 = Loader.getResource("jakarta/servlet/resources/web-app_2_2.dtd");
                URL dtd23 = Loader.getResource("jakarta/servlet/resources/web-app_2_3.dtd");
                URL j2ee14xsd = Loader.getResource("jakarta/servlet/resources/j2ee_1_4.xsd");
                URL javaee5 = Loader.getResource("jakarta/servlet/resources/javaee_5.xsd");
                URL javaee6 = Loader.getResource("jakarta/servlet/resources/javaee_6.xsd");
                URL javaee7 = Loader.getResource("jakarta/servlet/resources/javaee_7.xsd");
                URL javaee8 = Loader.getResource("jakarta/servlet/resources/javaee_8.xsd");
                URL jakartaee9 = Loader.getResource("jakarta/servlet/resources/jakartaee_9.xsd");
                URL webapp24xsd = Loader.getResource("jakarta/servlet/resources/web-app_2_4.xsd");
                URL webapp25xsd = Loader.getResource("jakarta/servlet/resources/web-app_2_5.xsd");
                URL webapp30xsd = Loader.getResource("jakarta/servlet/resources/web-app_3_0.xsd");
                URL webapp31xsd = Loader.getResource("jakarta/servlet/resources/web-app_3_1.xsd");
                URL webapp40xsd = Loader.getResource("jakarta/servlet/resources/web-app_4_0.xsd");
                URL webapp50xsd = Loader.getResource("jakarta/servlet/resources/web-app_5_0.xsd");
                URL webcommon30xsd = Loader.getResource("jakarta/servlet/resources/web-common_3_0.xsd");
                URL webcommon31xsd = Loader.getResource("jakarta/servlet/resources/web-common_3_1.xsd");
                URL webcommon40xsd = Loader.getResource("jakarta/servlet/resources/web-common_4_0.xsd");
                URL webcommon50xsd = Loader.getResource("jakarta/servlet/resources/web-common_5_0.xsd");
                URL webfragment30xsd = Loader.getResource("jakarta/servlet/resources/web-fragment_3_0.xsd");
                URL webfragment31xsd = Loader.getResource("jakarta/servlet/resources/web-fragment_3_1.xsd");
                URL webfragment40xsd = Loader.getResource("jakarta/servlet/resources/web-fragment_4_0.xsd");
                URL webfragment50xsd = Loader.getResource("jakarta/servlet/resources/web-fragment_5_0.xsd");
                URL schemadtd = Loader.getResource("jakarta/servlet/resources/XMLSchema.dtd");
                URL xmlxsd = Loader.getResource("jakarta/servlet/resources/xml.xsd");
                URL webservice11xsd = Loader.getResource("jakarta/servlet/resources/j2ee_web_services_client_1_1.xsd");
                URL webservice12xsd = Loader.getResource("jakarta/servlet/resources/javaee_web_services_client_1_2.xsd");
                URL webservice13xsd = Loader.getResource("jakarta/servlet/resources/javaee_web_services_client_1_3.xsd");
                URL webservice14xsd = Loader.getResource("jakarta/servlet/resources/javaee_web_services_client_1_4.xsd");
                URL webservice20xsd = Loader.getResource("jakarta/servlet/resources/jakartaee_web_services_client_2_0.xsd");
                URL datatypesdtd = Loader.getResource("jakarta/servlet/resources/datatypes.dtd");
                URL jsp20xsd = null;
                URL jsp21xsd = null;
                URL jsp22xsd = null;
                URL jsp23xsd = null;
                URL jsp30xsd = null;
                try {
                    jsp20xsd = Loader.getResource("jakarta/servlet/resources/jsp_2_0.xsd");
                    jsp21xsd = Loader.getResource("jakarta/servlet/resources/jsp_2_1.xsd");
                    jsp22xsd = Loader.getResource("jakarta/servlet/resources/jsp_2_2.xsd");
                    jsp23xsd = Loader.getResource("jakarta/servlet/resources/jsp_2_3.xsd");
                    jsp30xsd = Loader.getResource("jakarta/servlet/resources/jsp_3_0.xsd");
                }
                catch (Exception e) {
                    LOG.trace("IGNORED", e);
                }
                finally {
                    if (jsp20xsd == null) {
                        jsp20xsd = Loader.getResource("jakarta/servlet/jsp/resources/jsp_2_0.xsd");
                    }
                    if (jsp21xsd == null) {
                        jsp21xsd = Loader.getResource("jakarta/servlet/jsp/resources/jsp_2_1.xsd");
                    }
                    if (jsp22xsd == null) {
                        jsp22xsd = Loader.getResource("jakarta/servlet/jsp/resources/jsp_2_2.xsd");
                    }
                    if (jsp23xsd == null) {
                        jsp23xsd = Loader.getResource("jakarta/servlet/jsp/resources/jsp_2_3.xsd");
                    }
                    if (jsp30xsd == null) {
                        jsp30xsd = Loader.getResource("jakarta/servlet/jsp/resources/jsp_3_0.xsd");
                    }
                }
                this.redirectEntity("web-app_2_2.dtd", dtd22);
                this.redirectEntity("-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", dtd22);
                this.redirectEntity("web.dtd", dtd23);
                this.redirectEntity("web-app_2_3.dtd", dtd23);
                this.redirectEntity("-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", dtd23);
                this.redirectEntity("XMLSchema.dtd", schemadtd);
                this.redirectEntity("http://www.w3.org/2001/XMLSchema.dtd", schemadtd);
                this.redirectEntity("-//W3C//DTD XMLSCHEMA 200102//EN", schemadtd);
                this.redirectEntity("jsp_2_0.xsd", jsp20xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/j2ee/jsp_2_0.xsd", jsp20xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/jsp_2_1.xsd", jsp21xsd);
                this.redirectEntity("jsp_2_2.xsd", jsp22xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/jsp_2_2.xsd", jsp22xsd);
                this.redirectEntity("jsp_2_3.xsd", jsp23xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/jsp_2_3.xsd", jsp23xsd);
                this.redirectEntity("jsp_3_0.xsd", jsp30xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/jsp_3_0.xsd", jsp30xsd);
                this.redirectEntity("j2ee_1_4.xsd", j2ee14xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/j2ee/j2ee_1_4.xsd", j2ee14xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/javaee_5.xsd", javaee5);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/javaee_6.xsd", javaee6);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/javaee_7.xsd", javaee7);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/javaee_8.xsd", javaee8);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/javaee_9.xsd", jakartaee9);
                this.redirectEntity("web-common_3_0.xsd", webcommon30xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/web-common_3_0.xsd", webcommon30xsd);
                this.redirectEntity("web-common_3_1.xsd", webcommon31xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-common_3_1.xsd", webcommon31xsd);
                this.redirectEntity("web-common_4_0.xsd", webcommon40xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-common_4_0.xsd", webcommon40xsd);
                this.redirectEntity("web-common_5_0.xsd", webcommon50xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/web-common_5_0.xsd", webcommon50xsd);
                this.redirectEntity("web-app_2_4.xsd", webapp24xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd", webapp24xsd);
                this.redirectEntity("web-app_2_5.xsd", webapp25xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd", webapp25xsd);
                this.redirectEntity("web-app_3_0.xsd", webapp30xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd", webapp30xsd);
                this.redirectEntity("web-app_3_1.xsd", webapp31xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd", webapp31xsd);
                this.redirectEntity("web-app_4_0.xsd", webapp40xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd", webapp40xsd);
                this.redirectEntity("web-app_5_0.xsd", webapp50xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd", webapp50xsd);
                this.redirectEntity("webapp_4_0.xsd", webapp40xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/webapp_4_0.xsd", webapp40xsd);
                this.redirectEntity("webapp_5_0.xsd", webapp50xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/webapp_5_0.xsd", webapp50xsd);
                this.redirectEntity("http://xmlns.eclipse.org/xml/ns/jakartaee/web-app_4_0.xsd", webapp40xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd", webapp50xsd);
                this.redirectEntity("web-fragment_3_0.xsd", webfragment30xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/web-fragment_3_0.xsd", webfragment30xsd);
                this.redirectEntity("web-fragment_3_1.xsd", webfragment31xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-fragment_3_1.xsd", webfragment31xsd);
                this.redirectEntity("web-fragment_4_0.xsd", webfragment40xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/web-fragment_4_0.xsd", webfragment40xsd);
                this.redirectEntity("web-fragment_5_0.xsd", webfragment50xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/web-fragment_5_0.xsd", webfragment50xsd);
                this.redirectEntity("xml.xsd", xmlxsd);
                this.redirectEntity("http://www.w3.org/2001/xml.xsd", xmlxsd);
                this.redirectEntity("datatypes.dtd", datatypesdtd);
                this.redirectEntity("http://www.w3.org/2001/datatypes.dtd", datatypesdtd);
                this.redirectEntity("j2ee_web_services_client_1_1.xsd", webservice11xsd);
                this.redirectEntity("http://www.ibm.com/webservices/xsd/j2ee_web_services_client_1_1.xsd", webservice11xsd);
                this.redirectEntity("javaee_web_services_client_1_2.xsd", webservice12xsd);
                this.redirectEntity("http://www.ibm.com/webservices/xsd/javaee_web_services_client_1_2.xsd", webservice12xsd);
                this.redirectEntity("javaee_web_services_client_1_3.xsd", webservice13xsd);
                this.redirectEntity("http://java.sun.com/xml/ns/javaee/javaee_web_services_client_1_3.xsd", webservice13xsd);
                this.redirectEntity("javaee_web_services_client_1_4.xsd", webservice14xsd);
                this.redirectEntity("http://xmlns.jcp.org/xml/ns/javaee/javaee_web_services_client_1_4.xsd", webservice14xsd);
                this.redirectEntity("jakartaee_web_services_client_2_0.xsd", webservice20xsd);
                this.redirectEntity("https://jakarta.ee/xml/ns/jakartaee/jakartaee_web_services_client_2_0.xsd", webservice20xsd);
            }
        };
        return xmlParser;
    }

    public WebDescriptor(Resource xml) {
        super(xml);
    }

    @Override
    public void parse(XmlParser parser) throws Exception {
        super.parse(parser);
        this.processVersion();
        this.processOrdering();
        this.processDistributable();
    }

    public MetaData.Complete getMetaDataComplete() {
        return this._metaDataComplete;
    }

    public int getMajorVersion() {
        return this._majorVersion;
    }

    public int getMinorVersion() {
        return this._minorVersion;
    }

    public void processVersion() {
        String version = this._root.getAttribute("version", "DTD");
        if ("DTD".equals(version)) {
            this._majorVersion = 2;
            this._minorVersion = 3;
            if (this._dtd != null && this._dtd.indexOf("web-app_2_2") >= 0) {
                this._majorVersion = 2;
                this._minorVersion = 2;
            }
        } else {
            int dot = version.indexOf(".");
            if (dot > 0) {
                this._majorVersion = Integer.parseInt(version.substring(0, dot));
                this._minorVersion = Integer.parseInt(version.substring(dot + 1));
            }
        }
        if (this._majorVersion <= 2 && this._minorVersion < 5) {
            this._metaDataComplete = MetaData.Complete.True;
        } else {
            String s2 = this._root.getAttribute("metadata-complete");
            if (s2 == null) {
                this._metaDataComplete = MetaData.Complete.NotSet;
            } else {
                MetaData.Complete complete = this._metaDataComplete = Boolean.valueOf(s2) != false ? MetaData.Complete.True : MetaData.Complete.False;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{}: Calculated metadatacomplete = {} with version = {}", new Object[]{this._xml.toString(), this._metaDataComplete, version});
        }
    }

    public void processOrdering() {
        XmlParser.Node ordering = this._root.get("absolute-ordering");
        if (ordering == null) {
            return;
        }
        this._isOrdered = true;
        Iterator iter = ordering.iterator();
        XmlParser.Node node = null;
        while (iter.hasNext()) {
            Object o = iter.next();
            if (!(o instanceof XmlParser.Node)) continue;
            node = (XmlParser.Node)o;
            if (node.getTag().equalsIgnoreCase("others")) {
                this._ordering.add("others");
                continue;
            }
            if (!node.getTag().equalsIgnoreCase("name")) continue;
            this._ordering.add(node.toString(false, true));
        }
    }

    public void processDistributable() {
        XmlParser.Node distributable = this._root.get("distributable");
        if (distributable == null) {
            return;
        }
        this._distributable = true;
    }

    public void addClassName(String className) {
        if (!this._classNames.contains(className)) {
            this._classNames.add(className);
        }
    }

    public ArrayList<String> getClassNames() {
        return this._classNames;
    }

    public boolean isDistributable() {
        return this._distributable;
    }

    public boolean isOrdered() {
        return this._isOrdered;
    }

    public List<String> getOrdering() {
        return this._ordering;
    }
}

