/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.apps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.helpers.Debug;
import org.apache.xml.resolver.helpers.FileURL;
import org.apache.xml.resolver.tools.CatalogResolver;

public class resolver {
    private static Debug debug = CatalogManager.getStaticManager().debug;

    public static void main(String[] args2) throws FileNotFoundException, IOException {
        int debuglevel = 0;
        Vector<String> catalogFiles = new Vector<String>();
        int resType = 0;
        String resTypeStr = null;
        String name = null;
        String publicId = null;
        String systemId = null;
        String uri = null;
        boolean absoluteSystem = false;
        int i = 0;
        while (i < args2.length) {
            if (args2[i].equals("-c")) {
                catalogFiles.add(args2[++i]);
            } else if (args2[i].equals("-p")) {
                publicId = args2[++i];
            } else if (args2[i].equals("-s")) {
                systemId = args2[++i];
            } else if (args2[i].equals("-n")) {
                name = args2[++i];
            } else if (args2[i].equals("-u")) {
                uri = args2[++i];
            } else if (args2[i].equals("-a")) {
                absoluteSystem = true;
            } else if (args2[i].equals("-d")) {
                String debugstr = args2[++i];
                try {
                    debuglevel = Integer.parseInt(debugstr);
                    if (debuglevel > 0) {
                        debug.setDebug(debuglevel);
                    }
                }
                catch (Exception e) {}
            } else {
                resTypeStr = args2[i];
            }
            ++i;
        }
        if (resTypeStr == null) {
            resolver.usage();
        }
        if (resTypeStr.equalsIgnoreCase("doctype")) {
            resType = Catalog.DOCTYPE;
            if (publicId == null && systemId == null) {
                System.out.println("DOCTYPE requires public or system identifier.");
                resolver.usage();
            }
        } else if (resTypeStr.equalsIgnoreCase("document")) {
            resType = Catalog.DOCUMENT;
        } else if (resTypeStr.equalsIgnoreCase("entity")) {
            resType = Catalog.ENTITY;
            if (publicId == null && systemId == null && name == null) {
                System.out.println("ENTITY requires name or public or system identifier.");
                resolver.usage();
            }
        } else if (resTypeStr.equalsIgnoreCase("notation")) {
            resType = Catalog.NOTATION;
            if (publicId == null && systemId == null && name == null) {
                System.out.println("NOTATION requires name or public or system identifier.");
                resolver.usage();
            }
        } else if (resTypeStr.equalsIgnoreCase("public")) {
            resType = Catalog.PUBLIC;
            if (publicId == null) {
                System.out.println("PUBLIC requires public identifier.");
                resolver.usage();
            }
        } else if (resTypeStr.equalsIgnoreCase("system")) {
            resType = Catalog.SYSTEM;
            if (systemId == null) {
                System.out.println("SYSTEM requires system identifier.");
                resolver.usage();
            }
        } else if (resTypeStr.equalsIgnoreCase("uri")) {
            resType = Catalog.URI;
            if (uri == null) {
                System.out.println("URI requires a uri.");
                resolver.usage();
            }
        } else {
            System.out.println(resTypeStr + " is not a recognized keyword.");
            resolver.usage();
        }
        if (absoluteSystem) {
            URL base = null;
            URL sysid = null;
            try {
                base = FileURL.makeURL("basename");
            }
            catch (MalformedURLException e) {
                String userdir = System.getProperty("user.dir");
                userdir = userdir.replace('\\', '/');
                debug.message(1, "Malformed URL on cwd", userdir);
                base = null;
            }
            try {
                sysid = new URL(base, systemId);
                systemId = sysid.toString();
            }
            catch (MalformedURLException e) {
                try {
                    sysid = new URL("file:///" + systemId);
                }
                catch (MalformedURLException e2) {
                    debug.message(1, "Malformed URL on system id", systemId);
                }
            }
        }
        CatalogResolver catalogResolver = new CatalogResolver();
        Catalog resolver2 = catalogResolver.getCatalog();
        int count = 0;
        while (count < catalogFiles.size()) {
            String file = (String)catalogFiles.elementAt(count);
            resolver2.parseCatalog(file);
            ++count;
        }
        String result = null;
        if (resType == Catalog.DOCTYPE) {
            System.out.println("Resolve DOCTYPE (name, publicid, systemid):");
            if (name != null) {
                System.out.println("       name: " + name);
            }
            if (publicId != null) {
                System.out.println("  public id: " + publicId);
            }
            if (systemId != null) {
                System.out.println("  system id: " + systemId);
            }
            if (uri != null) {
                System.out.println("        uri: " + uri);
            }
            result = resolver2.resolveDoctype(name, publicId, systemId);
        } else if (resType == Catalog.DOCUMENT) {
            System.out.println("Resolve DOCUMENT ():");
            result = resolver2.resolveDocument();
        } else if (resType == Catalog.ENTITY) {
            System.out.println("Resolve ENTITY (name, publicid, systemid):");
            if (name != null) {
                System.out.println("       name: " + name);
            }
            if (publicId != null) {
                System.out.println("  public id: " + publicId);
            }
            if (systemId != null) {
                System.out.println("  system id: " + systemId);
            }
            result = resolver2.resolveEntity(name, publicId, systemId);
        } else if (resType == Catalog.NOTATION) {
            System.out.println("Resolve NOTATION (name, publicid, systemid):");
            if (name != null) {
                System.out.println("       name: " + name);
            }
            if (publicId != null) {
                System.out.println("  public id: " + publicId);
            }
            if (systemId != null) {
                System.out.println("  system id: " + systemId);
            }
            result = resolver2.resolveNotation(name, publicId, systemId);
        } else if (resType == Catalog.PUBLIC) {
            System.out.println("Resolve PUBLIC (publicid, systemid):");
            if (publicId != null) {
                System.out.println("  public id: " + publicId);
            }
            if (systemId != null) {
                System.out.println("  system id: " + systemId);
            }
            result = resolver2.resolvePublic(publicId, systemId);
        } else if (resType == Catalog.SYSTEM) {
            System.out.println("Resolve SYSTEM (systemid):");
            if (systemId != null) {
                System.out.println("  system id: " + systemId);
            }
            result = resolver2.resolveSystem(systemId);
        } else if (resType == Catalog.URI) {
            System.out.println("Resolve URI (uri):");
            if (uri != null) {
                System.out.println("        uri: " + uri);
            }
            result = resolver2.resolveURI(uri);
        } else {
            System.out.println("resType is wrong!? This can't happen!");
            resolver.usage();
        }
        System.out.println("Result: " + result);
    }

    public static void usage() {
        System.out.println("Usage: resolver [options] keyword");
        System.out.println("");
        System.out.println("Where:");
        System.out.println("");
        System.out.println("-c catalogfile  Loads a particular catalog file.");
        System.out.println("-n name         Sets the name.");
        System.out.println("-p publicId     Sets the public identifier.");
        System.out.println("-s systemId     Sets the system identifier.");
        System.out.println("-a              Makes the system URI absolute before resolution");
        System.out.println("-u uri          Sets the URI.");
        System.out.println("-d integer      Set the debug level.");
        System.out.println("keyword         Identifies the type of resolution to perform:");
        System.out.println("                doctype, document, entity, notation, public, system,");
        System.out.println("                or uri.");
        System.exit(1);
    }
}

