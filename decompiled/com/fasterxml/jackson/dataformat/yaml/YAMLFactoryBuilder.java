/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import com.fasterxml.jackson.dataformat.yaml.util.StringQuotingChecker;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;

public class YAMLFactoryBuilder
extends TSFBuilder<YAMLFactory, YAMLFactoryBuilder> {
    protected int _formatGeneratorFeatures;
    protected int _formatParserFeatures;
    protected StringQuotingChecker _quotingChecker;
    protected DumperOptions.Version _version;
    protected LoaderOptions _loaderOptions;
    protected DumperOptions _dumperOptions;

    protected YAMLFactoryBuilder() {
        this._formatGeneratorFeatures = YAMLFactory.DEFAULT_YAML_GENERATOR_FEATURE_FLAGS;
    }

    public YAMLFactoryBuilder(YAMLFactory base) {
        super(base);
        this._formatGeneratorFeatures = base._yamlGeneratorFeatures;
        this._formatParserFeatures = base._yamlParserFeatures;
        this._version = base._version;
        this._quotingChecker = base._quotingChecker;
    }

    public YAMLFactoryBuilder enable(YAMLParser.Feature f) {
        this._formatParserFeatures |= f.getMask();
        return this;
    }

    public YAMLFactoryBuilder enable(YAMLParser.Feature first, YAMLParser.Feature ... other) {
        this._formatParserFeatures |= first.getMask();
        for (YAMLParser.Feature f : other) {
            this._formatParserFeatures |= f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLParser.Feature f) {
        this._formatParserFeatures &= ~f.getMask();
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLParser.Feature first, YAMLParser.Feature ... other) {
        this._formatParserFeatures &= ~first.getMask();
        for (YAMLParser.Feature f : other) {
            this._formatParserFeatures &= ~f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder configure(YAMLParser.Feature f, boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }

    public YAMLFactoryBuilder enable(YAMLGenerator.Feature f) {
        this._formatGeneratorFeatures |= f.getMask();
        return this;
    }

    public YAMLFactoryBuilder enable(YAMLGenerator.Feature first, YAMLGenerator.Feature ... other) {
        this._formatGeneratorFeatures |= first.getMask();
        for (YAMLGenerator.Feature f : other) {
            this._formatGeneratorFeatures |= f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLGenerator.Feature f) {
        this._formatGeneratorFeatures &= ~f.getMask();
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLGenerator.Feature first, YAMLGenerator.Feature ... other) {
        this._formatGeneratorFeatures &= ~first.getMask();
        for (YAMLGenerator.Feature f : other) {
            this._formatGeneratorFeatures &= ~f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder configure(YAMLGenerator.Feature f, boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }

    public YAMLFactoryBuilder stringQuotingChecker(StringQuotingChecker sqc) {
        this._quotingChecker = sqc;
        return this;
    }

    public YAMLFactoryBuilder yamlVersionToWrite(DumperOptions.Version v) {
        this._version = v;
        return this;
    }

    public YAMLFactoryBuilder loaderOptions(LoaderOptions loaderOptions) {
        this._loaderOptions = loaderOptions;
        if (!this._loaderOptions.isAllowDuplicateKeys()) {
            this.enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION);
        }
        return this;
    }

    public YAMLFactoryBuilder dumperOptions(DumperOptions dumperOptions) {
        this._dumperOptions = dumperOptions;
        return this;
    }

    public int formatParserFeaturesMask() {
        return this._formatParserFeatures;
    }

    public int formatGeneratorFeaturesMask() {
        return this._formatGeneratorFeatures;
    }

    public DumperOptions.Version yamlVersionToWrite() {
        return this._version;
    }

    public StringQuotingChecker stringQuotingChecker() {
        if (this._quotingChecker != null) {
            return this._quotingChecker;
        }
        return StringQuotingChecker.Default.instance();
    }

    public LoaderOptions loaderOptions() {
        return this._loaderOptions;
    }

    public DumperOptions dumperOptions() {
        return this._dumperOptions;
    }

    @Override
    public YAMLFactory build() {
        return new YAMLFactory(this);
    }
}

