<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Candidate Details</h1>
      </v-col>
    </v-row>

    <!-- Candidate details -->
    <v-row>
      <v-col>
        <v-card v-if="candidate" class="pa-6" outlined>
          <v-card-title class="headline">
            <v-avatar size="96" class="mr-3">
              <v-img :src="getAvatarUrl(candidate.istId)" alt="Profile picture"></v-img>
            </v-avatar>
            {{ candidate.name }}
          </v-card-title>
          <v-card-subtitle class="text-h6 mb-4">{{ candidate.istId }}</v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-card class="pa-4" outlined>
                  <v-icon large>mdi-email</v-icon>
                  <div class="text-h6">Email</div>
                  <div class="text-body-1">{{ candidate.email }}</div>
                </v-card>
              </v-col>
              <!-- Add more details as needed -->
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Enrolled Fellowships Section -->
    <v-row>
      <v-col v-if="candidateFellowships.length">
        <h1 class="text-center">Enrolled Fellowships</h1>
      </v-col>
      <v-col v-else>
        <h1 class="text-center">No fellowships enrolled yet</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-if="!loading" v-for="fellowship in candidateFellowships" :key="fellowship.id" cols="12" sm="6" md="4">
        <v-card class="fellowship-card" outlined>
          <v-card-title>
            <v-icon>mdi-school</v-icon>
            <span>{{ fellowship.name }}</span>
          </v-card-title>
          <v-card-subtitle>{{ fellowship.startDate }} - {{ fellowship.endDate }}</v-card-subtitle>
          <v-card-text>
            <v-chip color="primary" text-color="white">{{ fellowship.monthlyValue.toLocaleString('en-US', { style: 'currency', currency: 'EUR' }) }}</v-chip>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Action Buttons -->
    <v-card-actions>
      <v-btn color="primary" @click="goBack">Back</v-btn>
      <v-btn color="secondary" @click="goToCandidatesPage">Manage All Candidates</v-btn>
    </v-card-actions>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'

const route = useRoute()
const router = useRouter()
const candidate = ref<CandidateDto | null>(null)
const candidateFellowships = ref<FellowshipDto[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

onMounted(() => {
  const candidateId = Number(route.params.id)
  fetchCandidate(candidateId)
  fetchCandidateFellowships(candidateId)
})

async function fetchCandidate(id: number) {
  try {
    candidate.value = await RemoteService.getCandidateById(id)
  } catch (err) {
    error.value = 'Failed to fetch candidate details.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

async function fetchCandidateFellowships(id: number) {
  try {
    candidateFellowships.value = await RemoteService.getCandidateFellowships(id)
  } catch (err) {
    error.value = 'Failed to fetch fellowships.'
    console.error(err)
  }
}

function goBack() {
  router.back()
}

function goToCandidatesPage() {
  router.push({ name: 'candidateList' })
}

function getAvatarUrl(istId: string | undefined): string {
  const baseUrl = 'https://ui-avatars.com/api/';
  const defaultText = 'Unknown';
  const size = 96; // Size of the avatar
  if (istId) {
    return `${baseUrl}?name=${istId}&size=${size}&background=random`;
  }
  return `${baseUrl}?name=${defaultText}&size=${size}&background=random`;
}
</script>

