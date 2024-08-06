export default class CandidateDto {
    id: number | null = null;
    istId?: number
    name?: string
    email?: string

    constructor(jsonObj: Partial<CandidateDto>) {
        Object.assign(this, jsonObj)
    }
}
