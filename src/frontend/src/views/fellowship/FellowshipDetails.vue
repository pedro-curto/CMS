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
          <v-card-subtitle>IST{{ candidate.istId }}</v-card-subtitle>
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
          <v-card-text>
            <v-row justify="center">
              <v-col cols="auto">
                <v-btn color="primary" @click="openWeightsDialog">Change Evaluation Weights</v-btn>
              </v-col>
              <v-col cols="auto">
                <v-btn color="primary" @click="selectFellowshipWinner">Select Fellowship Winner</v-btn>
              </v-col>
            </v-row>

            <!-- Winner Dialog -->
            <v-dialog v-model="winnerDialog" max-width="600">
              <v-card>
                <v-card-title class="headline text-center">
                  <v-icon justify="center" class="mr-2">mdi-trophy</v-icon>
                  Fellowship Winner
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                  <v-row align="center" justify="center">
                    <v-col cols="auto">
                      <v-avatar size="100" class="mb-3">
                        <v-img :src="getAvatarUrl(fellowshipWinner?.id)" alt="Profile picture"></v-img>
                      </v-avatar>
                    </v-col>
                  </v-row>
                  <v-row align="center" justify="center">
                    <v-col cols="auto" class="text-center">
                      <div class="text-h5">{{ fellowshipWinner?.name }}</div>
                      <div class="text-subtitle-1">IST{{ fellowshipWinner?.istId }}</div>
                      <div class="text-body-1">{{ fellowshipWinner?.email }}</div>
                      <div class="text-h6 mt-4">Final Grade: {{ finalGrade !== null ? finalGrade.toFixed(2) : 'N/A' }}</div>
                    </v-col>
                  </v-row>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" @click="closeWinnerDialog">Close</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

  <v-dialog v-model="weightsDialog" max-width="1000">
    <EditWeightsDialog
        v-if="fellowship"
        :fellowshipId="fellowship.id"
        @dialog-close="closeWeightsDialog"
    />
  </v-dialog>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import type CandidateDto from "@/models/candidate/CandidateDto";
import EditWeightsDialog from '@/views/fellowship/EditWeightsDialog.vue'

const route = useRoute()
const router = useRouter()
const fellowship = ref<FellowshipDto | null>(null)
const enrolledCandidates = ref<CandidateDto[]>([])
const fellowshipWinner = ref<CandidateDto | null>(null)
const finalGrade = ref<number | null>(null);
const weightsDialog = ref(false)
const winnerDialog = ref(false)
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

function openWeightsDialog() {
  weightsDialog.value = true
}
function closeWeightsDialog() {
  weightsDialog.value = false
}

async function selectFellowshipWinner() {
  try {
    fellowshipWinner.value = await RemoteService.getFellowshipWinner(fellowship.value.id)
    // gets grade of the winner
    const enrollmentId = await RemoteService.getEnrollmentId(fellowship.value.id, fellowshipWinner.value!.id)
    finalGrade.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId)
    winnerDialog.value = true
  } catch (err) {
    error.value = 'Failed to fetch evaluation weights.'
    console.error(err)
    alert('Failed to select fellowship winner. Not all candidates may have been evaluated.')
  }
}

function closeWinnerDialog() {
  winnerDialog.value = false
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
</style>
