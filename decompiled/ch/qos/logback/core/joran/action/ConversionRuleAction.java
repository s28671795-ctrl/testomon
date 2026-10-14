/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.joran.action;

import ch.qos.logback.core.joran.action.BaseModelAction;
import ch.qos.logback.core.joran.action.PreconditionValidator;
import ch.qos.logback.core.joran.spi.SaxEventInterpretationContext;
import ch.qos.logback.core.model.ConversionRuleModel;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.util.OptionHelper;
import org.xml.sax.Attributes;

public class ConversionRuleAction
extends BaseModelAction {
    public static String CONVERTER_CLASS_ATTRIBUTE = "converterClass";

    @Override
    protected boolean validPreconditions(SaxEventInterpretationContext seic, String name, Attributes attributes) {
        boolean multipleClassAttributes;
        boolean missingClass;
        PreconditionValidator pv = new PreconditionValidator(this, seic, name, attributes);
        boolean invalidConverterClassAttribute = pv.isInvalidAttribute(CONVERTER_CLASS_ATTRIBUTE);
        boolean invalidClassAttribute = pv.isInvalidAttribute("class");
        if (!invalidConverterClassAttribute) {
            pv.addWarn("[" + CONVERTER_CLASS_ATTRIBUTE + "] attribute is deprecated and replaced by [class]. " + pv.getLocationSuffix());
        }
        boolean bl = missingClass = invalidClassAttribute && invalidConverterClassAttribute;
        if (missingClass) {
            pv.addMissingAttributeError("class");
            return false;
        }
        boolean bl2 = multipleClassAttributes = !invalidClassAttribute && !invalidConverterClassAttribute;
        if (multipleClassAttributes) {
            pv.addWarn("Both [" + CONVERTER_CLASS_ATTRIBUTE + "] attribute and [class] attribute specified. ");
            pv.addWarn("[class] attribute will override. ");
        }
        pv.validateGivenAttribute("conversionWord");
        return pv.isValid();
    }

    @Override
    protected Model buildCurrentModel(SaxEventInterpretationContext interpretationContext, String name, Attributes attributes) {
        String classStr;
        ConversionRuleModel conversionRuleModel = new ConversionRuleModel();
        conversionRuleModel.setConversionWord(attributes.getValue("conversionWord"));
        String converterClassStr = attributes.getValue(CONVERTER_CLASS_ATTRIBUTE);
        if (!OptionHelper.isNullOrEmpty(converterClassStr)) {
            conversionRuleModel.setClassName(converterClassStr);
        }
        if (!OptionHelper.isNullOrEmpty(classStr = attributes.getValue("class"))) {
            conversionRuleModel.setClassName(classStr);
        }
        return conversionRuleModel;
    }
}

