/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.client.utils;

import java.util.StringTokenizer;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.utils.Idn;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class Rfc3492Idn
implements Idn {
    private static final int base = 36;
    private static final int tmin = 1;
    private static final int tmax = 26;
    private static final int skew = 38;
    private static final int damp = 700;
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final char delimiter = '-';
    private static final String ACE_PREFIX = "xn--";

    private int adapt(int delta, int numpoints, boolean firsttime) {
        int d = delta;
        d = firsttime ? (d /= 700) : (d /= 2);
        d += d / numpoints;
        int k = 0;
        while (d > 455) {
            d /= 35;
            k += 36;
        }
        return k + 36 * d / (d + 38);
    }

    private int digit(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 65;
        }
        if (c >= 'a' && c <= 'z') {
            return c - 97;
        }
        if (c >= '0' && c <= '9') {
            return c - 48 + 26;
        }
        throw new IllegalArgumentException("illegal digit: " + c);
    }

    @Override
    public String toUnicode(String punycode) {
        StringBuilder unicode = new StringBuilder(punycode.length());
        StringTokenizer tok = new StringTokenizer(punycode, ".");
        while (tok.hasMoreTokens()) {
            String t2 = tok.nextToken();
            if (unicode.length() > 0) {
                unicode.append('.');
            }
            if (t2.startsWith(ACE_PREFIX)) {
                t2 = this.decode(t2.substring(4));
            }
            unicode.append(t2);
        }
        return unicode.toString();
    }

    protected String decode(String s2) {
        String input = s2;
        int n = 128;
        int i = 0;
        int bias = 72;
        StringBuilder output = new StringBuilder(input.length());
        int lastdelim = input.lastIndexOf(45);
        if (lastdelim != -1) {
            output.append(input.subSequence(0, lastdelim));
            input = input.substring(lastdelim + 1);
        }
        while (!input.isEmpty()) {
            int oldi = i;
            int w = 1;
            int k = 36;
            while (!input.isEmpty()) {
                char c = input.charAt(0);
                input = input.substring(1);
                int digit = this.digit(c);
                i += digit * w;
                int t2 = k <= bias + 1 ? 1 : (k >= bias + 26 ? 26 : k - bias);
                if (digit < t2) break;
                w *= 36 - t2;
                k += 36;
            }
            bias = this.adapt(i - oldi, output.length() + 1, oldi == 0);
            n += i / (output.length() + 1);
            output.insert(i %= output.length() + 1, (char)n);
            ++i;
        }
        return output.toString();
    }
}

