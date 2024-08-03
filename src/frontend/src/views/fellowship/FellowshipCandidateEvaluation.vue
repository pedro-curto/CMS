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
          <v-card-text>
            <div v-for="(score, index) in evaluation.scores" :key="index">
              <p>{{ index }}: {{ formatNumber(score) }}</p>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row v-else>
      <v-col>
        <v-card class="pa-6" outlined>
          <v-card-title class="headline">Create Evaluation for Candidate {{ candidateId }}</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="submitEvaluation">
              <div v-for="category in evaluationCategories" :key="category">
                <v-text-field
                    v-model="newEvaluation.scores[category]"
                    :label="`${category} Score`"
                ></v-text-field>
              </div>
              <v-btn type="submit" color="primary">Submit</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row v-if="evaluation">
      <v-col>
        <v-card class="pa-6" outlined>
          <v-card-title class="headline">Evaluation Result </v-card-title>
          <v-card-text>
              <p>{{ formatNumber(finalResult) }} </p>
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
    // tries to fetch evaluation details -- if it fails, presents form to create new evaluation
    try {
      evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
      finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
      console.log("evaluation", evaluation.value)
    } catch (err) {
      console.error('Failed to fetch evaluation details:', err)
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