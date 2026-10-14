/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core;

import com.github.zafarkhaja.semver.Version;
import com.google.common.base.Suppliers;
import com.incoresoft.core.ProjectPropertiesReader;
import com.incoresoft.version.models.MinorVersion;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class CoreServiceVersionSupplier {
    private final Supplier<Version> versionSupplier = Suppliers.memoize(this::readCoreVersion);
    private final ProjectPropertiesReader projectPropertiesReader = new ProjectPropertiesReader();

    @NotNull
    public Version getCoreVersion() {
        return this.versionSupplier.get();
    }

    public MinorVersion getMinorCoreVersion() {
        Version version = this.getCoreVersion();
        return new MinorVersion(version);
    }

    @NotNull
    private Version readCoreVersion() {
        return this.projectPropertiesReader.getVersion();
    }
}

