/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.List;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.logging.mdc.customobjects.MdcChangeset;

public class DuplicateChangesets
implements CustomMdcObject {
    private List<MdcChangeset> duplicateChangesets;

    public List<MdcChangeset> getDuplicateChangesets() {
        return this.duplicateChangesets;
    }

    public void setDuplicateChangesets(List<MdcChangeset> duplicateChangesets) {
        this.duplicateChangesets = duplicateChangesets;
    }

    public DuplicateChangesets() {
    }

    public DuplicateChangesets(List<MdcChangeset> duplicateChangesets) {
        this.duplicateChangesets = duplicateChangesets;
    }
}

