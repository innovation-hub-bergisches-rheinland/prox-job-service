package de.innovationhub.prox.jobservice.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

  private final String version;

  public SpringdocConfig(@Value("${spring.application.version:unknown}") String version) {
    this.version = version;
  }

  @Bean
  public GroupedOpenApi userOpenApi() {
    String paths[] = {"/**"};
    return GroupedOpenApi.builder().group("job-service").pathsToMatch(paths).build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addServersItem(
            new Server()
                .url("https://api.prox.innovation-hub.de")
                .description("PROX Production API"))
        .addServersItem(
            new Server()
                .url("https://dev.api.prox.innovation-hub.de")
                .description("PROX Development API"))
        .info(
            new Info()
                .title("PROX Job Service")
                .version("0.1.0")
                .description(
                    "This Service is part of [PROX](https://prox.innovation-hub.de/)"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "Bearer",
                    new SecurityScheme()
                        .description("Required Roles: `professor` or `company-manager`")
                        .type(Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(
                    "OIDC",
                    new SecurityScheme()
                        .type(Type.OPENIDCONNECT)
                        .openIdConnectUrl(
                            "https://login.archi-lab.io/auth/realms/archilab/.well-known/openid-configuration")));
  }
}

