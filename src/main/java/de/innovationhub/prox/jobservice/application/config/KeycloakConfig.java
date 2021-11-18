package de.innovationhub.prox.jobservice.application.config;


import de.innovationhub.prox.jobservice.application.security.CreatorPermissionEvaluator;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final CreatorPermissionEvaluator creatorPermissionEvaluator;

  @Autowired
  public KeycloakConfig(CreatorPermissionEvaluator creatorPermissionEvaluator) {
    this.creatorPermissionEvaluator = creatorPermissionEvaluator;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        this.keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Bean
  public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
    var handler = new DefaultWebSecurityExpressionHandler();
    handler.setPermissionEvaluator(this.creatorPermissionEvaluator);
    return handler;
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .expressionHandler(expressionHandler())
        .antMatchers(HttpMethod.GET, "/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/jobOffers/**")
        .access("hasAnyRole('professor', 'company-manager')")
        .antMatchers(HttpMethod.PUT, "/jobOffers/{id}/**")
        .access(
            "hasAnyRole('professor', 'company-manager') and hasPermission(#id, 'de.innovationhub.prox.jobservice.domain.job.JobOffer', 'write')")
        .antMatchers(HttpMethod.DELETE, "/jobOffers/{id}/**")
        .access(
            "hasAnyRole('professor', 'company-manager') and hasPermission(#id, 'de.innovationhub.prox.jobservice.domain.job.JobOffer', 'write')")
        .anyRequest()
        .denyAll();
  }
}
