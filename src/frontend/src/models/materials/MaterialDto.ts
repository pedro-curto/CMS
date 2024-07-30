export default class MaterialDto {
  id?: string
  name?: string
  type?: string
  available?: boolean

  constructor(jsonObj: Partial<MaterialDto>) {
    Object.assign(this, jsonObj)
  }
}
