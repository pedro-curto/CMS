export default class FellowshipDto {
    id: number | null = null;
    name?: string
    description?: string
    startDate?: string
    endDate?: string
    monthlyValue?: number
    closed?: boolean
    winnerId?: number

    constructor(jsonObj: Partial<FellowshipDto>) {
        Object.assign(this, jsonObj)
    }
}