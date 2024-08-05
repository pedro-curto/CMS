package pt.ulisboa.tecnico.rnl.dei.dms.error;

public enum ErrorMessage {
    // Evaluation
   ENROLLMENT_NOT_FOUND("Enrollment with id %s not found"),

    EVALUATION_NOT_FOUND("Evaluation for enrollment id %s not found"),

    EVALUATION_ALREADY_EXISTS("Evaluation already exists"),

    FELLOWSHIP_NOT_FOUND("Fellowship with id %s not found"),

    NO_ENROLLMENTS_FOUND("No enrollments found"),

    NO_WINNER_FOUND("No winner found"),

   // Fellowship

    // Candidate

    CANDIDATE_NOT_FOUND("Candidate with id %s not found")

    // Enrollment


    ;
    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}