/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.google.inject.Inject;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.backup.file.BackupFilesManager;
import com.incoresoft.vezha.database.BackupInfosRepository;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.Condition;

public class BackupNamesManager {
    private final BackupInfosRepository repository;

    public String getUniqueName(String originalName) {
        List<String> backupNames = this.repository.getAll(new Condition[0]).stream().map(BackupInfo::getFileName).map(BackupFilesManager::withoutZipExtension).toList();
        if (!backupNames.contains(originalName)) {
            return originalName;
        }
        String patternString = "^" + originalName + "_\\((\\d+)\\)$";
        Pattern pattern = Pattern.compile(patternString);
        List<String> indexes = backupNames.stream().filter(name -> pattern.matcher((CharSequence)name).matches()).map(pattern::matcher).filter(Matcher::find).map(matcher -> matcher.group(1)).toList();
        if (indexes.isEmpty()) {
            return String.format("%s_(1)", originalName);
        }
        OptionalInt latestCopyIndexOptional = indexes.stream().filter(Objects::nonNull).map(Integer::parseInt).mapToInt(Integer::intValue).max();
        if (latestCopyIndexOptional.isPresent()) {
            return String.format("%s_(%d)", originalName, latestCopyIndexOptional.getAsInt() + 1);
        }
        return originalName;
    }

    @Inject
    public BackupNamesManager(BackupInfosRepository repository) {
        this.repository = repository;
    }
}

