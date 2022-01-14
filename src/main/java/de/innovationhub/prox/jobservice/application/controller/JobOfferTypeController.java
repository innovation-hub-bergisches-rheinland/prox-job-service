package de.innovationhub.prox.jobservice.application.controller;


import de.innovationhub.prox.jobservice.domain.job.JobOfferType;
import de.innovationhub.prox.jobservice.domain.job.JobOfferTypeRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("jobOfferTypes")
public class JobOfferTypeController {

  private final JobOfferTypeRepository jobOfferTypeRepository;

  @Autowired
  public JobOfferTypeController(JobOfferTypeRepository jobOfferTypeRepository) {
    this.jobOfferTypeRepository = jobOfferTypeRepository;
  }

  @GetMapping
  public ResponseEntity<List<JobOfferType>> getAll() {
    return ResponseEntity.ok(
        StreamSupport.stream(jobOfferTypeRepository.findAll().spliterator(), false)
            .collect(Collectors.toList()));
  }

  @GetMapping("{id}")
  public ResponseEntity<JobOfferType> getById(@PathVariable UUID id) {
    return this.jobOfferTypeRepository
        .findById(id)
        .map(j -> ResponseEntity.ok(j))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
