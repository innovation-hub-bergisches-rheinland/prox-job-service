package de.innovationhub.prox.jobservice.application.controller;

import de.innovationhub.prox.jobservice.domain.job.JobOfferEntryLevel;
import de.innovationhub.prox.jobservice.domain.job.JobOfferEntryLevelRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.keycloak.common.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("jobOfferEntryLevels")
public class JobOfferEntryLevelController {

  private final JobOfferEntryLevelRepository jobOfferEntryLevelRepository;

  @Autowired
  public JobOfferEntryLevelController(JobOfferEntryLevelRepository jobOfferEntryLevelRepository) {
    this.jobOfferEntryLevelRepository = jobOfferEntryLevelRepository;
  }

  @GetMapping
  public ResponseEntity<List<JobOfferEntryLevel>> getAll() {
    return ResponseEntity.ok(StreamSupport.stream(jobOfferEntryLevelRepository.findAll().spliterator(), false).collect(
        Collectors.toList()));
  }

  @GetMapping("{id}")
  public ResponseEntity<JobOfferEntryLevel> getById(@PathVariable UUID id) {
    return this.jobOfferEntryLevelRepository.findById(id)
        .map(j -> ResponseEntity.ok(j))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
