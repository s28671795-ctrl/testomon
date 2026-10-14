/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.descriptor;

import jakarta.servlet.descriptor.JspPropertyGroupDescriptor;
import jakarta.servlet.descriptor.TaglibDescriptor;
import java.util.Collection;

public interface JspConfigDescriptor {
    public Collection<TaglibDescriptor> getTaglibs();

    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();
}

