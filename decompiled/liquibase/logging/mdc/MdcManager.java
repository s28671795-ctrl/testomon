/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc;

import java.util.List;
import java.util.Map;
import liquibase.Beta;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.logging.mdc.MdcObject;
import liquibase.plugin.Plugin;

@Beta
public interface MdcManager
extends Plugin {
    @Beta
    public MdcObject put(String var1, String var2);

    @Beta
    public MdcObject put(String var1, String var2, boolean var3);

    @Beta
    public MdcObject put(String var1, Map<String, Object> var2);

    @Beta
    public MdcObject put(String var1, Map<String, Object> var2, boolean var3);

    @Beta
    public MdcObject put(String var1, List<? extends CustomMdcObject> var2);

    @Beta
    public MdcObject put(String var1, CustomMdcObject var2);

    @Beta
    public MdcObject put(String var1, CustomMdcObject var2, boolean var3);

    @Beta
    public void remove(String var1);

    @Beta
    public void clear();

    @Beta
    public int getPriority();

    @Beta
    public Map<String, Object> getAll();
}

