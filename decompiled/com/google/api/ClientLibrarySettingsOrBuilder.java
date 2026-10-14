/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CppSettings;
import com.google.api.CppSettingsOrBuilder;
import com.google.api.DotnetSettings;
import com.google.api.DotnetSettingsOrBuilder;
import com.google.api.GoSettings;
import com.google.api.GoSettingsOrBuilder;
import com.google.api.JavaSettings;
import com.google.api.JavaSettingsOrBuilder;
import com.google.api.LaunchStage;
import com.google.api.NodeSettings;
import com.google.api.NodeSettingsOrBuilder;
import com.google.api.PhpSettings;
import com.google.api.PhpSettingsOrBuilder;
import com.google.api.PythonSettings;
import com.google.api.PythonSettingsOrBuilder;
import com.google.api.RubySettings;
import com.google.api.RubySettingsOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ClientLibrarySettingsOrBuilder
extends MessageOrBuilder {
    public String getVersion();

    public ByteString getVersionBytes();

    public int getLaunchStageValue();

    public LaunchStage getLaunchStage();

    public boolean getRestNumericEnums();

    public boolean hasJavaSettings();

    public JavaSettings getJavaSettings();

    public JavaSettingsOrBuilder getJavaSettingsOrBuilder();

    public boolean hasCppSettings();

    public CppSettings getCppSettings();

    public CppSettingsOrBuilder getCppSettingsOrBuilder();

    public boolean hasPhpSettings();

    public PhpSettings getPhpSettings();

    public PhpSettingsOrBuilder getPhpSettingsOrBuilder();

    public boolean hasPythonSettings();

    public PythonSettings getPythonSettings();

    public PythonSettingsOrBuilder getPythonSettingsOrBuilder();

    public boolean hasNodeSettings();

    public NodeSettings getNodeSettings();

    public NodeSettingsOrBuilder getNodeSettingsOrBuilder();

    public boolean hasDotnetSettings();

    public DotnetSettings getDotnetSettings();

    public DotnetSettingsOrBuilder getDotnetSettingsOrBuilder();

    public boolean hasRubySettings();

    public RubySettings getRubySettings();

    public RubySettingsOrBuilder getRubySettingsOrBuilder();

    public boolean hasGoSettings();

    public GoSettings getGoSettings();

    public GoSettingsOrBuilder getGoSettingsOrBuilder();
}

