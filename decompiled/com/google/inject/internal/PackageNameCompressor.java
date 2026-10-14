/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.inject.internal.Messages;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class PackageNameCompressor {
    static final String LEGEND_HEADER = "\n\n======================\nFull classname legend:\n======================\n";
    static final String LEGEND_FOOTER = "========================\nEnd of classname legend:\n========================\n";
    private static final ImmutableSet<String> PACKAGES_SKIPPED_IN_LEGEND = ImmutableSet.of("java.lang.", "java.util.");
    private static final Splitter PACKAGE_SPLITTER = Splitter.on('.');
    private static final Joiner PACKAGE_JOINER = Joiner.on('.');
    private static final Pattern CLASSNAME_PATTERN = Pattern.compile("[\\W](([a-z_0-9]++[.]){2,}+[A-Z][\\w$]*)");
    private static final Pattern QUOTED_PATTERN = Pattern.compile("([^\\\"]+)((\\\")?[^\\\"\\r\\n]*\\\")?");

    static String compressPackagesInMessage(String input) {
        Matcher matcher = CLASSNAME_PATTERN.matcher(input);
        HashSet<String> names = new HashSet<String>();
        while (matcher.find()) {
            String name = matcher.group(1);
            names.add(name);
        }
        Map<String, String> replacementMap = PackageNameCompressor.shortenNames(names);
        if (replacementMap.isEmpty()) {
            return input;
        }
        StringBuilder output = new StringBuilder();
        ImmutableSet<String> replacedShortNames = PackageNameCompressor.replaceFullNames(input, replacementMap, output);
        if (replacedShortNames.isEmpty()) {
            return input;
        }
        String classNameLegend = PackageNameCompressor.buildClassNameLegend(Maps.filterKeys(replacementMap, replacedShortNames::contains));
        return output.append(classNameLegend).toString();
    }

    private static ImmutableSet<String> replaceFullNames(String input, Map<String, String> replacementMap, StringBuilder output) {
        ImmutableSet.Builder replacedShortNames = ImmutableSet.builder();
        List shortNames = replacementMap.keySet().stream().sorted(Ordering.natural().reverse()).collect(Collectors.toList());
        Matcher matcher = QUOTED_PATTERN.matcher(input);
        while (matcher.find()) {
            String replaced = matcher.group(1);
            for (String shortName : shortNames) {
                String fullName = replacementMap.get(shortName);
                int beforeLen = replaced.length();
                if ((replaced = replaced.replace(fullName, shortName)).length() >= beforeLen) continue;
                replacedShortNames.add(shortName);
            }
            output.append(replaced);
            String quoted = matcher.group(2);
            if (quoted == null) continue;
            output.append(quoted);
        }
        return replacedShortNames.build();
    }

    private static String buildClassNameLegend(Map<String, String> replacementMap) {
        StringBuilder legendBuilder = new StringBuilder();
        int longestKey = replacementMap.keySet().stream().max(Comparator.comparing(String::length)).get().length();
        for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
            String shortName = entry.getKey();
            String fullName = entry.getValue();
            String prefix = fullName.substring(0, fullName.length() - shortName.length());
            if (PACKAGES_SKIPPED_IN_LEGEND.contains(prefix) && !shortName.contains(".")) continue;
            legendBuilder.append(shortName).append(": ").append(Strings.repeat(" ", longestKey - shortName.length())).append('\"').append(fullName).append('\"').append("\n");
        }
        return legendBuilder.length() == 0 ? "" : Messages.bold(LEGEND_HEADER) + Messages.faint(legendBuilder.toString()) + Messages.bold(LEGEND_FOOTER);
    }

    /*
     * Exception decompiling
     */
    private static Map<String, String> shortenNames(Collection<String> names) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[DOLOOP]], but top level block is 4[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private PackageNameCompressor() {
    }
}

