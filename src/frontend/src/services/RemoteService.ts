import axios from 'axios'

import MaterialDto from '@/models/materials/MaterialDto'
import CandidateDto from '@/models/candidate/CandidateDto';
import FellowshipDto from '@/models/fellowship/FellowshipDto';

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

  // ------------------- Candidates -------------------

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
    return httpClient.put(`/candidates/update/${candidateId}`, candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  static async deleteCandidate(candidateId: number): Promise<CandidateDto> {
    return httpClient.delete(`/candidates/delete/${candidateId}`, candidateId).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  // ------------------- Fellowships -------------------

  static async getFellowships() {
    return httpClient.get('/fellowships/getAll').then((response) => {
      return response.data.map((fellowship: any) => {
        return new FellowshipDto(fellowship)
      })
    })
  }

  static async getFellowshipById(fellowshipId: number): Promise<FellowshipDto> {
    return httpClient.get(`/fellowships/get/${fellowshipId}`).then((response) => {
        return new FellowshipDto(response.data)
    })
  }

  static async addFellowship(fellowship: FellowshipDto): Promise<FellowshipDto> {
    return httpClient.post('/fellowships/add', fellowship).then((response) => {
      return new FellowshipDto(response.data)
    })
  }

  static async updateFellowship(fellowshipId: number, fellowship: FellowshipDto): Promise<FellowshipDto> {
    return httpClient.put(`/fellowships/update/${fellowshipId}`, fellowship).then((response) => {
      return new FellowshipDto(response.data)
    })
  }

  static async deleteFellowship(fellowshipId: number): Promise<FellowshipDto> {
    return httpClient.delete(`/fellowships/delete/${fellowshipId}`, fellowshipId).then((response) => {
      return new FellowshipDto(response.data)
    })
  }

}
