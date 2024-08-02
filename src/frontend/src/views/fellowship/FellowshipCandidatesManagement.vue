<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Candidates Management for Fellowship {{ fellowship?.name }}</h1>
      </v-col>
    </v-row>

    <!-- Candidates Section -->
    <v-row>
      <v-col v-if="!loading" v-for="candidate in allCandidates" :key="candidate.id" cols="12" sm="6" md="4">
        <v-card class="candidate-card" outlined>
          <v-card-title>
            <v-avatar size="56" class="mr-3">
              <v-img :src="getAvatarUrl(candidate.id)" alt="Profile picture"></v-img>
            </v-avatar>
            <span>{{ candidate.name }}</span>
          </v-card-title>
          <v-card-subtitle>{{ candidate.istId }}</v-card-subtitle>
          <v-card-text>
            <v-chip color="primary" text-color="white">{{ candidate.email }}</v-chip>
          </v-card-text>
          <v-btn v-if="!isEnrolled(candidate.id)" color="success" @click="enrollCandidate(candidate.id)">Enroll</v-btn>
          <v-btn v-if="isEnrolled(candidate.id)" color="error" @click="unenrollCandidate(candidate.id)">Unenroll</v-btn>
        </v-card>
      </v-col>
    </v-row>
    <v-card-actions>
      <v-btn color="primary" @click="goBack">Back</v-btn>
    </v-card-actions>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import { useRoute } from 'vue-router'
import router from "@/router";

const route = useRoute()
const loading = ref(true)
const fellowship = ref<FellowshipDto | null>(null)
const enrolledCandidates = ref<CandidateDto[]>([])
const allCandidates = ref<CandidateDto[]>([])

async function fetchFellowshipAndCandidates() {
  const fellowshipId = Number(route.params.id)
  try {
    fellowship.value = await RemoteService.getFellowshipById(fellowshipId)
    allCandidates.value = await RemoteService.getCandidates()
    enrolledCandidates.value = await RemoteService.getEnrolledCandidatesByFellowship(fellowshipId)
  } catch (error) {
    console.error('Failed to fetch fellowship or candidates:', error)
  } finally {
    loading.value = false
  }
}

async function enrollCandidate(candidateId: number) {
  // constructs an enrollmentDto object and sends it to the backend
  const enrollmentDto = {
    motivation: '',
    enrollmentDateTime: new Date(),
    fellowshipId: fellowship.value?.id,
    candidateId: candidateId
  }
  try {
    if (fellowship.value) {
      await RemoteService.enrollCandidate(enrollmentDto)
      enrolledCandidates.value = [...enrolledCandidates.value,
        allCandidates.value.find(candidate => candidate.id === candidateId)]
    }
  } catch (err) {
    console.error('Failed to enroll candidate:', err)
  }
}

async function unenrollCandidate(candidateId: number) {
  try {
    if (fellowship.value) {
      await RemoteService.unenrollCandidate(fellowship.value.id, candidateId)
      // removes the candidate from the enrolledCandidates list
      enrolledCandidates.value = enrolledCandidates.value.filter(candidate => candidate.id !== candidateId)
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

function goBack() {
  router.back()
}

onMounted(() => {
  fetchFellowshipAndCandidates()
})

function getAvatarUrl(candidateId: string | undefined): string {
  const baseUrl = 'https://ui-avatars.com/api/';
  const defaultText = 'Unknown';
  const size = 56; // Size of the avatar
  if (candidateId) {
    return `${baseUrl}?name=${candidateId}&size=${size}&background=random`;
  }
  return `${baseUrl}?name=${defaultText}&size=${size}&background=random`;
}

</script>
