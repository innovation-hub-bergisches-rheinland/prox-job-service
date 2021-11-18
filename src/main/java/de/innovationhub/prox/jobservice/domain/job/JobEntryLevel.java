package de.innovationhub.prox.jobservice.domain.job;

public enum JobEntryLevel {
  CAREER_STARTER("Berufseinsteiger"),
  EXPERIENCED("Berufserfahrene");

  private String description;

  JobEntryLevel(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
