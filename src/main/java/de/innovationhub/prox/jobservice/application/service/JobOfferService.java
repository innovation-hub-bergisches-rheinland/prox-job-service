package de.innovationhub.prox.jobservice.application.service;


import com.google.common.collect.Sets;
import de.innovationhub.prox.jobservice.domain.job.JobOffer;
import de.innovationhub.prox.jobservice.domain.job.JobOfferRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferService {

  private final JobOfferRepository jobOfferRepository;

  @Autowired
  public JobOfferService(JobOfferRepository jobOfferRepository) {
    this.jobOfferRepository = jobOfferRepository;
  }

  public Optional<JobOffer> findById(UUID id) {
    return jobOfferRepository.findById(id);
  }

  public Set<JobOffer> findAll() {
    return Sets.newHashSet(jobOfferRepository.findAll());
  }

  public JobOffer save(JobOffer jobOffer) {
    return jobOfferRepository.save(jobOffer);
  }

  public Optional<JobOffer> update(UUID uuid, JobOffer jobOffer) {
    return jobOfferRepository
        .findById(uuid)
        .map(
            jobOffer1 -> {
              jobOffer1.setAvailableTypes(jobOffer.getAvailableTypes());
              jobOffer1.setDescription(jobOffer.getDescription());
              jobOffer1.setTitle(jobOffer.getTitle());
              jobOffer1.setEntryLevels(jobOffer.getEntryLevels());
              return jobOfferRepository.save(jobOffer1);
            });
  }

  public void deleteById(UUID id) {
    jobOfferRepository.deleteById(id);
  }

  public void delete(JobOffer jobOffer) {
    this.deleteById(jobOffer.getId());
  }

  private void authorize(JobOffer jobOffer) {}
}
