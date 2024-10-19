package com.ncepu.easygift.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "obs.file")
@PropertySource(value = {"classpath:obs.properties"})
@Component
@Data
public class ObsProperties {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucketName;
}
