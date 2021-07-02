package de.innovationhub.prox.jobservice.domain.job;

import de.innovationhub.prox.jobservice.domain.core.AbstractEntity;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
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
  @NotNull
  private String title;

  @Size(min = 50, max = 10000)
  @NotNull
  @Column(length = 10000)
  @Lob
  private String description;

  // Type of Employment
  @ElementCollection
  @Enumerated
  private Set<JobType> availableTypes = new HashSet<>();

  // Entry Levels
  @ElementCollection
  @Enumerated
  private Set<JobEntryLevel> entryLevels = new HashSet<>();

  public JobOffer(String title, String description) {
    this.setTitle(title);
    this.setDescription(description);
  }

  public JobOffer(String title, String description, Set<JobType> availableTypes) {
    this(title, description);
    this.setAvailableTypes(availableTypes);
  }

  public JobOffer(String title, String description, Set<JobType> availableTypes, Set<JobEntryLevel> entryLevels) {
    this(title, description, availableTypes);
    this.setEntryLevels(entryLevels);
  }

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt = Date.from(Instant.now());

  @CreatedBy
  private UUID createdBy;
}
