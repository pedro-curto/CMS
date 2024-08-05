<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Evaluation Details</h1>
      </v-col>
    </v-row>

    <v-row v-if="evaluation">
      <v-col>
        <v-card class="pa-6" outlined>
          <v-card-title class="headline">Evaluation for Candidate {{ candidateId }}</v-card-title>
          <v-card-subtitle class="text-h6 mb-4">Scores</v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-row>
              <v-col v-for="(score, category) in evaluation.scores" :key="category" cols="12" sm="6" md="4">
                <v-card class="pa-4" outlined>
                  <div class="text-h6">{{ category }}</div>
                  <div class="text-body-1">{{ formatNumber(score) }}</div>
                </v-card>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row v-else>
      <v-col>
        <v-card class="pa-6" outlined>
          <v-card-title class="headline">Create Evaluation for Candidate {{ candidateId }}</v-card-title>
          <v-card-subtitle class="text-h6 mb-4">Enter Scores</v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-form @submit.prevent="submitEvaluation">
              <v-row>
                <v-col v-for="category in evaluationCategories" :key="category" cols="12" sm="6" md="4">
                  <v-text-field
                      v-model="newEvaluation.scores[category]"
                      :label="`${category} Score`"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-btn type="submit" color="primary">Submit</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row v-if="evaluation" justify="center">
      <v-col cols="12" md="6">
        <v-card class="elevation-2" outlined>
          <v-card-title class="headline text-center">
            <v-icon>mdi-trophy</v-icon>
            <span class="ml-2">Evaluation Result</span>
          </v-card-title>
          <v-card-text class="text-center">
            <div class="final-result">
              {{ formatNumber(finalResult) }}
            </div>
          </v-card-text>
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
import { useRoute } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type EvaluationDto from '@/models/evaluation/EvaluationDto'
import router from "@/router";

const route = useRoute()
const candidateId = Number(route.params.candidateId)
const fellowshipId = Number(route.params.id)
const enrollmentId = ref<number | null>(null)
const evaluation = ref<EvaluationDto | null>(null)
const finalResult = ref<number | null>(null)
const evaluationCategories = ref<string[]>([])
const newEvaluation = ref<EvaluationDto>({
  enrollmentId: null,
  scores: {}
})

onMounted(async () => {
  try {
    enrollmentId.value = await RemoteService.getEnrollmentId(fellowshipId, candidateId)
    try {
      evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
      finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
    } catch (err) {
      newEvaluation.value.enrollmentId = enrollmentId.value
      evaluationCategories.value = await RemoteService.getEvaluationCategories()
    }
  } catch (err) {
    console.error('Failed to fetch evaluation details:', err)
  }
})

async function submitEvaluation() {
  try {
    await RemoteService.createEvaluation(newEvaluation.value)
    evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
    finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
  } catch (err) {
    console.error('Failed to submit evaluation:', err)
  }
}

function formatNumber(value: number | null): string {
  return value !== null ? value.toFixed(2) : 'N/A'
}

function goBack() {
  router.back()
}
</script>

<style scoped>
.final-result {
  font-size: xx-large;
  font-weight: bold;
}
</style>