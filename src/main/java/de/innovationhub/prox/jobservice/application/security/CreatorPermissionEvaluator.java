package de.innovationhub.prox.jobservice.application.security;


import de.innovationhub.prox.jobservice.domain.job.JobOffer;
import de.innovationhub.prox.jobservice.domain.job.JobOfferRepository;
import java.io.Serializable;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreatorPermissionEvaluator implements PermissionEvaluator {

  private final JobOfferRepository jobOfferRepository;
  private final KeycloakUtils keycloakUtils;

  @Autowired
  public CreatorPermissionEvaluator(
      JobOfferRepository jobOfferRepository, KeycloakUtils keycloakUtils) {
    this.jobOfferRepository = jobOfferRepository;
    this.keycloakUtils = keycloakUtils;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    if (permission == null) {
      throw new IllegalArgumentException("Permission cannot be null");
    }
    if (targetDomainObject == null) {
      throw new IllegalArgumentException("Target domain object cannot be null");
    }
    if (!(permission instanceof String)) {
      throw new IllegalArgumentException("Permission must be a string");
    }
    var perm = (String) permission;

    if (perm.equalsIgnoreCase("read")) {
      log.debug("Read permission is granted");
      return true;
    }

    if (perm.equalsIgnoreCase("write")) {
      if (authentication != null) {
        if (targetDomainObject instanceof JobOffer) {
          log.debug("Checking write permission for JobOffer object");
          var createdBy = ((JobOffer) targetDomainObject).getCreatedBy();
          var authenticatedId = keycloakUtils.getIdFromAuthentication(authentication);
          if (authenticatedId.isPresent()) {
            var res = authenticatedId.get().equals(createdBy.getUserId());
            log.debug("Write permission result for user " + authentication + " is: " + res);
            return res;
          }
        }
      }
    }

    return false;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {
    if (targetId != null && targetId instanceof String) {
      var id = UUID.fromString((String) targetId);
      if (targetType != null && targetType.equalsIgnoreCase(JobOffer.class.getCanonicalName())) {
        var jobOffer = jobOfferRepository.findById(id);
        if (jobOffer.isPresent()) {
          return hasPermission(authentication, jobOffer.get(), permission);
        } else {
          log.info("No job offer with id " + id + " found");
        }
      }
    }
    return false;
  }
}
