/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.apps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.apps.XParseError;
import org.apache.xml.resolver.helpers.Debug;
import org.apache.xml.resolver.tools.ResolvingXMLReader;
import org.xml.sax.SAXException;

public class xread {
    private static Debug debug = CatalogManager.getStaticManager().debug;

    public static void main(String[] args2) throws FileNotFoundException, IOException {
        String nsType;
        String xmlfile = null;
        int debuglevel = 0;
        int maxErrs = 10;
        boolean nsAware = true;
        boolean validating = true;
        boolean useSchema = false;
        boolean showWarnings = debuglevel > 2;
        boolean showErrors = true;
        Vector<String> catalogFiles = new Vector<String>();
        int i = 0;
        while (i < args2.length) {
            if (args2[i].equals("-c")) {
                catalogFiles.add(args2[++i]);
            } else if (args2[i].equals("-w")) {
                validating = false;
            } else if (args2[i].equals("-v")) {
                validating = true;
            } else if (args2[i].equals("-s")) {
                useSchema = true;
            } else if (args2[i].equals("-n")) {
                nsAware = false;
            } else if (args2[i].equals("-N")) {
                nsAware = true;
            } else if (args2[i].equals("-d")) {
                String debugstr = args2[++i];
                try {
                    debuglevel = Integer.parseInt(debugstr);
                    if (debuglevel >= 0) {
                        debug.setDebug(debuglevel);
                        showWarnings = debuglevel > 2;
                    }
                }
                catch (Exception e) {}
            } else if (args2[i].equals("-E")) {
                String errstr = args2[++i];
                try {
                    int errs = Integer.parseInt(errstr);
                    if (errs >= 0) {
                        maxErrs = errs;
                    }
                }
                catch (Exception e) {}
            } else {
                xmlfile = args2[i];
            }
            ++i;
        }
        if (xmlfile == null) {
            System.out.println("Usage: org.apache.xml.resolver.apps.xread [opts] xmlfile");
            System.exit(1);
        }
        ResolvingXMLReader reader = new ResolvingXMLReader();
        try {
            reader.setFeature("http://xml.org/sax/features/namespaces", nsAware);
            reader.setFeature("http://xml.org/sax/features/validation", validating);
            if (useSchema) {
                reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            }
        }
        catch (SAXException e) {
            // empty catch block
        }
        Catalog catalog = reader.getCatalog();
        int count = 0;
        while (count < catalogFiles.size()) {
            String file = (String)catalogFiles.elementAt(count);
            catalog.parseCatalog(file);
            ++count;
        }
        XParseError xpe = new XParseError(showErrors, showWarnings);
        xpe.setMaxMessages(maxErrs);
        reader.setErrorHandler(xpe);
        String parseType = validating ? "validating" : "well-formed";
        String string = nsType = nsAware ? "namespace-aware" : "namespace-ignorant";
        if (maxErrs > 0) {
            System.out.println("Attempting " + parseType + ", " + nsType + " parse");
        }
        Date startTime = new Date();
        try {
            reader.parse(xmlfile);
        }
        catch (SAXException sx) {
            System.out.println("SAX Exception: " + sx);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Date endTime = new Date();
        long millisec = endTime.getTime() - startTime.getTime();
        long secs = 0L;
        long mins = 0L;
        long hours = 0L;
        if (millisec > 1000L) {
            secs = millisec / 1000L;
            millisec %= 1000L;
        }
        if (secs > 60L) {
            mins = secs / 60L;
            secs %= 60L;
        }
        if (mins > 60L) {
            hours = mins / 60L;
            mins %= 60L;
        }
        if (maxErrs > 0) {
            System.out.print("Parse ");
            if (xpe.getFatalCount() > 0) {
                System.out.print("failed ");
            } else {
                System.out.print("succeeded ");
                System.out.print("(");
                if (hours > 0L) {
                    System.out.print(hours + ":");
                }
                if (hours > 0L || mins > 0L) {
                    System.out.print(mins + ":");
                }
                System.out.print(secs + "." + millisec);
                System.out.print(") ");
            }
            System.out.print("with ");
            int errCount = xpe.getErrorCount();
            int warnCount = xpe.getWarningCount();
            if (errCount > 0) {
                System.out.print(errCount + " error");
                System.out.print(errCount > 1 ? "s" : "");
                System.out.print(" and ");
            } else {
                System.out.print("no errors and ");
            }
            if (warnCount > 0) {
                System.out.print(warnCount + " warning");
                System.out.print(warnCount > 1 ? "s" : "");
                System.out.print(".");
            } else {
                System.out.print("no warnings.");
            }
            System.out.println("");
        }
        if (xpe.getErrorCount() > 0) {
            System.exit(1);
        }
    }
}

