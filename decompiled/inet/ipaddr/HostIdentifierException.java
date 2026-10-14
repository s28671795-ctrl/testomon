/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class HostIdentifierException
extends Exception {
    private static final long serialVersionUID = 4L;
    static ResourceBundle bundle;

    public HostIdentifierException(CharSequence str, String errorMessage, String key, Throwable cause) {
        super(str.toString() + ' ' + errorMessage + ' ' + HostIdentifierException.getMessage(key), cause);
    }

    public HostIdentifierException(CharSequence str, String errorMessage, String key) {
        super(str.toString() + ' ' + errorMessage + ' ' + HostIdentifierException.getMessage(key));
    }

    public HostIdentifierException(CharSequence message) {
        super(message.toString());
    }

    public HostIdentifierException(CharSequence message, Throwable cause) {
        super(message.toString(), cause);
    }

    public HostIdentifierException(CharSequence errorMessage, String key) {
        super(errorMessage.toString() + ' ' + HostIdentifierException.getMessage(key));
    }

    static String getMessage(String key) {
        if (bundle != null) {
            try {
                return bundle.getString(key);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return key;
    }

    static {
        String propertyFileName = "IPAddressResources";
        String name = HostIdentifierException.class.getPackage().getName() + '.' + propertyFileName;
        try {
            bundle = ResourceBundle.getBundle(name);
        }
        catch (MissingResourceException e) {
            System.err.println("bundle " + name + " is missing");
        }
    }
}

