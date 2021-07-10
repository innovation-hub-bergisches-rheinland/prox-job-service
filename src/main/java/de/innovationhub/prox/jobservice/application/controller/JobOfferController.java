package de.innovationhub.prox.jobservice.application.controller;

import de.innovationhub.prox.jobservice.application.service.JobOfferService;
import de.innovationhub.prox.jobservice.domain.job.JobOffer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("jobOffers")
public class JobOfferController {

  private final JobOfferService jobOfferService;

  @Autowired
  public JobOfferController(
      JobOfferService jobOfferService) {
    this.jobOfferService = jobOfferService;
  }

  @GetMapping
  public ResponseEntity<Set<JobOffer>> getAll() {
    return ResponseEntity.ok(jobOfferService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<JobOffer> getById(@PathVariable UUID id) {
    return jobOfferService.findById(id)
        .map(jobOffer -> ResponseEntity.ok(jobOffer))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @Operation(summary = "create a new job offer", security = @SecurityRequirement(name = "Bearer"))
  public ResponseEntity<JobOffer> save(@Valid @RequestBody JobOffer jobOffer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.save(jobOffer));
  }

  @PutMapping("{id}")
  @Operation(summary = "edit an existing job offer", security = @SecurityRequirement(name = "Bearer"))
  public ResponseEntity<JobOffer> update(@PathVariable UUID id, @Valid @RequestBody JobOffer jobOffer) {
    return this.jobOfferService.update(id, jobOffer)
        .map(jobOffer1 -> ResponseEntity.ok(jobOffer1))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{id}")
  @Operation(summary = "delete an existing job offer", security = @SecurityRequirement(name = "Bearer"))
  public void delete(@PathVariable UUID id) {
    this.jobOfferService.deleteById(id);
  }
}
