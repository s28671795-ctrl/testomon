/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.model.processor;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.GenericXMLConfigurator;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.SaxEventRecorder;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.model.IncludeModel;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.processor.ModelHandlerException;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.model.processor.ResourceHandlerBase;
import ch.qos.logback.core.spi.ContextAwarePropertyContainer;
import ch.qos.logback.core.util.OptionHelper;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.function.Supplier;

public class IncludeModelHandler
extends ResourceHandlerBase {
    boolean inError = false;

    public IncludeModelHandler(Context context) {
        super(context);
    }

    public static IncludeModelHandler makeInstance(Context context, ModelInterpretationContext mic) {
        return new IncludeModelHandler(context);
    }

    protected Class<IncludeModel> getSupportedModelClass() {
        return IncludeModel.class;
    }

    @Override
    public void handle(ModelInterpretationContext mic, Model model) throws ModelHandlerException {
        IncludeModel includeModel = (IncludeModel)model;
        Model modelFromIncludedFile = this.buildModelFromIncludedFile(mic, includeModel);
        if (modelFromIncludedFile == null) {
            this.warnIfRequired("Failed to build include model from included file");
            return;
        }
        this.processModelFromIncludedFile(includeModel, modelFromIncludedFile);
    }

    public Model buildModelFromIncludedFile(ContextAwarePropertyContainer capc, IncludeModel includeModel) throws ModelHandlerException {
        this.optional = OptionHelper.toBoolean(includeModel.getOptional(), false);
        if (!this.checkAttributes(includeModel)) {
            this.inError = true;
            return null;
        }
        InputStream in = this.getInputStream(capc, includeModel);
        if (in == null) {
            this.inError = true;
            return null;
        }
        SaxEventRecorder recorder = null;
        try {
            recorder = this.populateSaxEventRecorder(in);
            List<SaxEvent> saxEvents = recorder.getSaxEventList();
            if (saxEvents.isEmpty()) {
                this.addWarn("Empty sax event list");
                return null;
            }
            Supplier<? extends GenericXMLConfigurator> jcSupplier = capc.getConfiguratorSupplier();
            if (jcSupplier == null) {
                this.addError("null configurator supplier. Abandoning inclusion of [" + this.attributeInUse + "]");
                this.inError = true;
                return null;
            }
            GenericXMLConfigurator genericXMLConfigurator = jcSupplier.get();
            genericXMLConfigurator.getRuleStore().addPathPathMapping("included", "configuration");
            Model modelFromIncludedFile = genericXMLConfigurator.buildModelFromSaxEventList(recorder.getSaxEventList());
            return modelFromIncludedFile;
        }
        catch (JoranException e) {
            this.inError = true;
            this.addError("Error processing XML data in [" + this.attributeInUse + "]", e);
            return null;
        }
    }

    private void processModelFromIncludedFile(IncludeModel includeModel, Model modelFromIncludedFile) {
        includeModel.getSubModels().addAll(modelFromIncludedFile.getSubModels());
    }

    public SaxEventRecorder populateSaxEventRecorder(InputStream inputStream2) throws JoranException {
        SaxEventRecorder recorder = new SaxEventRecorder(this.context);
        recorder.recordEvents(inputStream2);
        return recorder;
    }

    private InputStream getInputStream(ContextAwarePropertyContainer capc, IncludeModel includeModel) {
        URL inputURL = this.getInputURL(capc, includeModel);
        if (inputURL == null) {
            return null;
        }
        ConfigurationWatchListUtil.addToWatchList(this.context, inputURL);
        return this.openURL(inputURL);
    }
}

