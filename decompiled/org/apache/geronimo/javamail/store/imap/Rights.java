/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Rights
implements Cloneable {
    private SortedSet rights = new TreeSet(new RightComparator());

    public Rights() {
    }

    public Rights(Right right) {
        this.rights.add(right);
    }

    public Rights(Rights list) {
        this.add(list);
        Rights[] otherRights = list.getRights();
        for (int i = 0; i < otherRights.length; ++i) {
            this.rights.add(otherRights[i]);
        }
    }

    public Rights(String list) {
        for (int i = 0; i < list.length(); ++i) {
            this.rights.add(Right.getInstance(list.charAt(i)));
        }
    }

    public void add(Right right) {
        this.rights.add(right);
    }

    public void add(Rights list) {
        Rights[] otherRights = list.getRights();
        for (int i = 0; i < otherRights.length; ++i) {
            this.rights.add(otherRights[i]);
        }
    }

    public Object clone() {
        return new Rights(this);
    }

    public boolean contains(Right right) {
        return this.rights.contains(right);
    }

    public boolean contains(Rights list) {
        return this.rights.containsAll(list.rights);
    }

    public boolean equals(Rights list) {
        return this.rights.equals(list.rights);
    }

    public Rights[] getRights() {
        Rights[] list = new Rights[this.rights.size()];
        return this.rights.toArray(list);
    }

    public int hashCode() {
        return this.rights.hashCode();
    }

    public void remove(Right right) {
        this.rights.remove(right);
    }

    public void remove(Rights list) {
        this.rights.removeAll(list.rights);
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        Iterator i = this.rights.iterator();
        while (i.hasNext()) {
            buff.append(i.next().toString());
        }
        return buff.toString();
    }

    class RightComparator
    implements Comparator {
        RightComparator() {
        }

        public int compare(Object o1, Object o2) {
            String left = o1.toString();
            return left.compareTo(o2.toString());
        }
    }

    public static final class Right {
        private static Map rights = new HashMap();
        public static final Right LOOKUP = Right.getInstance('l');
        public static final Right READ = Right.getInstance('r');
        public static final Right KEEP_SEEN = Right.getInstance('s');
        public static final Right WRITE = Right.getInstance('w');
        public static final Right INSERT = Right.getInstance('i');
        public static final Right POST = Right.getInstance('p');
        public static final Right CREATE = Right.getInstance('c');
        public static final Right DELETE = Right.getInstance('d');
        public static final Right ADMINISTER = Right.getInstance('a');
        String right;

        private Right(String right) {
            this.right = right;
        }

        public static synchronized Right getInstance(char right) {
            String name = String.valueOf(right);
            Right instance = (Right)rights.get(name);
            if (instance == null) {
                instance = new Right(name);
                rights.put(name, instance);
            }
            return instance;
        }

        public String toString() {
            return this.right;
        }
    }
}

