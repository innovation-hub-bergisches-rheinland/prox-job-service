package de.innovationhub.prox.jobservice.domain.job;


import de.innovationhub.prox.jobservice.domain.core.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobOfferType extends AbstractEntity {
  @NotNull private Type type;

  @NotBlank
  @Size(max = 255)
  private String description;
}
