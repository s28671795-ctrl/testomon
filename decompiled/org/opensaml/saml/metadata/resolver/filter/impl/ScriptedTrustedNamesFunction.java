/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Function;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resource.Resource;
import net.shibboleth.utilities.java.support.scripting.EvaluableScript;
import org.opensaml.core.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptedTrustedNamesFunction
implements Function<XMLObject, Set<String>> {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ENGINE = "JavaScript";
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ScriptedTrustedNamesFunction.class);
    @Nonnull
    private final EvaluableScript script;
    @Nullable
    private Object customObject;
    @Nullable
    private final String logPrefix;

    protected ScriptedTrustedNamesFunction(@Nonnull EvaluableScript theScript, @Nullable String extraInfo) {
        this.script = Constraint.isNotNull(theScript, "Supplied script cannot be null");
        this.logPrefix = "Scripted Function from " + extraInfo + " :";
    }

    protected ScriptedTrustedNamesFunction(@Nonnull EvaluableScript theScript) {
        this.script = Constraint.isNotNull(theScript, "Supplied script should not be null");
        this.logPrefix = "Anonymous Scripted Function :";
    }

    @Nullable
    public Object getCustomObject() {
        return this.customObject;
    }

    @Nullable
    public void setCustomObject(Object object) {
        this.customObject = object;
    }

    @Override
    public Set<String> apply(@Nullable XMLObject context) {
        SimpleScriptContext scriptContext = new SimpleScriptContext();
        scriptContext.setAttribute("custom", this.getCustomObject(), 100);
        scriptContext.setAttribute("profileContext", context, 100);
        try {
            Object output = this.script.eval(scriptContext);
            return (Set)output;
        }
        catch (ScriptException e) {
            this.log.error("{} Error while executing Function script", (Object)this.logPrefix, (Object)e);
            return null;
        }
    }

    @Nonnull
    static ScriptedTrustedNamesFunction resourceScript(@Nonnull @NotEmpty String engineName, @Nonnull Resource resource) throws ScriptException, IOException {
        try (InputStream is = resource.getInputStream();){
            EvaluableScript script = new EvaluableScript(engineName, is);
            ScriptedTrustedNamesFunction scriptedTrustedNamesFunction = new ScriptedTrustedNamesFunction(script, resource.getDescription());
            return scriptedTrustedNamesFunction;
        }
    }

    @Nonnull
    static ScriptedTrustedNamesFunction resourceScript(@Nonnull Resource resource) throws ScriptException, IOException {
        return ScriptedTrustedNamesFunction.resourceScript(DEFAULT_ENGINE, resource);
    }

    @Nonnull
    static ScriptedTrustedNamesFunction inlineScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
        EvaluableScript script = new EvaluableScript(engineName, scriptSource);
        return new ScriptedTrustedNamesFunction(script, "Inline");
    }

    @Nonnull
    static ScriptedTrustedNamesFunction inlineScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
        return ScriptedTrustedNamesFunction.inlineScript(DEFAULT_ENGINE, scriptSource);
    }
}

