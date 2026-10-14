/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver;

import java.util.Hashtable;
import java.util.Vector;
import org.apache.xml.resolver.CatalogException;

public class CatalogEntry {
    protected static int nextEntry = 0;
    protected static Hashtable entryTypes = new Hashtable();
    protected static Vector entryArgs = new Vector();
    protected int entryType = 0;
    protected Vector args = null;

    public static int addEntryType(String name, int numArgs) {
        entryTypes.put(name, new Integer(nextEntry));
        entryArgs.add(nextEntry, new Integer(numArgs));
        return ++nextEntry - 1;
    }

    public static int getEntryType(String name) throws CatalogException {
        if (!entryTypes.containsKey(name)) {
            throw new CatalogException(3);
        }
        Integer iType = (Integer)entryTypes.get(name);
        if (iType == null) {
            throw new CatalogException(3);
        }
        return iType;
    }

    public static int getEntryArgCount(String name) throws CatalogException {
        return CatalogEntry.getEntryArgCount(CatalogEntry.getEntryType(name));
    }

    public static int getEntryArgCount(int type) throws CatalogException {
        try {
            Integer iArgs = (Integer)entryArgs.get(type);
            return iArgs;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new CatalogException(3);
        }
    }

    public CatalogEntry() {
    }

    public CatalogEntry(String name, Vector args2) throws CatalogException {
        Integer iType = (Integer)entryTypes.get(name);
        if (iType == null) {
            throw new CatalogException(3);
        }
        int type = iType;
        try {
            Integer iArgs = (Integer)entryArgs.get(type);
            if (iArgs.intValue() != args2.size()) {
                throw new CatalogException(2);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new CatalogException(3);
        }
        this.entryType = type;
        this.args = args2;
    }

    public CatalogEntry(int type, Vector args2) throws CatalogException {
        try {
            Integer iArgs = (Integer)entryArgs.get(type);
            if (iArgs.intValue() != args2.size()) {
                throw new CatalogException(2);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new CatalogException(3);
        }
        this.entryType = type;
        this.args = args2;
    }

    public int getEntryType() {
        return this.entryType;
    }

    public String getEntryArg(int argNum) {
        try {
            String arg = (String)this.args.get(argNum);
            return arg;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setEntryArg(int argNum, String newspec) throws ArrayIndexOutOfBoundsException {
        this.args.set(argNum, newspec);
    }
}

