package de.innovationhub.prox.jobservice.domain.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public enum Type {
  FULL_TIME, // Vollzeit
  PART_TIME, // Teilzeit
  INTERNSHIP, // Ausbildung / Praktikum
  MINIJOB, // Minijob
  STUDENT_WORKER, // Studentische Hilfskraft / Werkstudent
  RESEARCH_ASSISTANT; // Wissenschaftliche Hilfskraft
}
