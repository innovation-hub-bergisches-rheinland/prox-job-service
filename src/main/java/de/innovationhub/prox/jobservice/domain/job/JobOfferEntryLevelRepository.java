package de.innovationhub.prox.jobservice.domain.job;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferEntryLevelRepository extends CrudRepository<JobOfferEntryLevel, UUID> {

}
