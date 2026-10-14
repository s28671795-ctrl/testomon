/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.superpom;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.apache.maven.model.InputSource;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.model.superpom.SuperPomProvider;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role=SuperPomProvider.class)
public class DefaultSuperPomProvider
implements SuperPomProvider {
    private Model superModel;
    @Requirement
    private ModelProcessor modelProcessor;

    public DefaultSuperPomProvider setModelProcessor(ModelProcessor modelProcessor) {
        this.modelProcessor = modelProcessor;
        return this;
    }

    @Override
    public Model getSuperModel(String version) {
        if (this.superModel == null) {
            String resource = "/org/apache/maven/model/pom-" + version + ".xml";
            InputStream is = this.getClass().getResourceAsStream(resource);
            if (is == null) {
                throw new IllegalStateException("The super POM " + resource + " was not found, please verify the integrity of your Maven installation");
            }
            try {
                HashMap<String, Object> options = new HashMap<String, Object>();
                options.put("xml:4.0.0", "xml:4.0.0");
                String modelId = "org.apache.maven:maven-model-builder:" + this.getClass().getPackage().getImplementationVersion() + ":super-pom";
                InputSource inputSource = new InputSource();
                inputSource.setModelId(modelId);
                inputSource.setLocation(this.getClass().getResource(resource).toExternalForm());
                options.put("org.apache.maven.model.io.inputSource", inputSource);
                this.superModel = this.modelProcessor.read(is, options);
            }
            catch (IOException e) {
                throw new IllegalStateException("The super POM " + resource + " is damaged, please verify the integrity of your Maven installation", e);
            }
        }
        return this.superModel;
    }
}

