/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalOutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;

public final class CodeSetComponentInfo {
    private CodeSetComponent forCharData;
    private CodeSetComponent forWCharData;
    public static final CodeSetComponentInfo JAVASOFT_DEFAULT_CODESETS;
    public static final CodeSetContext LOCAL_CODE_SETS;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CodeSetComponentInfo)) {
            return false;
        }
        CodeSetComponentInfo other = (CodeSetComponentInfo)obj;
        return this.forCharData.equals(other.forCharData) && this.forWCharData.equals(other.forWCharData);
    }

    public int hashCode() {
        return this.forCharData.hashCode() ^ this.forWCharData.hashCode();
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer("CodeSetComponentInfo(");
        sbuf.append("char_data:");
        sbuf.append(this.forCharData.toString());
        sbuf.append(" wchar_data:");
        sbuf.append(this.forWCharData.toString());
        sbuf.append(")");
        return sbuf.toString();
    }

    public CodeSetComponentInfo() {
        this.forCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forCharData;
        this.forWCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forWCharData;
    }

    public CodeSetComponentInfo(CodeSetComponent charData, CodeSetComponent wcharData) {
        this.forCharData = charData;
        this.forWCharData = wcharData;
    }

    public void read(MarshalInputStream in) {
        this.forCharData = new CodeSetComponent();
        this.forCharData.read(in);
        this.forWCharData = new CodeSetComponent();
        this.forWCharData.read(in);
    }

    public void write(MarshalOutputStream out) {
        this.forCharData.write(out);
        this.forWCharData.write(out);
    }

    public CodeSetComponent getCharComponent() {
        return this.forCharData;
    }

    public CodeSetComponent getWCharComponent() {
        return this.forWCharData;
    }

    public static CodeSetComponent createFromString(String str) {
        ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.encoding");
        if (str == null || str.length() == 0) {
            throw wrapper.badCodeSetString();
        }
        StringTokenizer stok = new StringTokenizer(str, ", ", false);
        int nativeSet = 0;
        int[] conversionInts = null;
        try {
            nativeSet = Integer.decode(stok.nextToken());
            if (OSFCodeSetRegistry.lookupEntry(nativeSet) == null) {
                throw wrapper.unknownNativeCodeset(new Integer(nativeSet));
            }
            ArrayList<Integer> conversionList = new ArrayList<Integer>(10);
            while (stok.hasMoreTokens()) {
                Integer value = Integer.decode(stok.nextToken());
                if (OSFCodeSetRegistry.lookupEntry(value) == null) {
                    throw wrapper.unknownConversionCodeSet(value);
                }
                conversionList.add(value);
            }
            conversionInts = new int[conversionList.size()];
            for (int i = 0; i < conversionInts.length; ++i) {
                conversionInts[i] = (Integer)conversionList.get(i);
            }
        }
        catch (NumberFormatException nfe) {
            throw wrapper.invalidCodeSetNumber(nfe);
        }
        catch (NoSuchElementException nsee) {
            throw wrapper.invalidCodeSetString(nsee, (Object)str);
        }
        return new CodeSetComponent(nativeSet, conversionInts);
    }

    static {
        CodeSetComponent charData = new CodeSetComponent(OSFCodeSetRegistry.ISO_8859_1.getNumber(), new int[]{OSFCodeSetRegistry.UTF_8.getNumber(), OSFCodeSetRegistry.ISO_646.getNumber()});
        CodeSetComponent wcharData = new CodeSetComponent(OSFCodeSetRegistry.UTF_16.getNumber(), new int[]{OSFCodeSetRegistry.UCS_2.getNumber()});
        JAVASOFT_DEFAULT_CODESETS = new CodeSetComponentInfo(charData, wcharData);
        LOCAL_CODE_SETS = new CodeSetContext(OSFCodeSetRegistry.ISO_8859_1.getNumber(), OSFCodeSetRegistry.UTF_16.getNumber());
    }

    public static final class CodeSetContext {
        private int char_data;
        private int wchar_data;

        public CodeSetContext() {
        }

        public CodeSetContext(int charEncoding, int wcharEncoding) {
            this.char_data = charEncoding;
            this.wchar_data = wcharEncoding;
        }

        public void read(MarshalInputStream in) {
            this.char_data = in.read_ulong();
            this.wchar_data = in.read_ulong();
        }

        public void write(MarshalOutputStream out) {
            out.write_ulong(this.char_data);
            out.write_ulong(this.wchar_data);
        }

        public int getCharCodeSet() {
            return this.char_data;
        }

        public int getWCharCodeSet() {
            return this.wchar_data;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append("CodeSetContext char set: ");
            sbuf.append(Integer.toHexString(this.char_data));
            sbuf.append(" wchar set: ");
            sbuf.append(Integer.toHexString(this.wchar_data));
            return sbuf.toString();
        }
    }

    public static final class CodeSetComponent {
        int nativeCodeSet;
        int[] conversionCodeSets;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CodeSetComponent)) {
                return false;
            }
            CodeSetComponent other = (CodeSetComponent)obj;
            return this.nativeCodeSet == other.nativeCodeSet && Arrays.equals(this.conversionCodeSets, other.conversionCodeSets);
        }

        public int hashCode() {
            int result = this.nativeCodeSet;
            for (int ctr = 0; ctr < this.conversionCodeSets.length; ++ctr) {
                result = 37 * result + this.conversionCodeSets[ctr];
            }
            return result;
        }

        public CodeSetComponent() {
        }

        public CodeSetComponent(int nativeCodeSet, int[] conversionCodeSets) {
            this.nativeCodeSet = nativeCodeSet;
            this.conversionCodeSets = conversionCodeSets == null ? new int[0] : conversionCodeSets;
        }

        public void read(MarshalInputStream in) {
            this.nativeCodeSet = in.read_ulong();
            int len = in.read_long();
            this.conversionCodeSets = new int[len];
            in.read_ulong_array(this.conversionCodeSets, 0, len);
        }

        public void write(MarshalOutputStream out) {
            out.write_ulong(this.nativeCodeSet);
            out.write_long(this.conversionCodeSets.length);
            out.write_ulong_array(this.conversionCodeSets, 0, this.conversionCodeSets.length);
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer("CodeSetComponent(");
            sbuf.append("native:");
            sbuf.append(Integer.toHexString(this.nativeCodeSet));
            sbuf.append(" conversion:");
            if (this.conversionCodeSets == null) {
                sbuf.append("null");
            } else {
                for (int i = 0; i < this.conversionCodeSets.length; ++i) {
                    sbuf.append(Integer.toHexString(this.conversionCodeSets[i]));
                    sbuf.append(' ');
                }
            }
            sbuf.append(")");
            return sbuf.toString();
        }
    }
}

