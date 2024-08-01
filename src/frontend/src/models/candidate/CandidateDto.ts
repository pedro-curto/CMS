export default class CandidateDto {
    id?: string
    istId?: string
    name?: string
    email?: string

    constructor(jsonObj: Partial<CandidateDto>) {
        Object.assign(this, jsonObj)
    }
}
