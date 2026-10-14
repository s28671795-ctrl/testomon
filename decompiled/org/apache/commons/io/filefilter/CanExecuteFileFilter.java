/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class CanExecuteFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 3179904805251622989L;
    public static final IOFileFilter CAN_EXECUTE = new CanExecuteFileFilter();
    public static final IOFileFilter CANNOT_EXECUTE = new NotFileFilter(CAN_EXECUTE);

    protected CanExecuteFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.canExecute();
    }
}

