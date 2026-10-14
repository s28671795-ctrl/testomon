/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface SpaceBearing {
    public static final String XML_SPACE_ATTR_LOCAL_NAME = "space";
    public static final QName XML_SPACE_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "space", "xml");

    @Nullable
    public XMLSpaceEnum getXMLSpace();

    public void setXMLSpace(@Nullable XMLSpaceEnum var1);

    public static enum XMLSpaceEnum {
        DEFAULT,
        PRESERVE;


        public String toString() {
            return super.toString().toLowerCase();
        }

        public static XMLSpaceEnum parseValue(String value) {
            return XMLSpaceEnum.valueOf(value.toUpperCase());
        }
    }
}

