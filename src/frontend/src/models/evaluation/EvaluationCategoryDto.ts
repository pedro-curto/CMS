export default class EvaluationDto {
    id: number | null = null;
    name: string;

    constructor(jsonObj: Partial<EvaluationDto>) {
        Object.assign(this, jsonObj)
    }
}