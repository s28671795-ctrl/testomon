/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.dtd;

import com.ctc.wstx.dtd.DTDAttribute;
import com.ctc.wstx.dtd.DTDValidatorBase;
import com.ctc.wstx.dtd.DefaultAttrValue;
import com.ctc.wstx.io.WstxInputData;
import com.ctc.wstx.sr.InputProblemReporter;
import com.ctc.wstx.util.ElementId;
import com.ctc.wstx.util.ElementIdMap;
import com.ctc.wstx.util.PrefixedName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

public final class DTDIdRefsAttr
extends DTDAttribute {
    public DTDIdRefsAttr(PrefixedName name, DefaultAttrValue defValue, int specIndex, boolean nsAware, boolean xml11) {
        super(name, defValue, specIndex, nsAware, xml11);
    }

    @Override
    public DTDAttribute cloneWith(int specIndex) {
        return new DTDIdRefsAttr(this.mName, this.mDefValue, specIndex, this.mCfgNsAware, this.mCfgXml11);
    }

    @Override
    public int getValueType() {
        return 4;
    }

    @Override
    public String validate(DTDValidatorBase v, char[] cbuf, int start2, int end, boolean normalize) throws XMLStreamException {
        while (start2 < end && WstxInputData.isSpaceChar(cbuf[start2])) {
            ++start2;
        }
        if (start2 >= end) {
            return this.reportValidationProblem(v, "Empty IDREFS value");
        }
        --end;
        while (end > start2 && WstxInputData.isSpaceChar(cbuf[end])) {
            --end;
        }
        ElementIdMap m4 = v.getIdMap();
        Location loc = v.getLocation();
        String idStr = null;
        StringBuilder sb = null;
        while (start2 <= end) {
            int i;
            int c = cbuf[start2];
            if (!WstxInputData.isNameStartChar((char)c, this.mCfgNsAware, this.mCfgXml11)) {
                return this.reportInvalidChar(v, (char)c, "not valid as the first IDREFS character");
            }
            int hash = c;
            for (i = start2 + 1; i <= end && !WstxInputData.isSpaceChar((char)(c = cbuf[i])); ++i) {
                if (!WstxInputData.isNameChar((char)c, this.mCfgNsAware, this.mCfgXml11)) {
                    return this.reportInvalidChar(v, (char)c, "not valid as an IDREFS character");
                }
                hash = hash * 31 + c;
            }
            ElementId id = m4.addReferenced(cbuf, start2, i - start2, hash, loc, v.getElemName(), this.mName);
            start2 = i + 1;
            if (normalize) {
                if (idStr == null) {
                    idStr = id.getId();
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(idStr);
                    }
                    idStr = id.getId();
                    sb.append(' ');
                    sb.append(idStr);
                }
            }
            while (start2 <= end && WstxInputData.isSpaceChar(cbuf[start2])) {
                ++start2;
            }
        }
        if (normalize) {
            if (sb != null) {
                idStr = sb.toString();
            }
            return idStr;
        }
        return null;
    }

    @Override
    public void validateDefault(InputProblemReporter rep, boolean normalize) throws XMLStreamException {
        String def = this.validateDefaultNames(rep, normalize);
        if (normalize) {
            this.mDefValue.setValue(def);
        }
    }
}

