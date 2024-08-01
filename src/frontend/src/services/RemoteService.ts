import axios from 'axios'

import MaterialDto from '@/models/materials/MaterialDto'
import CandidateDto from '@/models/candidate/CandidateDto';

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class RemoteService {

  // ------------------- Materials -------------------
  static async getMaterials(): Promise<MaterialDto[]> {
    return httpClient.get('/materials/all').then((response) => {
      return response.data.map((material: any) => {
        return new MaterialDto(material)
      })
    })
  }

  static async createMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.post('/materials/create', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async updateMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.put('/materials/update', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async deleteMaterial(material: MaterialDto): Promise<void> {
    return httpClient.delete(`/materials/delete/${material.id}`)
  }

  static async getCandidates() {
    return httpClient.get('/candidates/getAll').then((response) => {
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate)
      })
    })
  }

    static async addCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.post('/candidates/add', candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  static async updateCandidate(candidateId: number, candidate: CandidateDto): Promise<CandidateDto> {
    console.log(candidate)
    return httpClient.put(`/candidates/update/${candidateId}`, candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }
}
