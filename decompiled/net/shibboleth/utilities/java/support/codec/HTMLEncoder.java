/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.codec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class HTMLEncoder {
    @Nonnull
    public static final char[] IMMUNE_HTML = new char[]{',', '.', '-', '_', ' '};
    @Nonnull
    public static final char[] IMMUNE_HTMLATTR = new char[]{',', '.', '-', '_'};
    @Nonnull
    public static final char REPLACEMENT_CHAR = '\ufffd';
    @Nonnull
    public static final String REPLACEMENT_HEX = "fffd";
    @Nonnull
    private static final Map<Character, String> CHARACTER_TO_ENTITY_MAP = HTMLEncoder.mkCharacterToEntityMap();
    @Nonnull
    private static final String[] HEX = new String[256];

    private HTMLEncoder() {
    }

    @Nullable
    public static String encodeForHTML(@Nullable String input) {
        if (input == null) {
            return null;
        }
        return HTMLEncoder.encode(IMMUNE_HTML, input);
    }

    @Nullable
    public static String encodeForHTMLAttribute(@Nullable String input) {
        if (input == null) {
            return null;
        }
        return HTMLEncoder.encode(IMMUNE_HTMLATTR, input);
    }

    @Nonnull
    private static String encode(@Nonnull char[] immune, @Nonnull String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            sb.append(HTMLEncoder.encodeCharacter(immune, Character.valueOf(c)));
        }
        return sb.toString();
    }

    @Nonnull
    private static String encodeCharacter(@Nonnull char[] immune, @Nonnull Character toEncode) {
        String entityName;
        Character c = toEncode;
        if (HTMLEncoder.containsCharacter(c.charValue(), immune)) {
            return "" + c;
        }
        String hex = HTMLEncoder.getHexForNonAlphanumeric(c.charValue());
        if (hex == null) {
            return "" + c;
        }
        if (c.charValue() <= '\u001f' && c.charValue() != '\t' && c.charValue() != '\n' && c.charValue() != '\r' || c.charValue() >= '\u007f' && c.charValue() <= '\u009f') {
            hex = REPLACEMENT_HEX;
            c = Character.valueOf('\ufffd');
        }
        if ((entityName = CHARACTER_TO_ENTITY_MAP.get(c)) != null) {
            return "&" + entityName + ";";
        }
        return "&#x" + hex + ";";
    }

    @Nonnull
    private static String getHexForNonAlphanumeric(@Nonnull char c) {
        if (c < '\u00ff') {
            return HEX[c];
        }
        return Integer.toHexString(c);
    }

    private static boolean containsCharacter(@Nonnull char c, @Nonnull char[] array) {
        for (char ch : array) {
            if (c != ch) continue;
            return true;
        }
        return false;
    }

    @Nonnull
    private static synchronized Map<Character, String> mkCharacterToEntityMap() {
        HashMap<Character, String> map = new HashMap<Character, String>(252);
        map.put(Character.valueOf('\"'), "quot");
        map.put(Character.valueOf('&'), "amp");
        map.put(Character.valueOf('<'), "lt");
        map.put(Character.valueOf('>'), "gt");
        map.put(Character.valueOf('\u00a0'), "nbsp");
        map.put(Character.valueOf('\u00a1'), "iexcl");
        map.put(Character.valueOf('\u00a2'), "cent");
        map.put(Character.valueOf('\u00a3'), "pound");
        map.put(Character.valueOf('\u00a4'), "curren");
        map.put(Character.valueOf('\u00a5'), "yen");
        map.put(Character.valueOf('\u00a6'), "brvbar");
        map.put(Character.valueOf('\u00a7'), "sect");
        map.put(Character.valueOf('\u00a8'), "uml");
        map.put(Character.valueOf('\u00a9'), "copy");
        map.put(Character.valueOf('\u00aa'), "ordf");
        map.put(Character.valueOf('\u00ab'), "laquo");
        map.put(Character.valueOf('\u00ac'), "not");
        map.put(Character.valueOf('\u00ad'), "shy");
        map.put(Character.valueOf('\u00ae'), "reg");
        map.put(Character.valueOf('\u00af'), "macr");
        map.put(Character.valueOf('\u00b0'), "deg");
        map.put(Character.valueOf('\u00b1'), "plusmn");
        map.put(Character.valueOf('\u00b2'), "sup2");
        map.put(Character.valueOf('\u00b3'), "sup3");
        map.put(Character.valueOf('\u00b4'), "acute");
        map.put(Character.valueOf('\u00b5'), "micro");
        map.put(Character.valueOf('\u00b6'), "para");
        map.put(Character.valueOf('\u00b7'), "middot");
        map.put(Character.valueOf('\u00b8'), "cedil");
        map.put(Character.valueOf('\u00b9'), "sup1");
        map.put(Character.valueOf('\u00ba'), "ordm");
        map.put(Character.valueOf('\u00bb'), "raquo");
        map.put(Character.valueOf('\u00bc'), "frac14");
        map.put(Character.valueOf('\u00bd'), "frac12");
        map.put(Character.valueOf('\u00be'), "frac34");
        map.put(Character.valueOf('\u00bf'), "iquest");
        map.put(Character.valueOf('\u00c0'), "Agrave");
        map.put(Character.valueOf('\u00c1'), "Aacute");
        map.put(Character.valueOf('\u00c2'), "Acirc");
        map.put(Character.valueOf('\u00c3'), "Atilde");
        map.put(Character.valueOf('\u00c4'), "Auml");
        map.put(Character.valueOf('\u00c5'), "Aring");
        map.put(Character.valueOf('\u00c6'), "AElig");
        map.put(Character.valueOf('\u00c7'), "Ccedil");
        map.put(Character.valueOf('\u00c8'), "Egrave");
        map.put(Character.valueOf('\u00c9'), "Eacute");
        map.put(Character.valueOf('\u00ca'), "Ecirc");
        map.put(Character.valueOf('\u00cb'), "Euml");
        map.put(Character.valueOf('\u00cc'), "Igrave");
        map.put(Character.valueOf('\u00cd'), "Iacute");
        map.put(Character.valueOf('\u00ce'), "Icirc");
        map.put(Character.valueOf('\u00cf'), "Iuml");
        map.put(Character.valueOf('\u00d0'), "ETH");
        map.put(Character.valueOf('\u00d1'), "Ntilde");
        map.put(Character.valueOf('\u00d2'), "Ograve");
        map.put(Character.valueOf('\u00d3'), "Oacute");
        map.put(Character.valueOf('\u00d4'), "Ocirc");
        map.put(Character.valueOf('\u00d5'), "Otilde");
        map.put(Character.valueOf('\u00d6'), "Ouml");
        map.put(Character.valueOf('\u00d7'), "times");
        map.put(Character.valueOf('\u00d8'), "Oslash");
        map.put(Character.valueOf('\u00d9'), "Ugrave");
        map.put(Character.valueOf('\u00da'), "Uacute");
        map.put(Character.valueOf('\u00db'), "Ucirc");
        map.put(Character.valueOf('\u00dc'), "Uuml");
        map.put(Character.valueOf('\u00dd'), "Yacute");
        map.put(Character.valueOf('\u00de'), "THORN");
        map.put(Character.valueOf('\u00df'), "szlig");
        map.put(Character.valueOf('\u00e0'), "agrave");
        map.put(Character.valueOf('\u00e1'), "aacute");
        map.put(Character.valueOf('\u00e2'), "acirc");
        map.put(Character.valueOf('\u00e3'), "atilde");
        map.put(Character.valueOf('\u00e4'), "auml");
        map.put(Character.valueOf('\u00e5'), "aring");
        map.put(Character.valueOf('\u00e6'), "aelig");
        map.put(Character.valueOf('\u00e7'), "ccedil");
        map.put(Character.valueOf('\u00e8'), "egrave");
        map.put(Character.valueOf('\u00e9'), "eacute");
        map.put(Character.valueOf('\u00ea'), "ecirc");
        map.put(Character.valueOf('\u00eb'), "euml");
        map.put(Character.valueOf('\u00ec'), "igrave");
        map.put(Character.valueOf('\u00ed'), "iacute");
        map.put(Character.valueOf('\u00ee'), "icirc");
        map.put(Character.valueOf('\u00ef'), "iuml");
        map.put(Character.valueOf('\u00f0'), "eth");
        map.put(Character.valueOf('\u00f1'), "ntilde");
        map.put(Character.valueOf('\u00f2'), "ograve");
        map.put(Character.valueOf('\u00f3'), "oacute");
        map.put(Character.valueOf('\u00f4'), "ocirc");
        map.put(Character.valueOf('\u00f5'), "otilde");
        map.put(Character.valueOf('\u00f6'), "ouml");
        map.put(Character.valueOf('\u00f7'), "divide");
        map.put(Character.valueOf('\u00f8'), "oslash");
        map.put(Character.valueOf('\u00f9'), "ugrave");
        map.put(Character.valueOf('\u00fa'), "uacute");
        map.put(Character.valueOf('\u00fb'), "ucirc");
        map.put(Character.valueOf('\u00fc'), "uuml");
        map.put(Character.valueOf('\u00fd'), "yacute");
        map.put(Character.valueOf('\u00fe'), "thorn");
        map.put(Character.valueOf('\u00ff'), "yuml");
        map.put(Character.valueOf('\u0152'), "OElig");
        map.put(Character.valueOf('\u0153'), "oelig");
        map.put(Character.valueOf('\u0160'), "Scaron");
        map.put(Character.valueOf('\u0161'), "scaron");
        map.put(Character.valueOf('\u0178'), "Yuml");
        map.put(Character.valueOf('\u0192'), "fnof");
        map.put(Character.valueOf('\u02c6'), "circ");
        map.put(Character.valueOf('\u02dc'), "tilde");
        map.put(Character.valueOf('\u0391'), "Alpha");
        map.put(Character.valueOf('\u0392'), "Beta");
        map.put(Character.valueOf('\u0393'), "Gamma");
        map.put(Character.valueOf('\u0394'), "Delta");
        map.put(Character.valueOf('\u0395'), "Epsilon");
        map.put(Character.valueOf('\u0396'), "Zeta");
        map.put(Character.valueOf('\u0397'), "Eta");
        map.put(Character.valueOf('\u0398'), "Theta");
        map.put(Character.valueOf('\u0399'), "Iota");
        map.put(Character.valueOf('\u039a'), "Kappa");
        map.put(Character.valueOf('\u039b'), "Lambda");
        map.put(Character.valueOf('\u039c'), "Mu");
        map.put(Character.valueOf('\u039d'), "Nu");
        map.put(Character.valueOf('\u039e'), "Xi");
        map.put(Character.valueOf('\u039f'), "Omicron");
        map.put(Character.valueOf('\u03a0'), "Pi");
        map.put(Character.valueOf('\u03a1'), "Rho");
        map.put(Character.valueOf('\u03a3'), "Sigma");
        map.put(Character.valueOf('\u03a4'), "Tau");
        map.put(Character.valueOf('\u03a5'), "Upsilon");
        map.put(Character.valueOf('\u03a6'), "Phi");
        map.put(Character.valueOf('\u03a7'), "Chi");
        map.put(Character.valueOf('\u03a8'), "Psi");
        map.put(Character.valueOf('\u03a9'), "Omega");
        map.put(Character.valueOf('\u03b1'), "alpha");
        map.put(Character.valueOf('\u03b2'), "beta");
        map.put(Character.valueOf('\u03b3'), "gamma");
        map.put(Character.valueOf('\u03b4'), "delta");
        map.put(Character.valueOf('\u03b5'), "epsilon");
        map.put(Character.valueOf('\u03b6'), "zeta");
        map.put(Character.valueOf('\u03b7'), "eta");
        map.put(Character.valueOf('\u03b8'), "theta");
        map.put(Character.valueOf('\u03b9'), "iota");
        map.put(Character.valueOf('\u03ba'), "kappa");
        map.put(Character.valueOf('\u03bb'), "lambda");
        map.put(Character.valueOf('\u03bc'), "mu");
        map.put(Character.valueOf('\u03bd'), "nu");
        map.put(Character.valueOf('\u03be'), "xi");
        map.put(Character.valueOf('\u03bf'), "omicron");
        map.put(Character.valueOf('\u03c0'), "pi");
        map.put(Character.valueOf('\u03c1'), "rho");
        map.put(Character.valueOf('\u03c2'), "sigmaf");
        map.put(Character.valueOf('\u03c3'), "sigma");
        map.put(Character.valueOf('\u03c4'), "tau");
        map.put(Character.valueOf('\u03c5'), "upsilon");
        map.put(Character.valueOf('\u03c6'), "phi");
        map.put(Character.valueOf('\u03c7'), "chi");
        map.put(Character.valueOf('\u03c8'), "psi");
        map.put(Character.valueOf('\u03c9'), "omega");
        map.put(Character.valueOf('\u03d1'), "thetasym");
        map.put(Character.valueOf('\u03d2'), "upsih");
        map.put(Character.valueOf('\u03d6'), "piv");
        map.put(Character.valueOf('\u2002'), "ensp");
        map.put(Character.valueOf('\u2003'), "emsp");
        map.put(Character.valueOf('\u2009'), "thinsp");
        map.put(Character.valueOf('\u200c'), "zwnj");
        map.put(Character.valueOf('\u200d'), "zwj");
        map.put(Character.valueOf('\u200e'), "lrm");
        map.put(Character.valueOf('\u200f'), "rlm");
        map.put(Character.valueOf('\u2013'), "ndash");
        map.put(Character.valueOf('\u2014'), "mdash");
        map.put(Character.valueOf('\u2018'), "lsquo");
        map.put(Character.valueOf('\u2019'), "rsquo");
        map.put(Character.valueOf('\u201a'), "sbquo");
        map.put(Character.valueOf('\u201c'), "ldquo");
        map.put(Character.valueOf('\u201d'), "rdquo");
        map.put(Character.valueOf('\u201e'), "bdquo");
        map.put(Character.valueOf('\u2020'), "dagger");
        map.put(Character.valueOf('\u2021'), "Dagger");
        map.put(Character.valueOf('\u2022'), "bull");
        map.put(Character.valueOf('\u2026'), "hellip");
        map.put(Character.valueOf('\u2030'), "permil");
        map.put(Character.valueOf('\u2032'), "prime");
        map.put(Character.valueOf('\u2033'), "Prime");
        map.put(Character.valueOf('\u2039'), "lsaquo");
        map.put(Character.valueOf('\u203a'), "rsaquo");
        map.put(Character.valueOf('\u203e'), "oline");
        map.put(Character.valueOf('\u2044'), "frasl");
        map.put(Character.valueOf('\u20ac'), "euro");
        map.put(Character.valueOf('\u2111'), "image");
        map.put(Character.valueOf('\u2118'), "weierp");
        map.put(Character.valueOf('\u211c'), "real");
        map.put(Character.valueOf('\u2122'), "trade");
        map.put(Character.valueOf('\u2135'), "alefsym");
        map.put(Character.valueOf('\u2190'), "larr");
        map.put(Character.valueOf('\u2191'), "uarr");
        map.put(Character.valueOf('\u2192'), "rarr");
        map.put(Character.valueOf('\u2193'), "darr");
        map.put(Character.valueOf('\u2194'), "harr");
        map.put(Character.valueOf('\u21b5'), "crarr");
        map.put(Character.valueOf('\u21d0'), "lArr");
        map.put(Character.valueOf('\u21d1'), "uArr");
        map.put(Character.valueOf('\u21d2'), "rArr");
        map.put(Character.valueOf('\u21d3'), "dArr");
        map.put(Character.valueOf('\u21d4'), "hArr");
        map.put(Character.valueOf('\u2200'), "forall");
        map.put(Character.valueOf('\u2202'), "part");
        map.put(Character.valueOf('\u2203'), "exist");
        map.put(Character.valueOf('\u2205'), "empty");
        map.put(Character.valueOf('\u2207'), "nabla");
        map.put(Character.valueOf('\u2208'), "isin");
        map.put(Character.valueOf('\u2209'), "notin");
        map.put(Character.valueOf('\u220b'), "ni");
        map.put(Character.valueOf('\u220f'), "prod");
        map.put(Character.valueOf('\u2211'), "sum");
        map.put(Character.valueOf('\u2212'), "minus");
        map.put(Character.valueOf('\u2217'), "lowast");
        map.put(Character.valueOf('\u221a'), "radic");
        map.put(Character.valueOf('\u221d'), "prop");
        map.put(Character.valueOf('\u221e'), "infin");
        map.put(Character.valueOf('\u2220'), "ang");
        map.put(Character.valueOf('\u2227'), "and");
        map.put(Character.valueOf('\u2228'), "or");
        map.put(Character.valueOf('\u2229'), "cap");
        map.put(Character.valueOf('\u222a'), "cup");
        map.put(Character.valueOf('\u222b'), "int");
        map.put(Character.valueOf('\u2234'), "there4");
        map.put(Character.valueOf('\u223c'), "sim");
        map.put(Character.valueOf('\u2245'), "cong");
        map.put(Character.valueOf('\u2248'), "asymp");
        map.put(Character.valueOf('\u2260'), "ne");
        map.put(Character.valueOf('\u2261'), "equiv");
        map.put(Character.valueOf('\u2264'), "le");
        map.put(Character.valueOf('\u2265'), "ge");
        map.put(Character.valueOf('\u2282'), "sub");
        map.put(Character.valueOf('\u2283'), "sup");
        map.put(Character.valueOf('\u2284'), "nsub");
        map.put(Character.valueOf('\u2286'), "sube");
        map.put(Character.valueOf('\u2287'), "supe");
        map.put(Character.valueOf('\u2295'), "oplus");
        map.put(Character.valueOf('\u2297'), "otimes");
        map.put(Character.valueOf('\u22a5'), "perp");
        map.put(Character.valueOf('\u22c5'), "sdot");
        map.put(Character.valueOf('\u2308'), "lceil");
        map.put(Character.valueOf('\u2309'), "rceil");
        map.put(Character.valueOf('\u230a'), "lfloor");
        map.put(Character.valueOf('\u230b'), "rfloor");
        map.put(Character.valueOf('\u2329'), "lang");
        map.put(Character.valueOf('\u232a'), "rang");
        map.put(Character.valueOf('\u25ca'), "loz");
        map.put(Character.valueOf('\u2660'), "spades");
        map.put(Character.valueOf('\u2663'), "clubs");
        map.put(Character.valueOf('\u2665'), "hearts");
        map.put(Character.valueOf('\u2666'), "diams");
        return Collections.unmodifiableMap(map);
    }

    static {
        for (int c = 0; c < 255; c = (int)((char)(c + 1))) {
            HTMLEncoder.HEX[c] = c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122 ? null : Integer.toHexString(c).intern();
        }
    }
}

