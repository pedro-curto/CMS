import Candidate from '@models/candidate/Candidate'

export default class FellowshipDto {
    id: number | null = null;
    name?: string
    description?: string
    startDate?: string
    endDate?: string
    monthlyValue?: number
    candidates?: Candidate[] = []

    constructor(jsonObj: Partial<FellowshipDto>) {
        Object.assign(this, jsonObj)
    }
}