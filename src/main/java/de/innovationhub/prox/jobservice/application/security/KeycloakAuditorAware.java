package de.innovationhub.prox.jobservice.application.security;


import de.innovationhub.prox.jobservice.domain.core.Creator;
import de.innovationhub.prox.jobservice.domain.core.UserVariant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class KeycloakAuditorAware implements AuditorAware<Creator> {

  @Override
  public Optional<Creator> getCurrentAuditor() {
    var id = getIdFromSecurityContext();
    var variant = getVariantFromSecurityContext();

    if (id.isPresent()) {
      if (variant.isPresent()) {
        return Optional.of(new Creator(id.get(), variant.get()));
      } else {
        return Optional.of(new Creator(id.get(), UserVariant.UNKNOWN));
      }
    }
    return Optional.empty();
  }

  private Optional<UUID> getIdFromSecurityContext() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(KeycloakAuthenticationToken.class::cast)
        .map(KeycloakAuthenticationToken::getAccount)
        .map(OidcKeycloakAccount::getKeycloakSecurityContext)
        .map(KeycloakSecurityContext::getToken)
        .map(AccessToken::getSubject)
        .map(UUID::fromString);
  }

  private Optional<UserVariant> getVariantFromSecurityContext() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(KeycloakAuthenticationToken.class::cast)
        .map(KeycloakAuthenticationToken::getAuthorities)
        .map(
            authorities -> {
              var roles =
                  authorities.stream()
                      .map(a -> a.getAuthority().toLowerCase(Locale.ROOT).trim())
                      .collect(Collectors.toSet());
              if (roles.contains("role_professor")) {
                return UserVariant.PROFESSOR;
              } else if (roles.contains("role_company-manager")) {
                return UserVariant.COMPANY;
              }
              return UserVariant.UNKNOWN;
            });
  }
}
