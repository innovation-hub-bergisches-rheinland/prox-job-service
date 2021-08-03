package de.innovationhub.prox.jobservice.application.service;

import com.google.common.collect.Sets;
import de.innovationhub.prox.jobservice.domain.job.EntryLevel;
import de.innovationhub.prox.jobservice.domain.job.JobOffer;
import de.innovationhub.prox.jobservice.domain.job.JobOfferEntryLevel;
import de.innovationhub.prox.jobservice.domain.job.JobOfferEntryLevelRepository;
import de.innovationhub.prox.jobservice.domain.job.JobOfferRepository;
import de.innovationhub.prox.jobservice.domain.job.JobOfferType;
import de.innovationhub.prox.jobservice.domain.job.JobOfferTypeRepository;
import de.innovationhub.prox.jobservice.domain.job.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferService {

  private final JobOfferRepository jobOfferRepository;
  private final JobOfferTypeRepository jobOfferTypeRepository;
  private final JobOfferEntryLevelRepository jobOfferEntryLevelRepository;

  @Autowired
  public JobOfferService(
      JobOfferRepository jobOfferRepository,
      JobOfferTypeRepository jobOfferTypeRepository,
      JobOfferEntryLevelRepository jobOfferEntryLevelRepository) {
    this.jobOfferRepository = jobOfferRepository;
    this.jobOfferTypeRepository = jobOfferTypeRepository;
    this.jobOfferEntryLevelRepository = jobOfferEntryLevelRepository;
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
    return jobOfferRepository.findById(uuid)
        .map(jobOffer1 -> {
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

  public Set<JobOfferType> setJobOfferTypes(JobOffer jobOffer, UUID[] uuids) {
    var types = this.jobOfferTypeRepository.findAllByIdIn(uuids);
    jobOffer.setAvailableTypes(StreamSupport.stream(types.spliterator(), false).collect(Collectors.toSet()));
    return this.save(jobOffer).getAvailableTypes();
  }

  public Set<JobOfferEntryLevel> setJobOfferEntryLevels(JobOffer jobOffer, UUID[] uuids) {
    var types = this.jobOfferEntryLevelRepository.findAllByIdIn(uuids);
    jobOffer.setEntryLevels(StreamSupport.stream(types.spliterator(), false).collect(Collectors.toSet()));
    return this.save(jobOffer).getEntryLevels();
  }

  public void delete(JobOffer jobOffer) {
    this.deleteById(jobOffer.getId());
  }

  public boolean jobOfferExistsById(UUID id) {
    return this.jobOfferRepository.existsById(id);
  }

  public Set<JobOffer> findAllJobsByCreator(UUID creatorId) {
    return this.jobOfferRepository.findAllJobsByCreator(creatorId);
  }

  @Transactional
  public Set<JobOffer> searchJobOffers(String search, EntryLevel[] entryLevels, Type[] types) {
    var jobOffers = StreamSupport.stream(this.jobOfferRepository.findAll().spliterator(), false);

    if(entryLevels.length > 0) {
      jobOffers = jobOffers.filter(
          jobOffer -> jobOffer.getEntryLevels()
              .stream()
              .anyMatch(p1 ->
                  Arrays.stream(entryLevels).anyMatch(p2 -> p2.equals(p1.getEntryLevel()))));
    }

    if(types.length > 0) {
      jobOffers = jobOffers.filter(
          jobOffer -> jobOffer.getAvailableTypes()
              .stream()
              .anyMatch(p1 ->
                  Arrays.stream(types).anyMatch(p2 -> p2.equals(p1.getType()))));
    }

    if(search.length() > 0) {
      jobOffers = jobOffers.filter(
          jobOffer -> jobOffer.getTitle().toLowerCase().contains(search.toLowerCase()) || jobOffer.getDescription().toLowerCase().contains(search.toLowerCase()));
    }
    return jobOffers.collect(Collectors.toSet());
  }
}
