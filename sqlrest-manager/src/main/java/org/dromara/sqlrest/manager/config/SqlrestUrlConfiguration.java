package org.dromara.sqlrest.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Used for external configuration, configuring gateway and manager service addresses. External environments usually have nginx or other reverse proxy services
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sqlrest.url")
public class SqlrestUrlConfiguration {

  private String gateway = "";
  private String manager = "";
}
