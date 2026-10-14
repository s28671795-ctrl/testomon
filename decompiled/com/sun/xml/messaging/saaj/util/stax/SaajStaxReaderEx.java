/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.util.stax;

import java.util.Iterator;
import javax.xml.soap.SOAPElement;
import javax.xml.stream.XMLStreamException;
import org.jvnet.staxex.Base64Data;
import org.jvnet.staxex.BinaryText;
import org.jvnet.staxex.NamespaceContextEx;
import org.jvnet.staxex.XMLStreamReaderEx;
import org.jvnet.staxex.util.DOMStreamReader;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class SaajStaxReaderEx
extends DOMStreamReader
implements XMLStreamReaderEx {
    private BinaryText binaryText = null;
    private Base64Data base64AttData = null;

    public SaajStaxReaderEx(SOAPElement se) {
        super(se);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int next() throws XMLStreamException {
        int r;
        this.binaryText = null;
        this.base64AttData = null;
        block4: while (true) {
            r = this._next();
            switch (r) {
                case 4: {
                    if (this._current instanceof BinaryText) {
                        this.binaryText = (BinaryText)((Object)this._current);
                        this.base64AttData = new Base64Data();
                        this.base64AttData.set(this.binaryText.getDataHandler());
                        return 4;
                    } else {
                        Node prev = this._current.getPreviousSibling();
                        if (prev != null && prev.getNodeType() == 3) continue block4;
                        Text t2 = (Text)this._current;
                        this.wholeText = t2.getWholeText();
                        if (this.wholeText.length() != 0) return 4;
                        continue block4;
                    }
                }
                case 1: {
                    this.splitAttributes();
                    return 1;
                }
            }
            break;
        }
        return r;
    }

    @Override
    public String getElementTextTrim() throws XMLStreamException {
        return null;
    }

    @Override
    public CharSequence getPCDATA() throws XMLStreamException {
        return this.binaryText != null ? this.base64AttData : this.getText();
    }

    @Override
    public NamespaceContextEx getNamespaceContext() {
        return new NamespaceContextEx(){

            @Override
            public String getNamespaceURI(String prefix) {
                return SaajStaxReaderEx.this._current.lookupNamespaceURI(prefix);
            }

            @Override
            public String getPrefix(String uri) {
                return SaajStaxReaderEx.this._current.lookupPrefix(uri);
            }

            public Iterator getPrefixes(String arg0) {
                return null;
            }

            @Override
            public Iterator<NamespaceContextEx.Binding> iterator() {
                return null;
            }
        };
    }

    @Override
    public int getTextLength() {
        return this.binaryText != null ? this.base64AttData.length() : super.getTextLength();
    }

    @Override
    public int getTextStart() {
        return this.binaryText != null ? 0 : super.getTextStart();
    }

    @Override
    public char[] getTextCharacters() {
        if (this.binaryText != null) {
            char[] chars = new char[this.base64AttData.length()];
            this.base64AttData.writeTo(chars, 0);
            return chars;
        }
        return super.getTextCharacters();
    }
}

