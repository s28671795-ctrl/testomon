/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.config.parser;

import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.impl.Parseable;
import com.typesafe.config.parser.ConfigDocument;
import java.io.File;
import java.io.Reader;

public final class ConfigDocumentFactory {
    public static ConfigDocument parseReader(Reader reader, ConfigParseOptions options) {
        return Parseable.newReader(reader, options).parseConfigDocument();
    }

    public static ConfigDocument parseReader(Reader reader) {
        return ConfigDocumentFactory.parseReader(reader, ConfigParseOptions.defaults());
    }

    public static ConfigDocument parseFile(File file, ConfigParseOptions options) {
        return Parseable.newFile(file, options).parseConfigDocument();
    }

    public static ConfigDocument parseFile(File file) {
        return ConfigDocumentFactory.parseFile(file, ConfigParseOptions.defaults());
    }

    public static ConfigDocument parseString(String s2, ConfigParseOptions options) {
        return Parseable.newString(s2, options).parseConfigDocument();
    }

    public static ConfigDocument parseString(String s2) {
        return ConfigDocumentFactory.parseString(s2, ConfigParseOptions.defaults());
    }
}

