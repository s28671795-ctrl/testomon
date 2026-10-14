/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.guava.Primitives;
import org.glassfish.jersey.internal.routing.CombinedMediaType;
import org.glassfish.jersey.internal.routing.ContentTypeDeterminer;
import org.glassfish.jersey.internal.routing.RequestSpecificConsumesProducesAcceptor;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.ReaderModel;
import org.glassfish.jersey.message.WriterModel;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.MethodRouting;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.ResourceMethod;

final class MethodSelectingRouter
extends ContentTypeDeterminer
implements Router {
    private static final Logger LOGGER = Logger.getLogger(MethodSelectingRouter.class.getName());
    private static final Comparator<ConsumesProducesAcceptor> CONSUMES_PRODUCES_ACCEPTOR_COMPARATOR = new Comparator<ConsumesProducesAcceptor>(){

        @Override
        public int compare(ConsumesProducesAcceptor o1, ConsumesProducesAcceptor o2) {
            ResourceMethod model1 = o1.methodRouting.method;
            ResourceMethod model2 = o2.methodRouting.method;
            int compared = this.compare(model1.getConsumedTypes(), model2.getConsumedTypes());
            if (compared != 0) {
                return compared;
            }
            compared = this.compare(model1.getProducedTypes(), model2.getProducedTypes());
            if (compared != 0) {
                return compared;
            }
            compared = MediaTypes.PARTIAL_ORDER_COMPARATOR.compare(o1.consumes.getMediaType(), o2.consumes.getMediaType());
            if (compared != 0) {
                return compared;
            }
            return MediaTypes.PARTIAL_ORDER_COMPARATOR.compare(o1.produces.getMediaType(), o2.produces.getMediaType());
        }

        @Override
        private int compare(List<MediaType> mediaTypeList1, List<MediaType> mediaTypeList2) {
            mediaTypeList1 = mediaTypeList1.isEmpty() ? MediaTypes.WILDCARD_TYPE_SINGLETON_LIST : mediaTypeList1;
            mediaTypeList2 = mediaTypeList2.isEmpty() ? MediaTypes.WILDCARD_TYPE_SINGLETON_LIST : mediaTypeList2;
            return MediaTypes.MEDIA_TYPE_LIST_COMPARATOR.compare(mediaTypeList1, mediaTypeList2);
        }
    };
    private final Map<String, List<ConsumesProducesAcceptor>> consumesProducesAcceptors = new HashMap<String, List<ConsumesProducesAcceptor>>();
    private final Router router;

    MethodSelectingRouter(MessageBodyWorkers workers, List<MethodRouting> methodRoutings) {
        super(workers);
        HashSet<String> httpMethods = new HashSet<String>();
        for (MethodRouting methodRouting : methodRoutings) {
            String httpMethod = methodRouting.method.getHttpMethod();
            httpMethods.add(httpMethod);
            List<ConsumesProducesAcceptor> httpMethodBoundAcceptors = this.consumesProducesAcceptors.get(httpMethod);
            if (httpMethodBoundAcceptors == null) {
                httpMethodBoundAcceptors = new LinkedList<ConsumesProducesAcceptor>();
                this.consumesProducesAcceptors.put(httpMethod, httpMethodBoundAcceptors);
            }
            this.addAllConsumesProducesCombinations(httpMethodBoundAcceptors, methodRouting);
        }
        for (String httpMethod : httpMethods) {
            Collections.sort(this.consumesProducesAcceptors.get(httpMethod), CONSUMES_PRODUCES_ACCEPTOR_COMPARATOR);
        }
        this.router = !this.consumesProducesAcceptors.containsKey("HEAD") ? this.createHeadEnrichedRouter() : this.createInternalRouter();
    }

    Set<String> getHttpMethods() {
        return this.consumesProducesAcceptors.keySet();
    }

    private Router createInternalRouter() {
        return new Router(){

            @Override
            public Router.Continuation apply(RequestProcessingContext requestContext) {
                return Router.Continuation.of(requestContext, MethodSelectingRouter.this.getMethodRouter(requestContext));
            }
        };
    }

    @Override
    public Router.Continuation apply(RequestProcessingContext requestContext) {
        return this.router.apply(requestContext);
    }

    private void addAllConsumesProducesCombinations(List<ConsumesProducesAcceptor> acceptors, MethodRouting methodRouting) {
        ResourceMethod resourceMethod = methodRouting.method;
        LinkedHashSet<MediaType> effectiveInputTypes = new LinkedHashSet<MediaType>();
        boolean consumesFromWorkers = this.fillMediaTypes(effectiveInputTypes, resourceMethod, resourceMethod.getConsumedTypes(), true);
        LinkedHashSet<MediaType> effectiveOutputTypes = new LinkedHashSet<MediaType>();
        boolean producesFromWorkers = this.fillMediaTypes(effectiveOutputTypes, resourceMethod, resourceMethod.getProducedTypes(), false);
        HashSet<ConsumesProducesAcceptor> acceptorSet = new HashSet<ConsumesProducesAcceptor>();
        for (MediaType consumes : effectiveInputTypes) {
            for (MediaType produces : effectiveOutputTypes) {
                acceptorSet.add(new ConsumesProducesAcceptor(new CombinedMediaType.EffectiveMediaType(consumes, consumesFromWorkers), new CombinedMediaType.EffectiveMediaType(produces, producesFromWorkers), methodRouting));
            }
        }
        acceptors.addAll(acceptorSet);
    }

    private boolean fillMediaTypes(Set<MediaType> effectiveTypes, ResourceMethod resourceMethod, List<MediaType> methodTypes, boolean inputTypes) {
        boolean mediaTypesFromWorkers;
        if (methodTypes.size() > 1 || !methodTypes.contains(MediaType.WILDCARD_TYPE)) {
            effectiveTypes.addAll(methodTypes);
        }
        if (mediaTypesFromWorkers = effectiveTypes.isEmpty()) {
            Invocable invocableMethod = resourceMethod.getInvocable();
            if (inputTypes) {
                this.fillInputTypesFromWorkers(effectiveTypes, invocableMethod);
            } else {
                this.fillOutputTypesFromWorkers(effectiveTypes, invocableMethod.getRawResponseType());
            }
            boolean bl = mediaTypesFromWorkers = !effectiveTypes.isEmpty();
            if (!mediaTypesFromWorkers) {
                if (inputTypes) {
                    effectiveTypes.addAll(this.workers.getMessageBodyReaderMediaTypesByType(Object.class));
                } else {
                    effectiveTypes.addAll(this.workers.getMessageBodyWriterMediaTypesByType(Object.class));
                }
                mediaTypesFromWorkers = true;
            }
        }
        return mediaTypesFromWorkers;
    }

    private void fillOutputTypesFromWorkers(Set<MediaType> effectiveOutputTypes, Class<?> returnEntityType) {
        effectiveOutputTypes.addAll(this.workers.getMessageBodyWriterMediaTypesByType(returnEntityType));
    }

    private void fillInputTypesFromWorkers(Set<MediaType> effectiveInputTypes, Invocable invocableMethod) {
        for (Parameter p : invocableMethod.getParameters()) {
            if (p.getSource() != Parameter.Source.ENTITY) continue;
            effectiveInputTypes.addAll(this.workers.getMessageBodyReaderMediaTypesByType(p.getRawType()));
            break;
        }
    }

    private Parameter getEntityParam(Invocable invocable) {
        for (Parameter parameter : invocable.getParameters()) {
            if (parameter.getSource() != Parameter.Source.ENTITY || ContainerRequestContext.class.isAssignableFrom(parameter.getRawType())) continue;
            return parameter;
        }
        return null;
    }

    private List<Router> getMethodRouter(RequestProcessingContext context) {
        final ContainerRequest request = context.request();
        List<ConsumesProducesAcceptor> acceptors = this.consumesProducesAcceptors.get(request.getMethod());
        if (acceptors == null) {
            throw new NotAllowedException(Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(this.consumesProducesAcceptors.keySet()).build());
        }
        LinkedList<ConsumesProducesAcceptor> satisfyingAcceptors = new LinkedList<ConsumesProducesAcceptor>();
        Set differentInvokableMethods = Collections.newSetFromMap(new IdentityHashMap());
        for (ConsumesProducesAcceptor cpi : acceptors) {
            if (!cpi.isConsumable(request)) continue;
            satisfyingAcceptors.add(cpi);
            differentInvokableMethods.add(cpi.methodRouting.method);
        }
        if (satisfyingAcceptors.isEmpty()) {
            throw new NotSupportedException();
        }
        final List<AcceptableMediaType> acceptableMediaTypes = request.getQualifiedAcceptableMediaTypes();
        MediaType requestContentType = request.getMediaType();
        MediaType effectiveContentType = requestContentType == null ? MediaType.WILDCARD_TYPE : requestContentType;
        final MethodSelector methodSelector = this.selectMethod(acceptableMediaTypes, satisfyingAcceptors, effectiveContentType, differentInvokableMethods.size() == 1);
        if (methodSelector.selected != null) {
            RequestSpecificConsumesProducesAcceptor<MethodRouting> selected = methodSelector.selected;
            if (methodSelector.sameFitnessAcceptors != null) {
                this.reportMethodSelectionAmbiguity(acceptableMediaTypes, methodSelector.selected, methodSelector.sameFitnessAcceptors);
            }
            context.push(new Function<ContainerResponse, ContainerResponse>(){

                @Override
                public ContainerResponse apply(ContainerResponse responseContext) {
                    if (responseContext.getMediaType() == null && (responseContext.hasEntity() || "HEAD".equals(request.getMethod()))) {
                        MediaType effectiveResponseType = MethodSelectingRouter.this.determineResponseMediaType(responseContext.getEntityClass(), responseContext.getEntityType(), methodSelector.selected, acceptableMediaTypes);
                        if (MediaTypes.isWildcard(effectiveResponseType)) {
                            if (effectiveResponseType.isWildcardType() || "application".equalsIgnoreCase(effectiveResponseType.getType())) {
                                effectiveResponseType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
                            } else {
                                throw new NotAcceptableException();
                            }
                        }
                        responseContext.setMediaType(effectiveResponseType);
                    }
                    return responseContext;
                }
            });
            return selected.getMethodRouting().routers;
        }
        throw new NotAcceptableException();
    }

    private MediaType determineResponseMediaType(Class<?> entityClass, Type entityType, RequestSpecificConsumesProducesAcceptor<MethodRouting> selectedMethod, List<AcceptableMediaType> acceptableMediaTypes) {
        if (MethodSelectingRouter.usePreSelectedMediaType(selectedMethod, acceptableMediaTypes)) {
            return selectedMethod.getProduces().getCombinedType();
        }
        ResourceMethod resourceMethod = selectedMethod.getMethodRouting().method;
        Invocable invocable = resourceMethod.getInvocable();
        Class<?> responseEntityClass = entityClass == null ? invocable.getRawRoutingResponseType() : entityClass;
        Method handlingMethod = invocable.getHandlingMethod();
        List<MediaType> methodProducesTypes = !resourceMethod.getProducedTypes().isEmpty() ? resourceMethod.getProducedTypes() : Collections.singletonList(MediaType.WILDCARD_TYPE);
        return super.determineResponseMediaType(responseEntityClass, entityType, selectedMethod, acceptableMediaTypes, methodProducesTypes, handlingMethod.getDeclaredAnnotations());
    }

    private static boolean usePreSelectedMediaType(RequestSpecificConsumesProducesAcceptor<MethodRouting> selectedMethod, List<AcceptableMediaType> acceptableMediaTypes) {
        if (!selectedMethod.producesFromProviders() && selectedMethod.getMethodRouting().method.getProducedTypes().size() == 1) {
            return true;
        }
        return acceptableMediaTypes.size() == 1 && !MediaTypes.isWildcard(acceptableMediaTypes.get(0));
    }

    private boolean isWriteable(RequestSpecificConsumesProducesAcceptor<MethodRouting> candidate) {
        Invocable invocable = candidate.getMethodRouting().method.getInvocable();
        Class<?> responseType = Primitives.wrap(invocable.getRawRoutingResponseType());
        if (Response.class.isAssignableFrom(responseType) || Void.class.isAssignableFrom(responseType)) {
            return true;
        }
        Type genericType = invocable.getRoutingResponseType();
        Type genericReturnType = genericType instanceof GenericType ? ((GenericType)((Object)genericType)).getType() : genericType;
        for (WriterModel model : this.workers.getWritersModelsForType(responseType)) {
            if (!model.isWriteable(responseType, genericReturnType, invocable.getHandlingMethod().getDeclaredAnnotations(), candidate.getProduces().getCombinedType())) continue;
            return true;
        }
        return false;
    }

    private boolean isReadable(RequestSpecificConsumesProducesAcceptor<MethodRouting> candidate) {
        Invocable invocable = candidate.getMethodRouting().method.getInvocable();
        Method handlingMethod = invocable.getHandlingMethod();
        Parameter entityParam = this.getEntityParam(invocable);
        if (entityParam == null) {
            return true;
        }
        Class<?> entityType = entityParam.getRawType();
        for (ReaderModel model : this.workers.getReaderModelsForType(entityType)) {
            if (!model.isReadable(entityType, entityParam.getType(), handlingMethod.getDeclaredAnnotations(), candidate.getConsumes().getCombinedType())) continue;
            return true;
        }
        return false;
    }

    private MethodSelector selectMethod(List<AcceptableMediaType> acceptableMediaTypes, List<ConsumesProducesAcceptor> satisfyingAcceptors, MediaType effectiveContentType, boolean singleInvokableMethod) {
        MethodSelector method2 = new MethodSelector(null);
        MethodSelector alternative = new MethodSelector(null);
        for (MediaType mediaType : acceptableMediaTypes) {
            for (ConsumesProducesAcceptor satisfiable : satisfyingAcceptors) {
                CombinedMediaType produces = CombinedMediaType.create(mediaType, satisfiable.produces);
                if (produces == CombinedMediaType.NO_MATCH) continue;
                CombinedMediaType consumes = CombinedMediaType.create(effectiveContentType, satisfiable.consumes);
                RequestSpecificConsumesProducesAcceptor<MethodRouting> candidate = new RequestSpecificConsumesProducesAcceptor<MethodRouting>(consumes, produces, satisfiable.produces.isDerived(), satisfiable.methodRouting);
                if (singleInvokableMethod) {
                    return new MethodSelector(candidate);
                }
                if (candidate.compareTo(method2.selected) >= 0) continue;
                if (method2.selected == null || candidate.getMethodRouting().method != method2.selected.getMethodRouting().method) {
                    if (this.isReadable(candidate) && this.isWriteable(candidate)) {
                        method2.consider(candidate);
                        continue;
                    }
                    alternative.consider(candidate);
                    continue;
                }
                method2.consider(candidate);
            }
        }
        return method2.selected != null ? method2 : alternative;
    }

    private void reportMethodSelectionAmbiguity(List<AcceptableMediaType> acceptableTypes, RequestSpecificConsumesProducesAcceptor<MethodRouting> selected, List<RequestSpecificConsumesProducesAcceptor> sameFitnessAcceptors) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            StringBuilder msgBuilder = new StringBuilder(LocalizationMessages.AMBIGUOUS_RESOURCE_METHOD(acceptableTypes)).append('\n');
            msgBuilder.append('\t').append(selected.getMethodRouting().method).append('\n');
            HashSet<ResourceMethod> reportedMethods = new HashSet<ResourceMethod>();
            reportedMethods.add(selected.getMethodRouting().method);
            for (RequestSpecificConsumesProducesAcceptor i : sameFitnessAcceptors) {
                if (!reportedMethods.contains(((MethodRouting)i.getMethodRouting()).method)) {
                    msgBuilder.append('\t').append(((MethodRouting)i.getMethodRouting()).method).append('\n');
                }
                reportedMethods.add(((MethodRouting)i.getMethodRouting()).method);
            }
            LOGGER.log(Level.WARNING, msgBuilder.toString());
        }
    }

    private Router createHeadEnrichedRouter() {
        return new Router(){

            @Override
            public Router.Continuation apply(RequestProcessingContext context) {
                ContainerRequest request = context.request();
                if ("HEAD".equals(request.getMethod())) {
                    request.setMethodWithoutException("GET");
                    context.push(new Function<ContainerResponse, ContainerResponse>(){

                        @Override
                        public ContainerResponse apply(ContainerResponse responseContext) {
                            responseContext.getRequestContext().setMethodWithoutException("HEAD");
                            return responseContext;
                        }
                    });
                }
                return Router.Continuation.of(context, MethodSelectingRouter.this.getMethodRouter(context));
            }
        };
    }

    private static class MethodSelector {
        RequestSpecificConsumesProducesAcceptor<MethodRouting> selected;
        List<RequestSpecificConsumesProducesAcceptor> sameFitnessAcceptors;

        MethodSelector(RequestSpecificConsumesProducesAcceptor<MethodRouting> i) {
            this.selected = i;
            this.sameFitnessAcceptors = null;
        }

        void consider(RequestSpecificConsumesProducesAcceptor<MethodRouting> i) {
            int theLessTheBetter = i.compareTo(this.selected);
            if (theLessTheBetter < 0) {
                this.selected = i;
                this.sameFitnessAcceptors = null;
            } else if (theLessTheBetter == 0 && this.selected.getMethodRouting() != i.getMethodRouting()) {
                this.getSameFitnessList().add(i);
            }
        }

        List<RequestSpecificConsumesProducesAcceptor> getSameFitnessList() {
            if (this.sameFitnessAcceptors == null) {
                this.sameFitnessAcceptors = new LinkedList<RequestSpecificConsumesProducesAcceptor>();
            }
            return this.sameFitnessAcceptors;
        }
    }

    private static class ConsumesProducesAcceptor {
        final CombinedMediaType.EffectiveMediaType consumes;
        final CombinedMediaType.EffectiveMediaType produces;
        final MethodRouting methodRouting;

        private ConsumesProducesAcceptor(CombinedMediaType.EffectiveMediaType consumes, CombinedMediaType.EffectiveMediaType produces, MethodRouting methodRouting) {
            this.methodRouting = methodRouting;
            this.consumes = consumes;
            this.produces = produces;
        }

        boolean isConsumable(ContainerRequest requestContext) {
            MediaType contentType = requestContext.getMediaType();
            return contentType == null || this.consumes.getMediaType().isCompatible(contentType);
        }

        public String toString() {
            return String.format("%s->%s:%s", this.consumes.getMediaType(), this.produces.getMediaType(), this.methodRouting);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ConsumesProducesAcceptor)) {
                return false;
            }
            ConsumesProducesAcceptor that = (ConsumesProducesAcceptor)o;
            if (this.consumes != null ? !this.consumes.equals(that.consumes) : that.consumes != null) {
                return false;
            }
            if (this.methodRouting != null ? !this.methodRouting.equals(that.methodRouting) : that.methodRouting != null) {
                return false;
            }
            return !(this.produces != null ? !this.produces.equals(that.produces) : that.produces != null);
        }

        public int hashCode() {
            int result = this.consumes != null ? this.consumes.hashCode() : 0;
            result = 31 * result + (this.produces != null ? this.produces.hashCode() : 0);
            result = 31 * result + (this.methodRouting != null ? this.methodRouting.hashCode() : 0);
            return result;
        }
    }
}

