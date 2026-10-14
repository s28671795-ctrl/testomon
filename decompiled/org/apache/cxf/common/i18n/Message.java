/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.i18n;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Message
implements Serializable {
    private static final long serialVersionUID = 42L;
    transient String code;
    transient Object[] parameters;
    transient ResourceBundle bundle;

    public Message(String key, Logger logger2, Object ... params) {
        this(key, logger2.getResourceBundle(), params);
    }

    public Message(String key, ResourceBundle catalog, Object ... params) {
        this.code = key;
        this.bundle = catalog;
        this.parameters = params;
    }

    public String toString() {
        String fmt = null;
        try {
            if (null == this.bundle) {
                return this.code;
            }
            fmt = this.bundle.getString(this.code);
        }
        catch (MissingResourceException ex) {
            return this.code;
        }
        return MessageFormat.format(fmt, this.parameters);
    }

    public String getCode() {
        return this.code;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(this.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.code = in.readUTF();
        this.bundle = null;
        this.parameters = null;
    }
}

