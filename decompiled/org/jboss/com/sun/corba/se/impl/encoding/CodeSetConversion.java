/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.nio.charset.UnsupportedCharsetException;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetCache;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;

public class CodeSetConversion {
    private static final int FALLBACK_CODESET = 0;
    private CodeSetCache cache = new CodeSetCache();

    public CTBConverter getCTBConverter(OSFCodeSetRegistry.Entry codeset) {
        int alignment = !codeset.isFixedWidth() ? 1 : codeset.getMaxBytesPerChar();
        return new JavaCTBConverter(codeset, alignment);
    }

    public CTBConverter getCTBConverter(OSFCodeSetRegistry.Entry codeset, boolean littleEndian, boolean useByteOrderMarkers) {
        if (codeset == OSFCodeSetRegistry.UCS_2) {
            return new UTF16CTBConverter(littleEndian);
        }
        if (codeset == OSFCodeSetRegistry.UTF_16) {
            if (useByteOrderMarkers) {
                return new UTF16CTBConverter();
            }
            return new UTF16CTBConverter(littleEndian);
        }
        int alignment = !codeset.isFixedWidth() ? 1 : codeset.getMaxBytesPerChar();
        return new JavaCTBConverter(codeset, alignment);
    }

    public BTCConverter getBTCConverter(OSFCodeSetRegistry.Entry codeset) {
        return new JavaBTCConverter(codeset);
    }

    public BTCConverter getBTCConverter(OSFCodeSetRegistry.Entry codeset, boolean defaultToLittleEndian) {
        if (codeset == OSFCodeSetRegistry.UTF_16 || codeset == OSFCodeSetRegistry.UCS_2) {
            return new UTF16BTCConverter(defaultToLittleEndian);
        }
        return new JavaBTCConverter(codeset);
    }

    private int selectEncoding(CodeSetComponentInfo.CodeSetComponent client, CodeSetComponentInfo.CodeSetComponent server) {
        int i;
        int serverNative = server.nativeCodeSet;
        if (serverNative == 0) {
            if (server.conversionCodeSets.length > 0) {
                serverNative = server.conversionCodeSets[0];
            } else {
                return 0;
            }
        }
        if (client.nativeCodeSet == serverNative) {
            return serverNative;
        }
        for (i = 0; i < client.conversionCodeSets.length; ++i) {
            if (serverNative != client.conversionCodeSets[i]) continue;
            return serverNative;
        }
        for (i = 0; i < server.conversionCodeSets.length; ++i) {
            if (client.nativeCodeSet != server.conversionCodeSets[i]) continue;
            return client.nativeCodeSet;
        }
        for (i = 0; i < server.conversionCodeSets.length; ++i) {
            for (int y = 0; y < client.conversionCodeSets.length; ++y) {
                if (server.conversionCodeSets[i] != client.conversionCodeSets[y]) continue;
                return server.conversionCodeSets[i];
            }
        }
        return 0;
    }

    public CodeSetComponentInfo.CodeSetContext negotiate(CodeSetComponentInfo client, CodeSetComponentInfo server) {
        int wcharData;
        int charData = this.selectEncoding(client.getCharComponent(), server.getCharComponent());
        if (charData == 0) {
            charData = OSFCodeSetRegistry.UTF_8.getNumber();
        }
        if ((wcharData = this.selectEncoding(client.getWCharComponent(), server.getWCharComponent())) == 0) {
            wcharData = OSFCodeSetRegistry.UTF_16.getNumber();
        }
        return new CodeSetComponentInfo.CodeSetContext(charData, wcharData);
    }

    private CodeSetConversion() {
    }

    public static final CodeSetConversion impl() {
        return CodeSetConversionHolder.csc;
    }

    private static class CodeSetConversionHolder {
        static final CodeSetConversion csc = new CodeSetConversion();

        private CodeSetConversionHolder() {
        }
    }

    private class UTF16BTCConverter
    extends JavaBTCConverter {
        private boolean defaultToLittleEndian;
        private boolean converterUsesBOM;
        private static final char UTF16_BE_MARKER = '\ufeff';
        private static final char UTF16_LE_MARKER = '\ufffe';

        public UTF16BTCConverter(boolean defaultToLittleEndian) {
            super(OSFCodeSetRegistry.UTF_16);
            this.converterUsesBOM = true;
            this.defaultToLittleEndian = defaultToLittleEndian;
        }

        @Override
        public char[] getChars(byte[] bytes, int offset, int numBytes) {
            if (this.hasUTF16ByteOrderMarker(bytes, offset, numBytes)) {
                if (!this.converterUsesBOM) {
                    this.switchToConverter(OSFCodeSetRegistry.UTF_16);
                }
                this.converterUsesBOM = true;
                return super.getChars(bytes, offset, numBytes);
            }
            if (this.converterUsesBOM) {
                if (this.defaultToLittleEndian) {
                    this.switchToConverter(OSFCodeSetRegistry.UTF_16LE);
                } else {
                    this.switchToConverter(OSFCodeSetRegistry.UTF_16BE);
                }
                this.converterUsesBOM = false;
            }
            return super.getChars(bytes, offset, numBytes);
        }

        private boolean hasUTF16ByteOrderMarker(byte[] array, int offset, int length) {
            if (length >= 4) {
                int b1 = array[offset] & 0xFF;
                int b2 = array[offset + 1] & 0xFF;
                char marker = (char)(b1 << 8 | b2 << 0);
                return marker == '\ufeff' || marker == '\ufffe';
            }
            return false;
        }

        private void switchToConverter(OSFCodeSetRegistry.Entry newCodeSet) {
            this.btc = super.getConverter(newCodeSet.getName());
        }
    }

    private class JavaBTCConverter
    extends BTCConverter {
        private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.encoding");
        private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
        protected CharsetDecoder btc;
        private char[] buffer;
        private int resultingNumChars;
        private OSFCodeSetRegistry.Entry codeset;

        public JavaBTCConverter(OSFCodeSetRegistry.Entry codeset) {
            this.btc = this.getConverter(codeset.getName());
            this.codeset = codeset;
        }

        @Override
        public final boolean isFixedWidthEncoding() {
            return this.codeset.isFixedWidth();
        }

        @Override
        public final int getFixedCharWidth() {
            return this.codeset.getMaxBytesPerChar();
        }

        @Override
        public final int getNumChars() {
            return this.resultingNumChars;
        }

        @Override
        public char[] getChars(byte[] bytes, int offset, int numBytes) {
            try {
                ByteBuffer byteBuf = ByteBuffer.wrap(bytes, offset, numBytes);
                CharBuffer charBuf = this.btc.decode(byteBuf);
                this.resultingNumChars = charBuf.limit();
                if (charBuf.limit() == charBuf.capacity()) {
                    this.buffer = charBuf.array();
                } else {
                    this.buffer = new char[charBuf.limit()];
                    charBuf.get(this.buffer, 0, charBuf.limit()).position(0);
                }
                return this.buffer;
            }
            catch (IllegalStateException ile) {
                throw this.wrapper.btcConverterFailure(ile);
            }
            catch (MalformedInputException mie) {
                throw this.wrapper.badUnicodePair(mie);
            }
            catch (UnmappableCharacterException uce) {
                throw this.omgWrapper.charNotInCodeset(uce);
            }
            catch (CharacterCodingException cce) {
                throw this.wrapper.btcConverterFailure(cce);
            }
        }

        protected CharsetDecoder getConverter(String javaCodeSetName) {
            CharsetDecoder result = null;
            try {
                result = CodeSetConversion.this.cache.getByteToCharConverter(javaCodeSetName);
                if (result == null) {
                    Charset tmpCharset = Charset.forName(javaCodeSetName);
                    result = tmpCharset.newDecoder();
                    CodeSetConversion.this.cache.setConverter((Object)javaCodeSetName, result);
                }
            }
            catch (IllegalCharsetNameException icne) {
                throw this.wrapper.invalidBtcConverterName(icne, (Object)javaCodeSetName);
            }
            return result;
        }
    }

    private class UTF16CTBConverter
    extends JavaCTBConverter {
        public UTF16CTBConverter() {
            super(OSFCodeSetRegistry.UTF_16, 2);
        }

        public UTF16CTBConverter(boolean littleEndian) {
            super(littleEndian ? OSFCodeSetRegistry.UTF_16LE : OSFCodeSetRegistry.UTF_16BE, 2);
        }
    }

    private class JavaCTBConverter
    extends CTBConverter {
        private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.encoding");
        private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
        private CharsetEncoder ctb;
        private int alignment;
        private char[] chars = null;
        private int numBytes = 0;
        private int numChars = 0;
        private ByteBuffer buffer;
        private OSFCodeSetRegistry.Entry codeset;

        public JavaCTBConverter(OSFCodeSetRegistry.Entry codeset, int alignmentForEncoding) {
            try {
                this.ctb = CodeSetConversion.this.cache.getCharToByteConverter(codeset.getName());
                if (this.ctb == null) {
                    Charset tmpCharset = Charset.forName(codeset.getName());
                    this.ctb = tmpCharset.newEncoder();
                    CodeSetConversion.this.cache.setConverter((Object)codeset.getName(), this.ctb);
                }
            }
            catch (IllegalCharsetNameException icne) {
                throw this.wrapper.invalidCtbConverterName(icne, (Object)codeset.getName());
            }
            catch (UnsupportedCharsetException ucne) {
                throw this.wrapper.invalidCtbConverterName(ucne, (Object)codeset.getName());
            }
            this.codeset = codeset;
            this.alignment = alignmentForEncoding;
        }

        @Override
        public final float getMaxBytesPerChar() {
            return this.ctb.maxBytesPerChar();
        }

        @Override
        public void convert(char chToConvert) {
            if (this.chars == null) {
                this.chars = new char[1];
            }
            this.chars[0] = chToConvert;
            this.numChars = 1;
            this.convertCharArray();
        }

        @Override
        public void convert(String strToConvert) {
            if (this.chars == null || this.chars.length < strToConvert.length()) {
                this.chars = new char[strToConvert.length()];
            }
            this.numChars = strToConvert.length();
            strToConvert.getChars(0, this.numChars, this.chars, 0);
            this.convertCharArray();
        }

        @Override
        public final int getNumBytes() {
            return this.numBytes;
        }

        @Override
        public final int getAlignment() {
            return this.alignment;
        }

        @Override
        public final boolean isFixedWidthEncoding() {
            return this.codeset.isFixedWidth();
        }

        @Override
        public byte[] getBytes() {
            return this.buffer.array();
        }

        private void convertCharArray() {
            try {
                this.buffer = this.ctb.encode(CharBuffer.wrap(this.chars, 0, this.numChars));
                this.numBytes = this.buffer.limit();
            }
            catch (IllegalStateException ise) {
                throw this.wrapper.ctbConverterFailure(ise);
            }
            catch (MalformedInputException mie) {
                throw this.wrapper.badUnicodePair(mie);
            }
            catch (UnmappableCharacterException uce) {
                throw this.omgWrapper.charNotInCodeset(uce);
            }
            catch (CharacterCodingException cce) {
                throw this.wrapper.ctbConverterFailure(cce);
            }
        }
    }

    public static abstract class BTCConverter {
        public abstract boolean isFixedWidthEncoding();

        public abstract int getFixedCharWidth();

        public abstract int getNumChars();

        public abstract char[] getChars(byte[] var1, int var2, int var3);
    }

    public static abstract class CTBConverter {
        public abstract void convert(char var1);

        public abstract void convert(String var1);

        public abstract int getNumBytes();

        public abstract float getMaxBytesPerChar();

        public abstract boolean isFixedWidthEncoding();

        public abstract int getAlignment();

        public abstract byte[] getBytes();
    }
}

