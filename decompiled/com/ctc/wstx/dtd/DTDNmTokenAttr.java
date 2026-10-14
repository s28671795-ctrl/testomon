/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.dtd;

import com.ctc.wstx.dtd.DTDAttribute;
import com.ctc.wstx.dtd.DTDValidatorBase;
import com.ctc.wstx.dtd.DefaultAttrValue;
import com.ctc.wstx.io.WstxInputData;
import com.ctc.wstx.sr.InputProblemReporter;
import com.ctc.wstx.util.PrefixedName;
import javax.xml.stream.XMLStreamException;

public final class DTDNmTokenAttr
extends DTDAttribute {
    public DTDNmTokenAttr(PrefixedName name, DefaultAttrValue defValue, int specIndex, boolean nsAware, boolean xml11) {
        super(name, defValue, specIndex, nsAware, xml11);
    }

    @Override
    public DTDAttribute cloneWith(int specIndex) {
        return new DTDNmTokenAttr(this.mName, this.mDefValue, specIndex, this.mCfgNsAware, this.mCfgXml11);
    }

    @Override
    public int getValueType() {
        return 8;
    }

    @Override
    public String validate(DTDValidatorBase v, char[] cbuf, int start2, int end, boolean normalize) throws XMLStreamException {
        int len;
        int origLen = end - start2;
        while (start2 < end && WstxInputData.isSpaceChar(cbuf[start2])) {
            ++start2;
        }
        if (start2 >= end) {
            return this.reportValidationProblem(v, "Empty NMTOKEN value");
        }
        --end;
        while (end > start2 && WstxInputData.isSpaceChar(cbuf[end])) {
            --end;
        }
        for (int i = start2; i <= end; ++i) {
            char c = cbuf[i];
            if (WstxInputData.isNameChar(c, this.mCfgNsAware, this.mCfgXml11)) continue;
            return this.reportInvalidChar(v, c, "not valid NMTOKEN character");
        }
        if (normalize && (len = end - start2 + 1) != origLen) {
            return new String(cbuf, start2, len);
        }
        return null;
    }

    @Override
    public void validateDefault(InputProblemReporter rep, boolean normalize) throws XMLStreamException {
        String def = this.validateDefaultNmToken(rep, normalize);
        if (normalize) {
            this.mDefValue.setValue(def);
        }
    }
}

