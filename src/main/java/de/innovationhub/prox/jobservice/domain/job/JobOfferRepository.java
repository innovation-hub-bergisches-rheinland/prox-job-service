package de.innovationhub.prox.jobservice.domain.job;

import de.innovationhub.prox.jobservice.domain.core.Creator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends CrudRepository<JobOffer, UUID> {


  Set<JobOffer> findByAvailableTypesIn(Type[] types);

  Set<JobOffer> findByEntryLevelsIn(EntryLevel[] entryLevels);

  @Query("select j.createdBy from JobOffer j where j.id = id")
  Optional<Creator> findCreatorOfJobOffer(UUID id);
}
