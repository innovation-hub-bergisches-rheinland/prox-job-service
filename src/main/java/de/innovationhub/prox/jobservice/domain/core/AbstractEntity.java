package de.innovationhub.prox.jobservice.domain.core;


import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
  @Id private final UUID id = UUID.randomUUID();

  public UUID getId() {
    return id;
  }
}
