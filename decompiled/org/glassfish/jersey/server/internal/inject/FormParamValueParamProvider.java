/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Encoded;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.util.collection.NullableMultivaluedHashMap;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
final class FormParamValueParamProvider
extends AbstractValueParamProvider {
    public FormParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.FORM);
    }

    @Override
    public Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        String parameterName = parameter.getSourceName();
        if (parameterName == null || parameterName.isEmpty()) {
            return null;
        }
        MultivaluedParameterExtractor<?> e = this.get(parameter);
        if (e == null) {
            return null;
        }
        return new FormParamValueProvider(e, !parameter.isEncoded());
    }

    private static final class FormParamValueProvider
    implements Function<ContainerRequest, Object> {
        private static final Annotation encodedAnnotation = FormParamValueProvider.getEncodedAnnotation();
        private final MultivaluedParameterExtractor<?> extractor;
        private final boolean decode;

        FormParamValueProvider(MultivaluedParameterExtractor<?> extractor, boolean decode) {
            this.extractor = extractor;
            this.decode = decode;
        }

        private static Form getCachedForm(ContainerRequest request, boolean decode) {
            return (Form)request.getProperty(decode ? "jersey.config.server.representation.decoded.form" : "jersey.config.server.representation.form");
        }

        private static ContainerRequest ensureValidRequest(ContainerRequest request) throws IllegalStateException {
            if (request.getMethod().equals("GET")) {
                throw new IllegalStateException(LocalizationMessages.FORM_PARAM_METHOD_ERROR());
            }
            if (!MediaTypes.typeEqual(MediaType.APPLICATION_FORM_URLENCODED_TYPE, request.getMediaType())) {
                throw new IllegalStateException(LocalizationMessages.FORM_PARAM_CONTENT_TYPE_ERROR());
            }
            return request;
        }

        private static Annotation getEncodedAnnotation() {
            @Encoded
            final class EncodedAnnotationTemp {
                EncodedAnnotationTemp() {
                }
            }
            return EncodedAnnotationTemp.class.getAnnotation(Encoded.class);
        }

        @Override
        public Object apply(ContainerRequest request) {
            Form form = FormParamValueProvider.getCachedForm(request, this.decode);
            if (form == null) {
                Form otherForm = FormParamValueProvider.getCachedForm(request, !this.decode);
                if (otherForm != null) {
                    form = this.switchUrlEncoding(request, otherForm);
                    this.cacheForm(request, form);
                } else {
                    form = this.getForm(request);
                    this.cacheForm(request, form);
                }
            }
            try {
                return this.extractor.extract(form.asMap());
            }
            catch (ExtractorException e) {
                throw new ParamException.FormParamException(e.getCause(), this.extractor.getName(), this.extractor.getDefaultValueString());
            }
        }

        private Form switchUrlEncoding(ContainerRequest request, Form otherForm) {
            Set entries = otherForm.asMap().entrySet();
            NullableMultivaluedHashMap<String, String> formMap = new NullableMultivaluedHashMap<String, String>();
            for (Map.Entry entry : entries) {
                String charsetName = ReaderWriter.getCharset(MediaType.valueOf(request.getHeaderString("Content-Type"))).name();
                try {
                    String key = this.decode ? URLDecoder.decode((String)entry.getKey(), charsetName) : URLEncoder.encode((String)entry.getKey(), charsetName);
                    for (String value : (List)entry.getValue()) {
                        if (value != null) {
                            formMap.add(key, this.decode ? URLDecoder.decode(value, charsetName) : URLEncoder.encode(value, charsetName));
                            continue;
                        }
                        formMap.add(key, null);
                    }
                }
                catch (UnsupportedEncodingException uee) {
                    throw new ProcessingException(LocalizationMessages.ERROR_UNSUPPORTED_ENCODING(charsetName, this.extractor.getName()), uee);
                }
            }
            return new Form(formMap);
        }

        private void cacheForm(ContainerRequest request, Form form) {
            request.setProperty(this.decode ? "jersey.config.server.representation.decoded.form" : "jersey.config.server.representation.form", form);
        }

        private Form getForm(ContainerRequest request) {
            return this.getFormParameters(FormParamValueProvider.ensureValidRequest(request));
        }

        private Form getFormParameters(ContainerRequest request) {
            if (MediaTypes.typeEqual(MediaType.APPLICATION_FORM_URLENCODED_TYPE, request.getMediaType())) {
                Form form;
                request.bufferEntity();
                if (this.decode) {
                    form = request.readEntity(Form.class);
                } else {
                    Annotation[] annotations = new Annotation[]{encodedAnnotation};
                    form = request.readEntity(Form.class, annotations);
                }
                return form == null ? new Form() : form;
            }
            return new Form();
        }
    }
}

