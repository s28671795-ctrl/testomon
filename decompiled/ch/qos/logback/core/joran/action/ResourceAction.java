/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.joran.action;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.SaxEventInterpretationContext;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.ResourceModel;
import org.xml.sax.Attributes;

public abstract class ResourceAction
extends Action {
    private static final String FILE_ATTR = "file";
    private static final String URL_ATTR = "url";
    private static final String RESOURCE_ATTR = "resource";
    private static final String OPTIONAL_ATTR = "optional";
    Model parentModel;
    ResourceModel resourceModel;
    boolean inError = false;

    protected abstract ResourceModel makeNewResourceModel();

    @Override
    public void begin(SaxEventInterpretationContext seic, String tagName, Attributes attributes) throws ActionException {
        String optionalStr = attributes.getValue(OPTIONAL_ATTR);
        this.resourceModel = this.makeNewResourceModel();
        this.resourceModel.setOptional(optionalStr);
        this.fillInIncludeModelAttributes(this.resourceModel, tagName, attributes);
        if (!seic.isModelStackEmpty()) {
            this.parentModel = seic.peekModel();
        }
        int lineNumber = ResourceAction.getLineNumber(seic);
        this.resourceModel.setLineNumber(lineNumber);
        seic.pushModel(this.resourceModel);
    }

    private void fillInIncludeModelAttributes(ResourceModel resourceModel, String tagName, Attributes attributes) {
        this.resourceModel.setTag(tagName);
        String fileAttribute = attributes.getValue(FILE_ATTR);
        String urlAttribute = attributes.getValue(URL_ATTR);
        String resourceAttribute = attributes.getValue(RESOURCE_ATTR);
        this.resourceModel.setFile(fileAttribute);
        this.resourceModel.setUrl(urlAttribute);
        this.resourceModel.setResource(resourceAttribute);
    }

    @Override
    public void end(SaxEventInterpretationContext seic, String name) throws ActionException {
        if (this.inError) {
            return;
        }
        Model m4 = seic.peekModel();
        if (m4 != this.resourceModel) {
            this.addWarn("The object at the of the stack is not the model [" + this.resourceModel.idString() + "] pushed earlier.");
            this.addWarn("This is wholly unexpected.");
        }
        if (this.parentModel != null) {
            this.parentModel.addSubModel(this.resourceModel);
            seic.popModel();
        }
    }
}

