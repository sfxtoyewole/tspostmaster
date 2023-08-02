package com.ts.postmaster.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author toyewole
 */

@ConfigurationProperties("app.info")
@Component
@Data
public class ApiInfo {
    private String title;
    private String description;
    private String version;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
}
