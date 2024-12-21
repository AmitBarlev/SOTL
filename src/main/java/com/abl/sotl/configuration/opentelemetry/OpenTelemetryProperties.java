package com.abl.sotl.configuration.opentelemetry;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("open-telemetry")
public class OpenTelemetryProperties {

    private String serviceName;
    private String collectorUrl;
}
