package de.innovationhub.prox.jobservice.application.controller;


import de.innovationhub.prox.jobservice.application.service.JobOfferService;
import de.innovationhub.prox.jobservice.domain.job.EntryLevel;
import de.innovationhub.prox.jobservice.domain.job.JobOffer;
import de.innovationhub.prox.jobservice.domain.job.JobOfferEntryLevel;
import de.innovationhub.prox.jobservice.domain.job.JobOfferType;
import de.innovationhub.prox.jobservice.domain.job.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("jobOffers")
public class JobOfferController {

  private final JobOfferService jobOfferService;

  @Autowired
  public JobOfferController(JobOfferService jobOfferService) {
    this.jobOfferService = jobOfferService;
  }

  @GetMapping
  public ResponseEntity<Set<JobOffer>> getAll() {
    return ResponseEntity.ok(jobOfferService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<JobOffer> getById(@PathVariable UUID id) {
    return jobOfferService
        .findById(id)
        .map(jobOffer -> ResponseEntity.ok(jobOffer))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @GetMapping("{id}/entryLevels")
  public ResponseEntity<List<JobOfferEntryLevel>> getEntryLevels(@PathVariable UUID id) {
    return jobOfferService
        .findById(id)
        .map(
            jobOffer ->
                ResponseEntity.ok(jobOffer.getEntryLevels().stream().collect(Collectors.toList())))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PutMapping("{id}/entryLevels")
  public ResponseEntity<List<JobOfferEntryLevel>> setEntryLevels(
      @PathVariable UUID id, @RequestBody UUID[] uuids) {
    return jobOfferService
        .findById(id)
        .map(
            jobOffer ->
                ResponseEntity.ok(
                    this.jobOfferService.setJobOfferEntryLevels(jobOffer, uuids).stream()
                        .collect(Collectors.toList())))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @GetMapping("{id}/types")
  public ResponseEntity<List<JobOfferType>> getTypes(@PathVariable UUID id) {
    return jobOfferService
        .findById(id)
        .map(
            jobOffer ->
                ResponseEntity.ok(
                    jobOffer.getAvailableTypes().stream().collect(Collectors.toList())))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PutMapping("{id}/types")
  public ResponseEntity<List<JobOfferType>> setTypes(
      @PathVariable UUID id, @RequestBody UUID[] uuids) {
    return jobOfferService
        .findById(id)
        .map(
            jobOffer ->
                ResponseEntity.ok(
                    this.jobOfferService.setJobOfferTypes(jobOffer, uuids).stream()
                        .collect(Collectors.toList())))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @Operation(summary = "create a new job offer", security = @SecurityRequirement(name = "Bearer"))
  public ResponseEntity<JobOffer> save(@Valid @RequestBody JobOffer jobOffer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.save(jobOffer));
  }

  @PutMapping("{id}")
  @Operation(
      summary = "edit an existing job offer",
      security = @SecurityRequirement(name = "Bearer"))
  public ResponseEntity<JobOffer> update(
      @PathVariable UUID id, @Valid @RequestBody JobOffer jobOffer) {
    return this.jobOfferService
        .update(id, jobOffer)
        .map(jobOffer1 -> ResponseEntity.ok(jobOffer1))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{id}")
  @Operation(
      summary = "delete an existing job offer",
      security = @SecurityRequirement(name = "Bearer"))
  public void delete(@PathVariable UUID id) {
    if (this.jobOfferService.jobOfferExistsById(id)) {
      this.jobOfferService.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("search/findJobOffers")
  public ResponseEntity<List<JobOffer>> searchJobOffers(
      @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "") EntryLevel[] entryLevels,
      @RequestParam(defaultValue = "") Type[] types) {
    return ResponseEntity.ok(
        this.jobOfferService.searchJobOffers(search, entryLevels, types).stream()
            .collect(Collectors.toList()));
  }

  @GetMapping("search/findAllJobsByCreator")
  public ResponseEntity<List<JobOffer>> findAllJobsByCreator(
      @RequestParam(required = true, name = "creator") UUID creatorId) {
    return ResponseEntity.ok(
        this.jobOfferService.findAllJobsByCreator(creatorId).stream().collect(Collectors.toList()));
  }
}
