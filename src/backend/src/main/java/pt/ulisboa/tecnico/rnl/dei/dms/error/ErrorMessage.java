package pt.ulisboa.tecnico.rnl.dei.dms.error;

public enum ErrorMessage {
    // Evaluation
   ENROLLMENT_NOT_FOUND("Enrollment with id %s not found"),

    EVALUATION_NOT_FOUND("Evaluation for enrollment id %s not found"),

    EVALUATION_ALREADY_EXISTS("Evaluation already exists"),

    NO_ENROLLMENTS_FOUND("No enrollments found"),

    NO_WINNER_FOUND("No winner found"),

   // Fellowship
   FELLOWSHIP_NAME_REQUIRED("Fellowship name is mandatory"),
    FELLOWSHIP_NAME_SIZE("Fellowship name must be between 3 and 25 characters"),
    FELLOWSHIP_DESCRIPTION_SIZE("Fellowship description must be less than 255 characters"),
    FELLOWSHIP_START_DATE_REQUIRED("Start date is required"),
    FELLOWSHIP_END_DATE_REQUIRED("End date is required"),
    FELLOWSHIP_MONTHLY_VALUE_REQUIRED("Monthly value is required"),
    FELLOWSHIP_VALUE_MIN("Fellowship value must be greater than 0"),
   FELLOWSHIP_NOT_FOUND("Fellowship with id %s not found"),
    START_DATE_BEFORE_END_DATE("Start date must be before end date"),
    WRONG_WEIGHTS_SUM("Weights sum must be 1. Sum: %s"),
    // Candidate
    CANDIDATE_NOT_FOUND("Candidate with id %s not found"),
    IST_ID_REQUIRED("IST ID is required"),
    IST_ID_ALREADY_EXISTS("IST ID %s already exists"),
    NAME_REQUIRED("Name is required"),
    EMAIL_REQUIRED("Email is required"),
    INVALID_EMAIL("Invalid email: %s"),

    // Enrollment


    NO_SUCH_ENROLLMENT("No such enrollment");
    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}