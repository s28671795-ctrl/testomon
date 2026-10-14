/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.LdapException;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.User;
import org.ldaptive.dn.AttributeValueEscaper;
import org.ldaptive.dn.DefaultAttributeValueEscaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatDnResolver
implements DnResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttributeValueEscaper attributeValueEscaper = new DefaultAttributeValueEscaper();
    private String formatString;
    private Object[] formatArgs;
    private boolean escapeUser = true;

    public FormatDnResolver() {
    }

    public FormatDnResolver(String format) {
        this.setFormat(format);
    }

    public FormatDnResolver(String format, Object[] args2) {
        this.setFormat(format);
        this.setFormatArgs(args2);
    }

    public String getFormat() {
        return this.formatString;
    }

    public void setFormat(String format) {
        this.formatString = format;
    }

    public Object[] getFormatArgs() {
        return this.formatArgs;
    }

    public void setFormatArgs(Object[] args2) {
        this.formatArgs = args2;
    }

    public boolean getEscapeUser() {
        return this.escapeUser;
    }

    public void setEscapeUser(boolean b) {
        this.escapeUser = b;
    }

    @Override
    public String resolve(User user) throws LdapException {
        if (this.formatString == null) {
            throw new IllegalStateException("Format string cannot be null");
        }
        String dn = null;
        if (user != null && user.getIdentifier() != null && !"".equals(user.getIdentifier())) {
            String escapedUser = this.escapeUser ? this.attributeValueEscaper.escape(user.getIdentifier()) : user.getIdentifier();
            this.logger.debug("Formatting DN for {} with {}", (Object)escapedUser, (Object)this.formatString);
            if (this.formatArgs != null && this.formatArgs.length > 0) {
                Object[] args2 = new Object[this.formatArgs.length + 1];
                args2[0] = escapedUser;
                System.arraycopy(this.formatArgs, 0, args2, 1, this.formatArgs.length);
                dn = String.format(this.formatString, args2);
            } else {
                dn = String.format(this.formatString, escapedUser);
            }
        } else {
            this.logger.debug("User input was empty or null");
        }
        return dn;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "formatString=" + this.formatString + ", " + "formatArgs=" + Arrays.toString(this.formatArgs) + ", " + "escapeUser=" + this.escapeUser + "]";
    }
}

