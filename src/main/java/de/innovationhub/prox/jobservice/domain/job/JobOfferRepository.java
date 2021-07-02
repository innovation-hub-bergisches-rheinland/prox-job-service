package de.innovationhub.prox.jobservice.domain.job;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends CrudRepository<JobOffer, UUID> {
  Set<JobOffer> findByAvailableTypesIn(JobType[] types);

  Set<JobOffer> findByEntryLevelsIn(JobEntryLevel[] jobEntryLevels);
}
