/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.HashMap;
import java.util.Map;
import org.ldaptive.asn1.DERTag;

public enum UniversalDERTag implements DERTag
{
    BOOL(1, false),
    INT(2, false),
    BITSTR(3, false),
    OCTSTR(4, false),
    NULL(5, false),
    OID(6, false),
    ODESC(7, false),
    EXT(8, false),
    REAL(9, false),
    ENUM(10, false),
    EMBPDV(11, false),
    UTF8STR(12, false),
    ROID(13, false),
    SEQ(16, true),
    SET(17, true),
    NUMSTR(18, false),
    PRINTSTR(19, false),
    T61STR(20, false),
    VTEXSTR(21, false),
    IA5STR(22, false),
    UTCTIME(23, false),
    GENTIME(24, false),
    GRAPHICSTR(25, false),
    ISO646STR(26, false),
    GENSTR(27, false),
    UNISTR(28, false),
    CHARSTR(29, false),
    BMPSTR(30, false);

    public static final int TAG_CLASS = 0;
    private static final Map<Integer, UniversalDERTag> TAGNO_MAP;
    private static final Map<String, UniversalDERTag> TAGNAME_MAP;
    private final int tagNo;
    private final boolean constructed;

    private UniversalDERTag(int number, boolean isConstructed) {
        this.tagNo = number;
        this.constructed = isConstructed;
    }

    @Override
    public int getTagNo() {
        return this.tagNo;
    }

    @Override
    public boolean isConstructed() {
        return this.constructed;
    }

    @Override
    public int getTagByte() {
        return this.constructed ? this.tagNo | 0x20 : this.tagNo;
    }

    public static UniversalDERTag fromTagNo(int number) {
        UniversalDERTag derTag = TAGNO_MAP.get(number);
        if (derTag == null) {
            throw new IllegalArgumentException("Unknown tag number: " + number);
        }
        return derTag;
    }

    public static UniversalDERTag fromTagName(String name) {
        return TAGNAME_MAP.get(name);
    }

    static {
        TAGNO_MAP = new HashMap<Integer, UniversalDERTag>();
        TAGNAME_MAP = new HashMap<String, UniversalDERTag>();
        for (UniversalDERTag tag : UniversalDERTag.values()) {
            TAGNO_MAP.put(tag.getTagNo(), tag);
            TAGNAME_MAP.put(tag.name(), tag);
        }
    }
}

