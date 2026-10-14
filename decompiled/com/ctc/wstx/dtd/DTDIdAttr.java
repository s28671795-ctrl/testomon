/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.dtd;

import com.ctc.wstx.cfg.ErrorConsts;
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

public final class DTDIdAttr
extends DTDAttribute {
    public DTDIdAttr(PrefixedName name, DefaultAttrValue defValue, int specIndex, boolean nsAware, boolean xml11) {
        super(name, defValue, specIndex, nsAware, xml11);
    }

    @Override
    public DTDAttribute cloneWith(int specIndex) {
        return new DTDIdAttr(this.mName, this.mDefValue, specIndex, this.mCfgNsAware, this.mCfgXml11);
    }

    @Override
    public int getValueType() {
        return 2;
    }

    @Override
    public boolean typeIsId() {
        return true;
    }

    @Override
    public String validate(DTDValidatorBase v, char[] cbuf, int start2, int end, boolean normalize) throws XMLStreamException {
        while (start2 < end && WstxInputData.isSpaceChar(cbuf[start2])) {
            ++start2;
        }
        if (start2 >= end) {
            return this.reportValidationProblem(v, "Empty ID value");
        }
        --end;
        while (end > start2 && WstxInputData.isSpaceChar(cbuf[end])) {
            --end;
        }
        int c = cbuf[start2];
        if (!WstxInputData.isNameStartChar((char)c, this.mCfgNsAware, this.mCfgXml11)) {
            return this.reportInvalidChar(v, (char)c, "not valid as the first ID character");
        }
        int hash = c;
        for (int i = start2 + 1; i <= end; ++i) {
            c = cbuf[i];
            if (!WstxInputData.isNameChar((char)c, this.mCfgNsAware, this.mCfgXml11)) {
                return this.reportInvalidChar(v, (char)c, "not valid as an ID character");
            }
            hash = hash * 31 + c;
        }
        ElementIdMap m4 = v.getIdMap();
        PrefixedName elemName = v.getElemName();
        Location loc = v.getLocation();
        ElementId id = m4.addDefined(cbuf, start2, end - start2 + 1, hash, loc, elemName, this.mName);
        if (id.getLocation() != loc) {
            return this.reportValidationProblem(v, "Duplicate id '" + id.getId() + "', first declared at " + id.getLocation());
        }
        if (normalize) {
            return id.getId();
        }
        return null;
    }

    @Override
    public void validateDefault(InputProblemReporter rep, boolean normalize) {
        throw new IllegalStateException(ErrorConsts.ERR_INTERNAL);
    }
}

