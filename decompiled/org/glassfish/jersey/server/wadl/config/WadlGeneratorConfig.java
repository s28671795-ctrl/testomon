/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorLoader;

public abstract class WadlGeneratorConfig {
    public abstract List configure();

    public WadlGenerator createWadlGenerator(InjectionManager injectionManager) {
        WadlGenerator wadlGenerator;
        List wadlGeneratorDescriptions;
        try {
            wadlGeneratorDescriptions = this.configure();
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_GENERATOR_CONFIGURE(), e);
        }
        for (WadlGeneratorDescription desc : wadlGeneratorDescriptions) {
            desc.setConfiguratorClass(this.getClass());
        }
        try {
            wadlGenerator = WadlGeneratorLoader.loadWadlGeneratorDescriptions(injectionManager, wadlGeneratorDescriptions);
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_GENERATOR_LOAD(), e);
        }
        return wadlGenerator;
    }

    public static WadlGeneratorConfigDescriptionBuilder generator(Class<? extends WadlGenerator> generatorClass) {
        return new WadlGeneratorConfigDescriptionBuilder().generator(generatorClass);
    }

    static class WadlGeneratorConfigImpl
    extends WadlGeneratorConfig {
        public List<WadlGeneratorDescription> _descriptions;

        public WadlGeneratorConfigImpl(List<WadlGeneratorDescription> descriptions) {
            this._descriptions = descriptions;
        }

        @Override
        public List<WadlGeneratorDescription> configure() {
            return this._descriptions;
        }
    }

    public static class WadlGeneratorConfigDescriptionBuilder {
        private List<WadlGeneratorDescription> _descriptions = new ArrayList<WadlGeneratorDescription>();
        private WadlGeneratorDescription _description;

        public WadlGeneratorConfigDescriptionBuilder generator(Class<? extends WadlGenerator> generatorClass) {
            if (this._description != null) {
                this._descriptions.add(this._description);
            }
            this._description = new WadlGeneratorDescription();
            this._description.setGeneratorClass(generatorClass);
            return this;
        }

        public WadlGeneratorConfigDescriptionBuilder prop(String propName, Object propValue) {
            if (this._description.getProperties() == null) {
                this._description.setProperties(new Properties());
            }
            this._description.getProperties().put(propName, propValue);
            return this;
        }

        public List<WadlGeneratorDescription> descriptions() {
            if (this._description != null) {
                this._descriptions.add(this._description);
            }
            return this._descriptions;
        }

        public WadlGeneratorConfig build() {
            if (this._description != null) {
                this._descriptions.add(this._description);
            }
            return new WadlGeneratorConfigImpl(this._descriptions);
        }
    }
}

