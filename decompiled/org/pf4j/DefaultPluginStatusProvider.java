/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import org.pf4j.PluginRuntimeException;
import org.pf4j.PluginStatusProvider;
import org.pf4j.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPluginStatusProvider
implements PluginStatusProvider {
    private static final Logger log = LoggerFactory.getLogger(DefaultPluginStatusProvider.class);
    private final Path pluginsRoot;
    private List<String> enabledPlugins;
    private List<String> disabledPlugins;

    public DefaultPluginStatusProvider(Path pluginsRoot) {
        this.pluginsRoot = pluginsRoot;
        try {
            this.enabledPlugins = FileUtils.readLines(this.getEnabledFilePath(), true);
            log.info("Enabled plugins: {}", (Object)this.enabledPlugins);
            this.disabledPlugins = FileUtils.readLines(this.getDisabledFilePath(), true);
            log.info("Disabled plugins: {}", (Object)this.disabledPlugins);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isPluginDisabled(String pluginId) {
        if (this.disabledPlugins.contains(pluginId)) {
            return true;
        }
        return !this.enabledPlugins.isEmpty() && !this.enabledPlugins.contains(pluginId);
    }

    @Override
    public void disablePlugin(String pluginId) {
        if (this.isPluginDisabled(pluginId)) {
            return;
        }
        if (Files.exists(this.getEnabledFilePath(), new LinkOption[0])) {
            this.enabledPlugins.remove(pluginId);
            try {
                FileUtils.writeLines(this.enabledPlugins, this.getEnabledFilePath());
            }
            catch (IOException e) {
                throw new PluginRuntimeException(e);
            }
        }
        this.disabledPlugins.add(pluginId);
        try {
            FileUtils.writeLines(this.disabledPlugins, this.getDisabledFilePath());
        }
        catch (IOException e) {
            throw new PluginRuntimeException(e);
        }
    }

    @Override
    public void enablePlugin(String pluginId) {
        if (!this.isPluginDisabled(pluginId)) {
            return;
        }
        if (Files.exists(this.getEnabledFilePath(), new LinkOption[0])) {
            this.enabledPlugins.add(pluginId);
            try {
                FileUtils.writeLines(this.enabledPlugins, this.getEnabledFilePath());
            }
            catch (IOException e) {
                throw new PluginRuntimeException(e);
            }
        }
        this.disabledPlugins.remove(pluginId);
        try {
            FileUtils.writeLines(this.disabledPlugins, this.getDisabledFilePath());
        }
        catch (IOException e) {
            throw new PluginRuntimeException(e);
        }
    }

    public Path getEnabledFilePath() {
        return DefaultPluginStatusProvider.getEnabledFilePath(this.pluginsRoot);
    }

    public Path getDisabledFilePath() {
        return DefaultPluginStatusProvider.getDisabledFilePath(this.pluginsRoot);
    }

    public static Path getEnabledFilePath(Path pluginsRoot) {
        return pluginsRoot.resolve("enabled.txt");
    }

    public static Path getDisabledFilePath(Path pluginsRoot) {
        return pluginsRoot.resolve("disabled.txt");
    }
}

