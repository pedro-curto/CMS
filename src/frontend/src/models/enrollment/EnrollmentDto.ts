export default class EnrollmentDto {
    id: number | null = null;
    motivation?: string
    enrollmentDateTime?: string
    fellowshipId?: number | null = null
    candidateId?: number | null = null

    constructor(jsonObj: Partial<EnrollmentDto>) {
        Object.assign(this, jsonObj)
    }
}
