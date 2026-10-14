/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLNameTranslatorImpl;

public class IDLType {
    private Class<?> cl_;
    private String[] modules_;
    private String memberName_;

    public IDLType(Class<?> cl, String[] modules, String memberName) {
        this.cl_ = cl;
        this.modules_ = modules;
        this.memberName_ = memberName;
    }

    public IDLType(Class<?> cl, String memberName) {
        this(cl, new String[0], memberName);
    }

    public Class<?> getJavaClass() {
        return this.cl_;
    }

    public String[] getModules() {
        return this.modules_;
    }

    public String makeConcatenatedName(char separator, boolean fixIDLKeywords) {
        StringBuffer sbuff = new StringBuffer();
        for (int ctr = 0; ctr < this.modules_.length; ++ctr) {
            String mod = this.modules_[ctr];
            if (ctr > 0) {
                sbuff.append(separator);
            }
            if (fixIDLKeywords && IDLNameTranslatorImpl.isIDLKeyword(mod)) {
                mod = IDLNameTranslatorImpl.mangleIDLKeywordClash(mod);
            }
            sbuff.append(mod);
        }
        return sbuff.toString();
    }

    public String getModuleName() {
        return this.makeConcatenatedName('_', false);
    }

    public String getExceptionName() {
        String modName = this.makeConcatenatedName('/', true);
        String excName = this.memberName_;
        String suffix = "Exception";
        if (excName.endsWith(suffix)) {
            int last = excName.length() - suffix.length();
            excName = excName.substring(0, last);
        }
        excName = excName + "Ex";
        if (modName.length() == 0) {
            return "IDL:" + excName + ":1.0";
        }
        return "IDL:" + modName + '/' + excName + ":1.0";
    }

    public String getMemberName() {
        return this.memberName_;
    }

    public boolean hasModule() {
        return this.modules_.length > 0;
    }
}

