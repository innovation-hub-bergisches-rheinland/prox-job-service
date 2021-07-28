package de.innovationhub.prox.jobservice.domain.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.innovationhub.prox.jobservice.domain.core.AbstractEntity;
import de.innovationhub.prox.jobservice.domain.core.Creator;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class JobOffer extends AbstractEntity {
  // Job offer title, for example: Fullstack developer Java
  @Size(min = 10, max = 255)
  @Column(nullable = false)
  @NotNull
  private String title;

  @Size(min = 50, max = 50000)
  @NotNull
  @Column(length = 50000, nullable = false)
  @Lob
  private String description;

  // Type of Employment
  @ManyToMany
  @JsonIgnore
  private Set<JobOfferType> availableTypes = new HashSet<>();

  // Entry Levels
  @ManyToMany
  @JsonIgnore
  private Set<JobOfferEntryLevel> entryLevels = new HashSet<>();

  @Temporal(TemporalType.TIMESTAMP)
  private Date earliestStartOfEmployment;

  public JobOffer(String title, String description) {
    this.setTitle(title);
    this.setDescription(description);
  }

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  @Column(updatable = false)
  private Date createdAt = Date.from(Instant.now());

  @Embedded
  @CreatedBy
  @Column(updatable = false)
  private Creator createdBy;
}
