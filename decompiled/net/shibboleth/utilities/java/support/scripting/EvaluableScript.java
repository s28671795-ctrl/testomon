/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.scripting;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class EvaluableScript {
    private final String scriptLanguage;
    private final String script;
    private ScriptEngine scriptEngine;
    private CompiledScript compiledScript;

    public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
        this.scriptLanguage = Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");
        this.script = Constraint.isNotNull(StringSupport.trimOrNull(scriptSource), "Script source can not be null or empty");
        this.initialize();
    }

    public EvaluableScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
        this("javascript", scriptSource);
    }

    public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull InputStream scriptSource) throws ScriptException {
        this.scriptLanguage = Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");
        try {
            this.script = StringSupport.inputStreamToString(Constraint.isNotNull(scriptSource, "Script source can not be null or empty"), null);
        }
        catch (IOException e) {
            throw new ScriptException(e);
        }
        this.initialize();
    }

    public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull File scriptSource) throws ScriptException {
        this.scriptLanguage = Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");
        Constraint.isNotNull(scriptSource, "Script source file can not be null");
        if (!scriptSource.exists()) {
            throw new ScriptException("Script source file " + scriptSource.getAbsolutePath() + " does not exist");
        }
        if (!scriptSource.canRead()) {
            throw new ScriptException("Script source file " + scriptSource.getAbsolutePath() + " exists but is not readable");
        }
        try {
            this.script = Constraint.isNotNull(StringSupport.trimOrNull(Files.toString(scriptSource, Charset.defaultCharset())), "Script source can not be empty");
        }
        catch (IOException e) {
            throw new ScriptException("Unable to read data from source file " + scriptSource.getAbsolutePath());
        }
        this.initialize();
    }

    @Nonnull
    public String getScript() {
        return this.script;
    }

    @Nonnull
    public String getScriptLanguage() {
        return this.scriptLanguage;
    }

    @Nullable
    public Object eval(Bindings scriptBindings) throws ScriptException {
        if (this.compiledScript != null) {
            return this.compiledScript.eval(scriptBindings);
        }
        return this.scriptEngine.eval(this.script, scriptBindings);
    }

    @Nullable
    public Object eval(ScriptContext scriptContext) throws ScriptException {
        if (this.compiledScript != null) {
            return this.compiledScript.eval(scriptContext);
        }
        return this.scriptEngine.eval(this.script, scriptContext);
    }

    private void initialize() throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        this.scriptEngine = engineManager.getEngineByName(this.scriptLanguage);
        Constraint.isNotNull(this.scriptEngine, "No scripting engine associated with scripting language " + this.scriptLanguage);
        this.compiledScript = this.scriptEngine instanceof Compilable ? ((Compilable)((Object)this.scriptEngine)).compile(this.script) : null;
    }
}

