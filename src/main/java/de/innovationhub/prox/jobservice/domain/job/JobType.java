package de.innovationhub.prox.jobservice.domain.job;

public enum JobType {
  FULL_TIME("Vollzeit"),
  PART_TIME("Teilzeit"),
  INTERNSHIP("Ausbildung / Praktikum"),
  MINIJOB("Minijob"),
  STUDENT_WORKER("Studentische Hilfskraft / Werkstudent"),
  RESEARCH_ASSISTANT("Wissenschaftliche Hilfskraft");

  private String description;

  JobType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
