/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.core.property.ConsoleCharsetPropertyDefiner
 */
package ch.qos.logback.core.property;

import ch.qos.logback.core.PropertyDefinerBase;
import java.io.Console;
import java.nio.charset.Charset;

public class ConsoleCharsetPropertyDefiner
extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        Console console = System.console();
        if (console != null) {
            Charset charset = console.charset();
            if (charset != null) {
                String charsetName = charset.name();
                this.addInfo("Found value '" + charsetName + "' as returned by System.console().");
                return charsetName;
            }
            this.addInfo("System.console() returned null charset. Returning \"NULL\" string as defined value.");
            return "null";
        }
        this.addWarn("System.console() returned null. Cannot compute console's charset, returning the \"NULL\" string, i.e. the default JVM charset");
        return "null";
    }
}

