<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Fellowship Details</h1>
      </v-col>
    </v-row>

    <!-- Fellowship details -->
    <v-row>
      <v-col>
        <v-card v-if="fellowship" class="pa-6" outlined>
          <v-card-title class="headline">
            <v-icon>mdi-school</v-icon>
            {{ fellowship.name }}
          </v-card-title>
          <v-card-subtitle class="text-h6 mb-4">{{ fellowship.description }}</v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-card class="pa-4" outlined>
                  <v-icon large>mdi-calendar-start</v-icon>
                  <div class="text-h6">Start Date</div>
                  <div class="text-body-1">{{ fellowship.startDate }}</div>
                </v-card>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-card class="pa-4" outlined>
                  <v-icon large>mdi-calendar-end</v-icon>
                  <div class="text-h6">End Date</div>
                  <div class="text-body-1">{{ fellowship.endDate }}</div>
                </v-card>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-card class="pa-4" outlined>
                  <v-icon large>mdi-currency-eur</v-icon>
                  <div class="text-h6">Monthly Value</div>
                  <div class="text-body-1">{{ fellowship.monthlyValue.toLocaleString('en-US', { style: 'currency', currency: 'EUR' }) }}</div>
                </v-card>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Enrolled Candidates Section -->
    <v-row>
      <v-col v-if="enrolledCandidates.length">
        <h1 class="text-center">Enrolled Candidates</h1>
      </v-col>
      <v-col v-else>
        <h1 class="text-center">No candidates enrolled yet</h1>
      </v-col>

    </v-row>
    <v-row>
      <v-col v-if="!loading" v-for="candidate in enrolledCandidates" :key="candidate.id" cols="12" sm="6" md="4">
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
          <v-card-actions>
            <v-btn
                  color="primary"
                 @click="goToEvaluationPage(candidate.id)">View Evaluation
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-card-actions>
      <v-btn color="primary" @click="goBack">Back</v-btn>
      <v-btn color="secondary" @click="goToCandidatesPage">Manage All Candidates</v-btn>
    </v-card-actions>
    <v-row v-if="evaluationWeights">
      <v-col>
        <v-card class="pa-6" outlined>
          <v-card-title class="headline">Evaluation Weights</v-card-title>
          <v-card-text>
            <div v-for="(weight, category) in evaluationWeights" :key="category">
              <p>{{ category }}: {{ weight }}</p>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import type CandidateDto from "@/models/candidate/CandidateDto";

const route = useRoute()
const router = useRouter()
const fellowship = ref<FellowshipDto | null>(null)
const enrolledCandidates = ref<CandidateDto[]>([])
const loading = ref(true)
const error = ref<string | null>(null)
const evaluationWeights = ref<{ [key: string]: number }>({})

onMounted(() => {
  const fellowshipId = Number(route.params.id)
  fetchFellowship(fellowshipId)
  fetchEnrolledCandidates(fellowshipId)
  fetchEvaluationWeights(fellowshipId)
})

async function fetchFellowship(id: number) {
  try {
    fellowship.value = await RemoteService.getFellowshipById(id)
  } catch (err) {
    error.value = 'Failed to fetch fellowship details.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

async function fetchEnrolledCandidates(fellowshipId: number) {
  try {
    enrolledCandidates.value = await RemoteService.getEnrolledCandidatesByFellowship(fellowshipId)
  } catch (err) {
    error.value = 'Failed to fetch enrolled candidates.'
    console.error(err)
  }
}

async function fetchEvaluationWeights(fellowshipId: number) {
  try {
    evaluationWeights.value = await RemoteService.getEvaluationWeights(fellowshipId)
  } catch (err) {
    error.value = 'Failed to fetch evaluation weights.'
    console.error(err)
  }
}

function goBack() {
  router.back()
}

function goToCandidatesPage() {
  router.push({ name: 'fellowshipCandidates' })
}

function goToEvaluationPage(candidateId: number) {
  router.push({ name: 'candidateEvaluation', params: { candidateId } })
}

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

<style scoped>
.candidate-card {
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}
.candidate-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
