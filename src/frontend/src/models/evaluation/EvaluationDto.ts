export default class EvaluationDto {
    id: number | null = null;
    enrollmentId: number | null = null;
    scores: number[] = [];

    constructor(jsonObj: Partial<EvaluationDto>) {
        Object.assign(this, jsonObj)
    }
}