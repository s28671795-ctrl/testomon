/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.incoresoft.cleaner.CleanerListener;
import com.incoresoft.cleaner.CleaningTask;
import com.incoresoft.plugin.commons.cleaner.VezhaCleaningCategory;
import com.incoresoft.plugin.commons.utils.OsUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderCleaningTask
implements CleaningTask {
    private static final Logger log = LoggerFactory.getLogger(FolderCleaningTask.class);
    private static final OsUtils.OSType os = OsUtils.getOperatingSystemType();
    private final List<CleanerListener> listeners;
    private final String dir;

    public FolderCleaningTask(@NotNull String dir, @NotNull List<CleanerListener> listeners) {
        this.dir = Objects.requireNonNull(dir);
        this.listeners = Objects.requireNonNull(listeners);
    }

    @Override
    public void run() {
        try {
            ProcessBuilder builder = new ProcessBuilder(this.splitCommand(this.getCommand()));
            builder.start().waitFor();
            for (CleanerListener listener : this.listeners) {
                try {
                    listener.onStorageTaskExecuted(VezhaCleaningCategory.STORAGE_DATA, this.dir, true);
                }
                catch (Exception e) {
                    log.info("Listener failed");
                }
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "Storage cleaning task: " + this.getCommand();
    }

    private String getCommand() {
        if (os == OsUtils.OSType.Windows) {
            return String.format("cmd.exe /c rd /s /q %s", this.dir);
        }
        return String.format("rm -rf %s", this.dir);
    }

    private List<String> splitCommand(String command) {
        return Arrays.asList(command.split(" "));
    }
}

