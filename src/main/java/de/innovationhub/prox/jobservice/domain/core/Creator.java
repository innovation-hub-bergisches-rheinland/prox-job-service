package de.innovationhub.prox.jobservice.domain.core;


import java.util.UUID;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator {
  private UUID userId;
  private UserVariant variant;

  public Creator(UUID userId, UserVariant variant) {
    this.userId = userId;
    this.variant = variant;
  }
}
