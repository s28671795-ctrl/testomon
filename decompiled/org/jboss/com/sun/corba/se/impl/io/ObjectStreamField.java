/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.sun.corba.Bridge;

public class ObjectStreamField
implements Comparable<ObjectStreamField> {
    private static final Bridge bridge = AccessController.doPrivileged(new PrivilegedAction<Bridge>(){

        @Override
        public Bridge run() {
            return Bridge.get();
        }
    });
    private String name;
    private char type;
    private Field field;
    private String typeString;
    private Class<?> clazz;
    private String signature;
    private long fieldID = -1L;

    ObjectStreamField(String n, Class<?> clazz) {
        this.name = n;
        this.clazz = clazz;
        if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                this.type = (char)73;
            } else if (clazz == Byte.TYPE) {
                this.type = (char)66;
            } else if (clazz == Long.TYPE) {
                this.type = (char)74;
            } else if (clazz == Float.TYPE) {
                this.type = (char)70;
            } else if (clazz == Double.TYPE) {
                this.type = (char)68;
            } else if (clazz == Short.TYPE) {
                this.type = (char)83;
            } else if (clazz == Character.TYPE) {
                this.type = (char)67;
            } else if (clazz == Boolean.TYPE) {
                this.type = (char)90;
            }
        } else if (clazz.isArray()) {
            this.type = (char)91;
            this.typeString = ObjectStreamClass.getSignature(clazz);
        } else {
            this.type = (char)76;
            this.typeString = ObjectStreamClass.getSignature(clazz);
        }
        this.signature = this.typeString != null ? this.typeString : String.valueOf(this.type);
    }

    ObjectStreamField(Field field2) {
        this(field2.getName(), field2.getType());
        this.setField(field2);
    }

    ObjectStreamField(String n, char t2, Field f, String ts) {
        this.name = n;
        this.type = t2;
        this.setField(f);
        this.typeString = ts;
        this.signature = this.typeString != null ? this.typeString : String.valueOf(this.type);
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getType() {
        if (this.clazz != null) {
            return this.clazz;
        }
        switch (this.type) {
            case 'B': {
                this.clazz = Byte.TYPE;
                break;
            }
            case 'C': {
                this.clazz = Character.TYPE;
                break;
            }
            case 'S': {
                this.clazz = Short.TYPE;
                break;
            }
            case 'I': {
                this.clazz = Integer.TYPE;
                break;
            }
            case 'J': {
                this.clazz = Long.TYPE;
                break;
            }
            case 'F': {
                this.clazz = Float.TYPE;
                break;
            }
            case 'D': {
                this.clazz = Double.TYPE;
                break;
            }
            case 'Z': {
                this.clazz = Boolean.TYPE;
                break;
            }
            case 'L': 
            case '[': {
                this.clazz = Object.class;
            }
        }
        return this.clazz;
    }

    public char getTypeCode() {
        return this.type;
    }

    public String getTypeString() {
        return this.typeString;
    }

    Field getField() {
        return this.field;
    }

    void setField(Field field2) {
        this.field = field2;
        this.fieldID = bridge.objectFieldOffset(field2);
    }

    ObjectStreamField() {
    }

    public boolean isPrimitive() {
        return this.type != '[' && this.type != 'L';
    }

    @Override
    public int compareTo(ObjectStreamField o) {
        boolean otherprim;
        boolean thisprim = this.typeString == null;
        boolean bl = otherprim = o.typeString == null;
        if (thisprim != otherprim) {
            return thisprim ? -1 : 1;
        }
        return this.name.compareTo(o.name);
    }

    public boolean typeEquals(ObjectStreamField other) {
        if (other == null || this.type != other.type) {
            return false;
        }
        if (this.typeString == null && other.typeString == null) {
            return true;
        }
        return ObjectStreamClass.compareClassNames(this.typeString, other.typeString, '/');
    }

    public String getSignature() {
        return this.signature;
    }

    public String toString() {
        if (this.typeString != null) {
            return this.typeString + " " + this.name;
        }
        return this.type + " " + this.name;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public long getFieldID() {
        return this.fieldID;
    }
}

