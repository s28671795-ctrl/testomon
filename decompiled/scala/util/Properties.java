/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.util.jar.Attributes;
import scala.Function0;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.util.Properties$;

@ScalaSignature(bytes="\u0006\u00059;Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0012mAQ\u0001J\u0001\u0005\u0012\u0015Bq\u0001O\u0001C\u0002\u0013\u0005\u0011\b\u0003\u0004N\u0003\u0001\u0006IAO\u0001\u000b!J|\u0007/\u001a:uS\u0016\u001c(BA\u0005\u000b\u0003\u0011)H/\u001b7\u000b\u0003-\tQa]2bY\u0006\u001c\u0001\u0001\u0005\u0002\u000f\u00035\t\u0001B\u0001\u0006Qe>\u0004XM\u001d;jKN\u001c2!A\t\u0016!\t\u00112#D\u0001\u000b\u0013\t!\"B\u0001\u0004B]f\u0014VM\u001a\t\u0003\u001dYI!a\u0006\u0005\u0003\u001fA\u0013x\u000e]3si&,7\u000f\u0016:bSR\fa\u0001P5oSRtD#A\u0007\u0002\u0019A\u0014x\u000e]\"bi\u0016<wN]=\u0016\u0003q\u0001\"!\b\u0012\u000e\u0003yQ!a\b\u0011\u0002\t1\fgn\u001a\u0006\u0002C\u0005!!.\u0019<b\u0013\t\u0019cD\u0001\u0004TiJLgnZ\u0001\u000fa&\u001c7NS1s\u0005\u0006\u001cX\rZ(o+\u00051\u0003cA\u000f(S%\u0011\u0001F\b\u0002\u0006\u00072\f7o\u001d\u0019\u0003U=\u00022AE\u0016.\u0013\ta#B\u0001\u0004PaRLwN\u001c\t\u0003]=b\u0001\u0001B\u00051\t\u0005\u0005\t\u0011!B\u0001c\t\u0019q\fJ\u0019\u0012\u0005I*\u0004C\u0001\n4\u0013\t!$BA\u0004O_RD\u0017N\\4\u0011\u0005I1\u0014BA\u001c\u000b\u0005\r\te._\u0001\u0015'\u000e\fG.Y\"p[BLG.\u001a:WKJ\u001c\u0018n\u001c8\u0016\u0003i\u0002\"a\u000f&\u000f\u0005q:eBA\u001fE\u001d\tq4I\u0004\u0002@\u00056\t\u0001I\u0003\u0002B\u0019\u00051AH]8pizJ\u0011!I\u0005\u0003\u0013\u0001J!!\u0012$\u0002\u0007)\f'O\u0003\u0002\nA%\u0011\u0001*S\u0001\u000b\u0003R$(/\u001b2vi\u0016\u001c(BA#G\u0013\tYEJ\u0001\u0003OC6,'B\u0001%J\u0003U\u00196-\u00197b\u0007>l\u0007/\u001b7feZ+'o]5p]\u0002\u0002")
public final class Properties {
    public static Attributes.Name ScalaCompilerVersion() {
        return Properties$.MODULE$.ScalaCompilerVersion();
    }

    public static void main(String[] args2) {
        Properties$.MODULE$.main(args2);
    }

    public static boolean isJavaAtLeast(int version) {
        return Properties$.MODULE$.isJavaAtLeast(version);
    }

    public static boolean isJavaAtLeast(String version) {
        return Properties$.MODULE$.isJavaAtLeast(version);
    }

    public static String scalacCmd() {
        return Properties$.MODULE$.scalacCmd();
    }

    public static String scalaCmd() {
        return Properties$.MODULE$.scalaCmd();
    }

    public static String versionMsg() {
        return Properties$.MODULE$.versionMsg();
    }

    public static String jdkHome() {
        return Properties$.MODULE$.jdkHome();
    }

    public static boolean isLinux() {
        return Properties$.MODULE$.isLinux();
    }

    public static boolean isMac() {
        return Properties$.MODULE$.isMac();
    }

    public static boolean isWin() {
        return Properties$.MODULE$.isWin();
    }

    public static String userName() {
        return Properties$.MODULE$.userName();
    }

    public static String userHome() {
        return Properties$.MODULE$.userHome();
    }

    public static String userDir() {
        return Properties$.MODULE$.userDir();
    }

    public static String tmpDir() {
        return Properties$.MODULE$.tmpDir();
    }

    public static String scalaHome() {
        return Properties$.MODULE$.scalaHome();
    }

    public static String osName() {
        return Properties$.MODULE$.osName();
    }

    public static String javaSpecName() {
        return Properties$.MODULE$.javaSpecName();
    }

    public static String javaSpecVendor() {
        return Properties$.MODULE$.javaSpecVendor();
    }

    public static String javaSpecVersion() {
        return Properties$.MODULE$.javaSpecVersion();
    }

    public static String javaVmVersion() {
        return Properties$.MODULE$.javaVmVersion();
    }

    public static String javaVmVendor() {
        return Properties$.MODULE$.javaVmVendor();
    }

    public static String javaVmName() {
        return Properties$.MODULE$.javaVmName();
    }

    public static String javaVmInfo() {
        return Properties$.MODULE$.javaVmInfo();
    }

    public static String javaVersion() {
        return Properties$.MODULE$.javaVersion();
    }

    public static String javaVendor() {
        return Properties$.MODULE$.javaVendor();
    }

    public static String javaHome() {
        return Properties$.MODULE$.javaHome();
    }

    public static String javaClassPath() {
        return Properties$.MODULE$.javaClassPath();
    }

    public static String lineSeparator() {
        return Properties$.MODULE$.lineSeparator();
    }

    public static String encodingString() {
        return Properties$.MODULE$.encodingString();
    }

    public static String sourceReader() {
        return Properties$.MODULE$.sourceReader();
    }

    public static String sourceEncoding() {
        return Properties$.MODULE$.sourceEncoding();
    }

    public static String copyrightString() {
        return Properties$.MODULE$.copyrightString();
    }

    public static String versionString() {
        return Properties$.MODULE$.versionString();
    }

    public static String versionNumberString() {
        return Properties$.MODULE$.versionNumberString();
    }

    public static Option<String> developmentVersion() {
        return Properties$.MODULE$.developmentVersion();
    }

    public static Option<String> releaseVersion() {
        return Properties$.MODULE$.releaseVersion();
    }

    public static Option<String> scalaPropOrNone(String name) {
        return Properties$.MODULE$.scalaPropOrNone(name);
    }

    public static String scalaPropOrEmpty(String name) {
        return Properties$.MODULE$.scalaPropOrEmpty(name);
    }

    public static String scalaPropOrElse(String name, Function0<String> alt) {
        return Properties$.MODULE$.scalaPropOrElse(name, alt);
    }

    public static Option<String> envOrSome(String name, Function0<Option<String>> alt) {
        return Properties$.MODULE$.envOrSome(name, alt);
    }

    public static Option<String> envOrNone(String name) {
        return Properties$.MODULE$.envOrNone(name);
    }

    public static String envOrElse(String name, Function0<String> alt) {
        return Properties$.MODULE$.envOrElse(name, alt);
    }

    public static String clearProp(String name) {
        return Properties$.MODULE$.clearProp(name);
    }

    public static String setProp(String name, String value) {
        return Properties$.MODULE$.setProp(name, value);
    }

    public static boolean propOrFalse(String name) {
        return Properties$.MODULE$.propOrFalse(name);
    }

    public static Option<String> propOrNone(String name) {
        return Properties$.MODULE$.propOrNone(name);
    }

    public static String propOrNull(String name) {
        return Properties$.MODULE$.propOrNull(name);
    }

    public static String propOrEmpty(String name) {
        return Properties$.MODULE$.propOrEmpty(name);
    }

    public static String propOrElse(String name, Function0<String> alt) {
        return Properties$.MODULE$.propOrElse(name, alt);
    }

    public static boolean propIsSetTo(String name, String value) {
        return Properties$.MODULE$.propIsSetTo(name, value);
    }

    public static boolean propIsSet(String name) {
        return Properties$.MODULE$.propIsSet(name);
    }
}

