package com.abl.sotl.configuration.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import io.opentelemetry.instrumentation.lettuce.v5_1.LettuceTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenTelemetryConfiguration {

    private final OpenTelemetryProperties properties;

    private final static String SERVICE_NAME = "service.name";


    @Bean
    public Resource openTelemetryResource() {
        return Resource.builder()
                .put(AttributeKey.stringKey(SERVICE_NAME), properties.getServiceName())
                .build();
    }

    @Bean
    public BatchSpanProcessor spanProcessor() {
        OtlpGrpcSpanExporter export = OtlpGrpcSpanExporter.builder()
                .setEndpoint(properties.getCollectorUrl())
                .build();

        return BatchSpanProcessor.builder(export)
                .build();
    }

    @Bean
    public SdkTracerProvider tracerProvider(Resource resource,
                                            BatchSpanProcessor spanProcessor) {
        return SdkTracerProvider.builder()
                .addResource(resource)
                .addSpanProcessor(spanProcessor)
                .build();
    }

    @Bean
    public OpenTelemetry openTelemetry(SdkTracerProvider tracerProvider) {
        ContextPropagators propagators = ContextPropagators.create(
                TextMapPropagator.composite(
                        W3CTraceContextPropagator.getInstance(),
                        JaegerPropagator.getInstance()
                )
        );


        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .setPropagators(propagators)
                .buildAndRegisterGlobal();
    }

    @Bean
    public LettuceTelemetry lettuceTelemetry(OpenTelemetry telemetry) {
        return LettuceTelemetry.create(telemetry);
    }
}
