/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.dtd;

import com.ctc.wstx.dtd.DTDAttribute;
import com.ctc.wstx.dtd.DTDValidatorBase;
import com.ctc.wstx.dtd.DefaultAttrValue;
import com.ctc.wstx.dtd.MinimalDTDReader;
import com.ctc.wstx.ent.EntityDecl;
import com.ctc.wstx.io.WstxInputData;
import com.ctc.wstx.sr.InputProblemReporter;
import com.ctc.wstx.util.PrefixedName;
import java.util.StringTokenizer;
import javax.xml.stream.XMLStreamException;

public final class DTDEntitiesAttr
extends DTDAttribute {
    public DTDEntitiesAttr(PrefixedName name, DefaultAttrValue defValue, int specIndex, boolean nsAware, boolean xml11) {
        super(name, defValue, specIndex, nsAware, xml11);
    }

    @Override
    public DTDAttribute cloneWith(int specIndex) {
        return new DTDEntitiesAttr(this.mName, this.mDefValue, specIndex, this.mCfgNsAware, this.mCfgXml11);
    }

    @Override
    public int getValueType() {
        return 6;
    }

    @Override
    public String validate(DTDValidatorBase v, char[] cbuf, int start2, int end, boolean normalize) throws XMLStreamException {
        while (start2 < end && WstxInputData.isSpaceChar(cbuf[start2])) {
            ++start2;
        }
        if (start2 >= end) {
            return this.reportValidationProblem(v, "Empty ENTITIES value");
        }
        --end;
        while (end > start2 && WstxInputData.isSpaceChar(cbuf[end])) {
            --end;
        }
        String idStr = null;
        StringBuilder sb = null;
        while (start2 <= end) {
            int i;
            char c = cbuf[start2];
            if (!WstxInputData.isNameStartChar(c, this.mCfgNsAware, this.mCfgXml11)) {
                return this.reportInvalidChar(v, c, "not valid as the first ENTITIES character");
            }
            for (i = start2 + 1; i <= end && !WstxInputData.isSpaceChar(c = cbuf[i]); ++i) {
                if (WstxInputData.isNameChar(c, this.mCfgNsAware, this.mCfgXml11)) continue;
                return this.reportInvalidChar(v, c, "not valid as an ENTITIES character");
            }
            EntityDecl ent = this.findEntityDecl(v, cbuf, start2, i - start2);
            start2 = i + 1;
            if (normalize) {
                if (idStr == null) {
                    idStr = ent.getName();
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(idStr);
                    }
                    idStr = ent.getName();
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
        String normStr = this.validateDefaultNames(rep, true);
        if (normalize) {
            this.mDefValue.setValue(normStr);
        }
        StringTokenizer st = new StringTokenizer(normStr);
        MinimalDTDReader dtdr = (MinimalDTDReader)rep;
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            EntityDecl ent = dtdr.findEntity(str);
            this.checkEntity(rep, normStr, ent);
        }
    }
}

