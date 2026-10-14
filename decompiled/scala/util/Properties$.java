/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.util.Properties;
import java.util.jar.Attributes;
import scala.Function0;
import scala.Option;
import scala.util.PropertiesTrait;

public final class Properties$
implements PropertiesTrait {
    public static final Properties$ MODULE$ = new Properties$();
    private static final Attributes.Name ScalaCompilerVersion;
    private static String propFilename;
    private static Properties scalaProps;
    private static Option<String> releaseVersion;
    private static Option<String> developmentVersion;
    private static String versionString;
    private static String copyrightString;
    private static volatile boolean bitmap$0;

    static {
        PropertiesTrait.$init$(MODULE$);
        ScalaCompilerVersion = new Attributes.Name("Scala-Compiler-Version");
    }

    @Override
    public boolean propIsSet(String name) {
        return PropertiesTrait.propIsSet$(this, name);
    }

    @Override
    public boolean propIsSetTo(String name, String value) {
        return PropertiesTrait.propIsSetTo$(this, name, value);
    }

    @Override
    public String propOrElse(String name, Function0<String> alt) {
        return PropertiesTrait.propOrElse$(this, name, alt);
    }

    @Override
    public String propOrEmpty(String name) {
        return PropertiesTrait.propOrEmpty$(this, name);
    }

    @Override
    public String propOrNull(String name) {
        return PropertiesTrait.propOrNull$(this, name);
    }

    @Override
    public Option<String> propOrNone(String name) {
        return PropertiesTrait.propOrNone$(this, name);
    }

    @Override
    public boolean propOrFalse(String name) {
        return PropertiesTrait.propOrFalse$(this, name);
    }

    @Override
    public String setProp(String name, String value) {
        return PropertiesTrait.setProp$(this, name, value);
    }

    @Override
    public String clearProp(String name) {
        return PropertiesTrait.clearProp$(this, name);
    }

    @Override
    public String envOrElse(String name, Function0<String> alt) {
        return PropertiesTrait.envOrElse$(this, name, alt);
    }

    @Override
    public Option<String> envOrNone(String name) {
        return PropertiesTrait.envOrNone$(this, name);
    }

    @Override
    public Option<String> envOrSome(String name, Function0<Option<String>> alt) {
        return PropertiesTrait.envOrSome$(this, name, alt);
    }

    @Override
    public String scalaPropOrElse(String name, Function0<String> alt) {
        return PropertiesTrait.scalaPropOrElse$(this, name, alt);
    }

    @Override
    public String scalaPropOrEmpty(String name) {
        return PropertiesTrait.scalaPropOrEmpty$(this, name);
    }

    @Override
    public Option<String> scalaPropOrNone(String name) {
        return PropertiesTrait.scalaPropOrNone$(this, name);
    }

    @Override
    public String versionNumberString() {
        return PropertiesTrait.versionNumberString$(this);
    }

    @Override
    public String sourceEncoding() {
        return PropertiesTrait.sourceEncoding$(this);
    }

    @Override
    public String sourceReader() {
        return PropertiesTrait.sourceReader$(this);
    }

    @Override
    public String encodingString() {
        return PropertiesTrait.encodingString$(this);
    }

    @Override
    public String lineSeparator() {
        return PropertiesTrait.lineSeparator$(this);
    }

    @Override
    public String javaClassPath() {
        return PropertiesTrait.javaClassPath$(this);
    }

    @Override
    public String javaHome() {
        return PropertiesTrait.javaHome$(this);
    }

    @Override
    public String javaVendor() {
        return PropertiesTrait.javaVendor$(this);
    }

    @Override
    public String javaVersion() {
        return PropertiesTrait.javaVersion$(this);
    }

    @Override
    public String javaVmInfo() {
        return PropertiesTrait.javaVmInfo$(this);
    }

    @Override
    public String javaVmName() {
        return PropertiesTrait.javaVmName$(this);
    }

    @Override
    public String javaVmVendor() {
        return PropertiesTrait.javaVmVendor$(this);
    }

    @Override
    public String javaVmVersion() {
        return PropertiesTrait.javaVmVersion$(this);
    }

    @Override
    public String javaSpecVersion() {
        return PropertiesTrait.javaSpecVersion$(this);
    }

    @Override
    public String javaSpecVendor() {
        return PropertiesTrait.javaSpecVendor$(this);
    }

    @Override
    public String javaSpecName() {
        return PropertiesTrait.javaSpecName$(this);
    }

    @Override
    public String osName() {
        return PropertiesTrait.osName$(this);
    }

    @Override
    public String scalaHome() {
        return PropertiesTrait.scalaHome$(this);
    }

    @Override
    public String tmpDir() {
        return PropertiesTrait.tmpDir$(this);
    }

    @Override
    public String userDir() {
        return PropertiesTrait.userDir$(this);
    }

    @Override
    public String userHome() {
        return PropertiesTrait.userHome$(this);
    }

    @Override
    public String userName() {
        return PropertiesTrait.userName$(this);
    }

    @Override
    public boolean isWin() {
        return PropertiesTrait.isWin$(this);
    }

    @Override
    public boolean isMac() {
        return PropertiesTrait.isMac$(this);
    }

    @Override
    public boolean isLinux() {
        return PropertiesTrait.isLinux$(this);
    }

    @Override
    public boolean isAvian() {
        return PropertiesTrait.isAvian$(this);
    }

    @Override
    public boolean coloredOutputEnabled() {
        return PropertiesTrait.coloredOutputEnabled$(this);
    }

    @Override
    public String jdkHome() {
        return PropertiesTrait.jdkHome$(this);
    }

    @Override
    public String versionFor(String command) {
        return PropertiesTrait.versionFor$(this, command);
    }

    @Override
    public String versionMsg() {
        return PropertiesTrait.versionMsg$(this);
    }

    @Override
    public String scalaCmd() {
        return PropertiesTrait.scalaCmd$(this);
    }

    @Override
    public String scalacCmd() {
        return PropertiesTrait.scalacCmd$(this);
    }

    @Override
    public boolean isJavaAtLeast(String version) {
        return PropertiesTrait.isJavaAtLeast$((PropertiesTrait)this, version);
    }

    @Override
    public boolean isJavaAtLeast(int version) {
        return PropertiesTrait.isJavaAtLeast$((PropertiesTrait)this, version);
    }

    @Override
    public void main(String[] args2) {
        PropertiesTrait.main$(this, args2);
    }

    @Override
    public String propFilename() {
        return propFilename;
    }

    private Properties scalaProps$lzycompute() {
        synchronized (this) {
            if (!bitmap$0) {
                scalaProps = PropertiesTrait.scalaProps$(this);
                bitmap$0 = true;
            }
        }
        return scalaProps;
    }

    @Override
    public Properties scalaProps() {
        if (!bitmap$0) {
            return this.scalaProps$lzycompute();
        }
        return scalaProps;
    }

    @Override
    public Option<String> releaseVersion() {
        return releaseVersion;
    }

    @Override
    public Option<String> developmentVersion() {
        return developmentVersion;
    }

    @Override
    public String versionString() {
        return versionString;
    }

    @Override
    public String copyrightString() {
        return copyrightString;
    }

    @Override
    public void scala$util$PropertiesTrait$_setter_$propFilename_$eq(String x$1) {
        propFilename = x$1;
    }

    @Override
    public void scala$util$PropertiesTrait$_setter_$releaseVersion_$eq(Option<String> x$1) {
        releaseVersion = x$1;
    }

    @Override
    public void scala$util$PropertiesTrait$_setter_$developmentVersion_$eq(Option<String> x$1) {
        developmentVersion = x$1;
    }

    @Override
    public void scala$util$PropertiesTrait$_setter_$versionString_$eq(String x$1) {
        versionString = x$1;
    }

    @Override
    public void scala$util$PropertiesTrait$_setter_$copyrightString_$eq(String x$1) {
        copyrightString = x$1;
    }

    @Override
    public String propCategory() {
        return "library";
    }

    public Class<Option<?>> pickJarBasedOn() {
        return Option.class;
    }

    public Attributes.Name ScalaCompilerVersion() {
        return ScalaCompilerVersion;
    }

    private Properties$() {
    }
}

