import axios from 'axios'

import MaterialDto from '@/models/materials/MaterialDto'
import CandidateDto from '@/models/candidate/CandidateDto';
import FellowshipDto from '@/models/fellowship/FellowshipDto';
import EnrollmentDto from "@/models/enrollment/EnrollmentDto";
import EvaluationDto from '@/models/evaluation/EvaluationDto';

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

  static async getCandidateById(candidateId: number): Promise<CandidateDto> {
    return httpClient.get(`/candidates/get/${candidateId}`).then((response) => {
      return new CandidateDto(response.data)
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
    return httpClient.delete(`/fellowships/delete/${fellowshipId}`).then((response) => {
      return new FellowshipDto(response)
    })
  }

  static async updateFellowshipWeights(fellowshipId: number, weights: { [key: string]: number }): Promise<void> {
    return httpClient.put(`/fellowships/updateWeights/${fellowshipId}`, weights).then((response) => {
      return response.data;
    });
  }

  // ------------------- Enrollments -------------------

  static async enrollCandidate(enrollmentDto: EnrollmentDto): Promise<EnrollmentDto> {
    return httpClient.post('/enrollments/enroll', enrollmentDto).then((response) => {
      return new EnrollmentDto(response.data)
    })
  }

  static async unenrollCandidate(fellowshipId: number, candidateId: number): Promise<EnrollmentDto> {
    return httpClient.delete(`/enrollments/unenroll`,
        { params: { fellowshipId, candidateId}})
        .then((response) => {
          return new EnrollmentDto(response.data)
        })
  }

  static async deleteEnrollment(enrollmentId: number): Promise<EnrollmentDto> {
    return httpClient.delete(`/enrollments/delete/${enrollmentId}`)
        .then((response) => {
          return new EnrollmentDto(response.data)
        });
  }

  static async getEnrolledCandidatesByFellowship(fellowshipId: number): Promise<CandidateDto[]> {
    return httpClient.get(`/enrollments/fellowships/${fellowshipId}/candidates`).then((response) => {
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate)
      })
    })
  }

  static async getCandidateFellowships(candidateId: number): Promise<FellowshipDto[]> {
    return httpClient.get(`/enrollments/candidates/${candidateId}/fellowships`).then((response) => {
      return response.data.map((fellowship: any) => {
        return new FellowshipDto(fellowship)
      })
    })
  }

  static async getFellowshipEnrollments(fellowshipId: number): Promise<EnrollmentDto[]> {
    return httpClient.get(`/enrollments/fellowships/${fellowshipId}`).then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment)
      })
    })
  }EnrollmentDto

  static async getEnrollmentId(fellowshipId: number, candidateId: number): Promise<number> {
    console.log(fellowshipId, candidateId)
    return httpClient.get(`/enrollments/getId`,
        { params: { fellowshipId, candidateId}})
        .then((response) => {
          return response.data
        })
  }

    // ------------------- Evaluations -------------------

  static async createEvaluation(evaluation: EvaluationDto): Promise<EvaluationDto> {
    return httpClient.post('/evaluations/create', evaluation).then((response) => {
      return new EvaluationDto(response.data)
    })
  }

  static async getEvaluationDetails(enrollmentId: number): Promise<EvaluationDto> {
    return httpClient.get(`/evaluations/getDetails/${enrollmentId}`).then((response) => {
      return new EvaluationDto(response.data)
    })
  }

  static async getEvaluationCategories(): Promise<string[]> {
    return httpClient.get('/evaluations/getCategories').then((response) => {
      return response.data
    })
  }

  static async getEvaluationWeights(fellowshipId: number): Promise<number[]> {
    console.log(fellowshipId)
    return httpClient.get(`/evaluations/getWeights/${fellowshipId}`).then((response) => {
      return response.data
    })
  }

  static async getCandidateFinalEvaluation(enrollmentId: number): Promise<Double> {
    return httpClient.get(`/evaluations/getFinalEvaluation/${enrollmentId}`).then((response) => {
        return response.data
    })
  }

}
