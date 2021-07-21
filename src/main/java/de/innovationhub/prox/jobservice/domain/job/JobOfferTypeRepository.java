package de.innovationhub.prox.jobservice.domain.job;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferTypeRepository extends CrudRepository<JobOfferType, UUID> {
  Iterable<JobOfferType> findAllByIdIn(UUID[] ids);
}
