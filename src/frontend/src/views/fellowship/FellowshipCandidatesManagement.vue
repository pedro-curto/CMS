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
          <v-btn v-if="!isEnrolled(candidate.istId)" color="success" @click="enrollCandidate(candidate.istId)">Enroll</v-btn>
          <v-btn v-if="isEnrolled(candidate.istId)" color="error" @click="unenrollCandidate(candidate.istId)">Unenroll</v-btn>
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
const allCandidates = ref<CandidateDto[]>([])
const candidates = ref<CandidateDto[]>([])
const candidateHeaders = [
  { title: 'Name', value: 'name' },
  { title: 'Email', value: 'email' },
  { title: 'IST ID', value: 'istId' }
]

async function fetchFellowshipAndCandidates() {
  const fellowshipId = route.params.id as string
  try {
    fellowship.value = fetchFellowship(fellowshipId)
    allCandidates.value = await RemoteService.getCandidates()
  } catch (error) {
    console.error('Failed to fetch fellowship or candidates:', error)
  }
}

async function fetchFellowship(id: string) {
  try {
    fellowship.value = await RemoteService.getFellowshipById(id)
  } catch (err) {
    err.value = 'Failed to fetch fellowship details.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

async function enrollCandidate(candidateId: string) {
  try {
    if (fellowship.value) {
      await RemoteService.enrollCandidate(Number(fellowship.value.id), candidateId)
      fetchFellowship(route.params.id as string) // Refresh the fellowship data
    }
  } catch (err) {
    console.error('Failed to enroll candidate:', err)
  }
}

async function unenrollCandidate(candidateId: string) {
  try {
    if (fellowship.value) {
      await RemoteService.unenrollCandidate(fellowship.value.id, candidateId)
      fetchFellowship(route.params.id as string) // Refresh the fellowship data
    }
  } catch (err) {
    console.error('Failed to unenroll candidate:', err)
  }
}

function isEnrolled(candidateIstId: string): boolean {
  return fellowship.value?.candidates.some(c => c.istId === candidateIstId) ?? false
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
