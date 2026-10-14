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

public final class DTDNmTokensAttr
extends DTDAttribute {
    public DTDNmTokensAttr(PrefixedName name, DefaultAttrValue defValue, int specIndex, boolean nsAware, boolean xml11) {
        super(name, defValue, specIndex, nsAware, xml11);
    }

    @Override
    public DTDAttribute cloneWith(int specIndex) {
        return new DTDNmTokensAttr(this.mName, this.mDefValue, specIndex, this.mCfgNsAware, this.mCfgXml11);
    }

    @Override
    public int getValueType() {
        return 9;
    }

    @Override
    public String validate(DTDValidatorBase v, char[] cbuf, int start2, int end, boolean normalize) throws XMLStreamException {
        while (start2 < end && WstxInputData.isSpaceChar(cbuf[start2])) {
            ++start2;
        }
        if (start2 >= end) {
            return this.reportValidationProblem(v, "Empty NMTOKENS value");
        }
        if (!normalize) {
            while (start2 < end) {
                char c = cbuf[start2];
                if (!WstxInputData.isSpaceChar(c) && !WstxInputData.isNameChar(c, this.mCfgNsAware, this.mCfgXml11)) {
                    return this.reportInvalidChar(v, c, "not valid as NMTOKENS character");
                }
                ++start2;
            }
            return null;
        }
        --end;
        while (end > start2 && WstxInputData.isSpaceChar(cbuf[end])) {
            --end;
        }
        StringBuilder sb = null;
        while (start2 <= end) {
            char c;
            int i;
            for (i = start2; i <= end && !WstxInputData.isSpaceChar(c = cbuf[i]); ++i) {
                if (WstxInputData.isNameChar(c, this.mCfgNsAware, this.mCfgXml11)) continue;
                return this.reportInvalidChar(v, c, "not valid as an NMTOKENS character");
            }
            if (sb == null) {
                sb = new StringBuilder(end - start2 + 1);
            } else {
                sb.append(' ');
            }
            sb.append(cbuf, start2, i - start2);
            for (start2 = i + 1; start2 <= end && WstxInputData.isSpaceChar(cbuf[start2]); ++start2) {
            }
        }
        return sb.toString();
    }

    @Override
    public void validateDefault(InputProblemReporter rep, boolean normalize) throws XMLStreamException {
        String defValue = this.mDefValue.getValue();
        int len = defValue.length();
        StringBuilder sb = null;
        int count = 0;
        int start2 = 0;
        block0: while (start2 < len) {
            char c = defValue.charAt(start2);
            while (WstxInputData.isSpaceChar(c)) {
                if (++start2 >= len) break block0;
                c = defValue.charAt(start2);
            }
            int i = start2 + 1;
            while (++i < len && !WstxInputData.isSpaceChar(c = defValue.charAt(i))) {
            }
            ++count;
            String token = defValue.substring(start2, i);
            int illegalIx = WstxInputData.findIllegalNmtokenChar(token, this.mCfgNsAware, this.mCfgXml11);
            if (illegalIx >= 0) {
                this.reportValidationProblem(rep, "Invalid default value '" + defValue + "'; character #" + illegalIx + " (" + WstxInputData.getCharDesc(defValue.charAt(illegalIx)) + ") not a valid NMTOKENS character");
            }
            if (normalize) {
                if (sb == null) {
                    sb = new StringBuilder(i - start2 + 32);
                } else {
                    sb.append(' ');
                }
                sb.append(token);
            }
            start2 = i + 1;
        }
        if (count == 0) {
            this.reportValidationProblem(rep, "Invalid default value '" + defValue + "'; empty String is not a valid NMTOKENS value");
            return;
        }
        if (normalize) {
            this.mDefValue.setValue(sb.toString());
        }
    }
}

