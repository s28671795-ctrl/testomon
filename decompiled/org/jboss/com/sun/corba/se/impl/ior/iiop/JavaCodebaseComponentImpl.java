/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;
import org.omg.CORBA_2_3.portable.OutputStream;

public class JavaCodebaseComponentImpl
extends TaggedComponentBase
implements JavaCodebaseComponent {
    private String URLs;

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof JavaCodebaseComponentImpl)) {
            return false;
        }
        JavaCodebaseComponentImpl other = (JavaCodebaseComponentImpl)obj;
        return this.URLs.equals(other.getURLs());
    }

    public int hashCode() {
        return this.URLs.hashCode();
    }

    public String toString() {
        return "JavaCodebaseComponentImpl[URLs=" + this.URLs + "]";
    }

    @Override
    public String getURLs() {
        return this.URLs;
    }

    public JavaCodebaseComponentImpl(String URLs) {
        this.URLs = URLs;
    }

    @Override
    public void writeContents(OutputStream os) {
        os.write_string(this.URLs);
    }

    @Override
    public int getId() {
        return 25;
    }
}

