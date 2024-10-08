<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Candidates Management for Fellowship {{ fellowship?.name }}</h1>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <v-text-field v-model="searchQuery" label="Search by Name" clearable></v-text-field>
      </v-col>
    </v-row>

    <!-- Candidates Section -->
    <v-row>
      <v-col v-if="!loading" v-for="candidate in paginatedCandidates" :key="candidate.id" cols="12" sm="6" md="4">
        <v-card class="candidate-card" outlined @click="goToCandidateDetails(candidate.id)">
          <v-card-title>
            <v-avatar size="56" class="mr-3">
              <v-img :src="getAvatarUrl(candidate.id)" alt="Profile picture"></v-img>
            </v-avatar>
            <span>{{ candidate.name }}</span>
          </v-card-title>
          <v-card-subtitle>IST{{ candidate.istId }}</v-card-subtitle>
          <v-card-text>
            <v-chip color="primary" text-color="white">{{ candidate.email }}</v-chip>
          </v-card-text>
          <v-btn v-if="!isEnrolled(candidate.id) && !fellowship.closed" color="success" @click.stop="enrollCandidate(candidate.id)">Enroll</v-btn>
          <v-btn v-if="isEnrolled(candidate.id) && !fellowship.closed" color="error" @click.stop="unenrollCandidate(candidate.id)">Unenroll</v-btn>
        </v-card>
      </v-col>
    </v-row>
    <!-- controls-->
    <v-row>
      <v-col>
        <v-pagination v-model="currentPage" :length="totalPages"></v-pagination>
      </v-col>
    </v-row>

    <v-card-actions>
      <v-btn color="primary" @click="goBack">Back</v-btn>
    </v-card-actions>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import { useRoute } from 'vue-router'
import router from "@/router";
import type EnrollmentDto from "@/models/enrollment/EnrollmentDto"

const route = useRoute()
const loading = ref(true)
const fellowship = ref<FellowshipDto | null>(null)
const enrolledCandidates = ref<CandidateDto[]>([])
const allCandidates = ref<CandidateDto[]>([])
const fellowshipEnrollments = ref<EnrollmentDto[]>([])
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 10

const totalPages = computed(() => Math.ceil(filteredCandidates.value.length / itemsPerPage))

const filteredCandidates = computed(() => {
  if (searchQuery.value) {
    return allCandidates.value.filter(candidate => candidate.name.toString().includes(searchQuery.value))
  }
  return allCandidates.value
})

const paginatedCandidates = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filteredCandidates.value.slice(start, end)
})

async function fetchFellowshipAndCandidates() {
  const fellowshipId = Number(route.params.id)
  try {
    fellowship.value = await RemoteService.getFellowshipById(fellowshipId)
    allCandidates.value = await RemoteService.getCandidates()
    enrolledCandidates.value = await RemoteService.getEnrolledCandidatesByFellowship(fellowshipId)
    fellowshipEnrollments.value = await RemoteService.getFellowshipEnrollments(fellowshipId)
  } catch (error) {
    console.error('Failed to fetch fellowship or candidates:', error)
  } finally {
    loading.value = false
  }
}

async function enrollCandidate(candidateId: number) {
  const enrollmentDto = {
    motivation: '',
    enrollmentDateTime: new Date().toISOString(),
    fellowshipId: fellowship.value?.id,
    candidateId: candidateId
  }
  try {
    if (fellowship.value) {
      const newEnrollment = await RemoteService.enrollCandidate(enrollmentDto)
      enrolledCandidates.value.push(allCandidates.value.find(candidate => candidate.id === candidateId)!)
      fellowshipEnrollments.value.push(newEnrollment)
    }
  } catch (err) {
    console.error('Failed to enroll candidate:', err)
  }
}

async function unenrollCandidate(candidateId: number) {
  try {
    if (fellowship.value) {
      const enrollment = fellowshipEnrollments.value.find(enrollment => enrollment.candidateId === candidateId)
      if (enrollment) {
        await RemoteService.deleteEnrollment(enrollment.id)
        enrolledCandidates.value = enrolledCandidates.value.filter(candidate => candidate.id !== candidateId)
        fellowshipEnrollments.value = fellowshipEnrollments.value.filter(e => e.id !== enrollment.id)
      }
    }
  } catch (err) {
    console.error('Failed to unenroll candidate:', err)
  }
}

function isEnrolled(candidateId: number): boolean {
  if (fellowship.value) {
    return enrolledCandidates.value.some(candidate => candidate.id === candidateId)
  }
  return false
}

function goToCandidateDetails(candidateId: number) {
  router.push({ name: 'candidateDetails', params: { id: candidateId } })
}

function goBack() {
  router.back()
}

onMounted(() => {
  fetchFellowshipAndCandidates()
})

function getAvatarUrl(candidateId: string | undefined): string {
  const baseUrl = 'https://ui-avatars.com/api/';
  const defaultText = 'Unknown';
  const size = 56;
  if (candidateId) {
    return `${baseUrl}?name=${candidateId}&size=${size}&background=random`;
  }
  return `${baseUrl}?name=${defaultText}&size=${size}&background=random`;
}
</script>
