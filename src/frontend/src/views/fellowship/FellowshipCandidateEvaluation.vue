<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Evaluation Details</h1>
      </v-col>
    </v-row>
    <!-- Presents evaluation details if the candidate evaluation was already made -->
    <v-container v-if="evaluation">
      <v-row>
        <v-col>
          <v-card class="pa-6" outlined>
            <v-card-title class="headline">Evaluation for {{ candidate?.name }}</v-card-title>
            <v-card-subtitle class="text-h6 mb-4">Scores</v-card-subtitle>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col v-for="(score, category) in evaluation.scores" :key="category" cols="12" sm="6" md="4">
                  <v-card class="pa-4" outlined>
                    <div class="text-h6">{{ category }}</div>
                    <div v-if="!isEditing" class="text-body-1">{{ formatNumber(score) }}</div>
                    <v-text-field
                        v-else
                        v-model="editableScores[category]"
                        :label="`${category} Score`"
                        :rules="scoreRules"
                        required
                    ></v-text-field>
                  </v-card>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn v-if="!isEditing && !isFellowshipClosed" color="primary" @click="startEditing">Edit</v-btn>
              <v-btn v-else-if="!isFellowshipClosed" color="primary" @click="saveChanges">Save</v-btn>
              <v-btn v-if="isEditing && !isFellowshipClosed" text @click="cancelEditing">Cancel</v-btn>
            </v-card-actions>
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
    </v-container>
    <!-- If the candidate evaluation was not made yet, presents the form to create one -->
    <v-row v-else>
      <v-col>
        <v-card class="pa-6 md-6" outlined>
          <v-card-title class="headline">Create Evaluation for {{ candidate?.name }}</v-card-title>
          <v-card-subtitle class="text-h6 mb-4">Enter Scores</v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-row>
                <v-col v-for="(weight, category) in evaluationWeights" :key="category" cols="12" sm="6" md="4">
                  <v-text-field
                      v-model="newEvaluation.scores[category]"
                      :label="`${category} Score`"
                      :rules="scoreRules"
                      required
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-spacer></v-spacer>
          <v-btn
              type="submit"
              color="primary"
              :disabled="!valid"
              @click="submitEvaluation"
          >Submit</v-btn>
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
import type CandidateDto from "@/models/candidate/CandidateDto";

const route = useRoute()
const valid = ref(false)
const candidateId = Number(route.params.candidateId)
const fellowshipId = Number(route.params.id)
const enrollmentId = ref<number | null>(null)
const evaluation = ref<EvaluationDto | null>(null)
const candidate = ref<CandidateDto | null>(null)
const finalResult = ref<number | null>(null)
const evaluationWeights = ref<{ [key: string]: number }>({})
const newEvaluation = ref<EvaluationDto>({
  enrollmentId: null,
  scores: {}
})
const isEditing = ref(false)
const editableScores = ref<{ [key: string]: number }>({})
const isFellowshipClosed = ref(false)

// validation rules
const scoreRules = [
  (v: number) => !!v || 'Score is required',
  (v: number) => v >= 0 && v <= 20 || 'Score must be between 0 and 20'
]

onMounted(async () => {
  try {
    enrollmentId.value = await RemoteService.getEnrollmentId(fellowshipId, candidateId)
    candidate.value = await RemoteService.getCandidateById(candidateId)
    isFellowshipClosed.value = await RemoteService.isFellowshipClosed(fellowshipId)
    try {
      evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
      console.log("EVALUATION: " + JSON.stringify(evaluation.value))
      finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
    } catch (err) {
      newEvaluation.value.enrollmentId = enrollmentId.value
      evaluationWeights.value = await RemoteService.getEvaluationWeights(fellowshipId)
    }
  } catch (err) {
    console.error('Failed to fetch evaluation details:', err)
  }
})

function startEditing() {
  isEditing.value = true
  editableScores.value = { ...evaluation.value!.scores }
}

function cancelEditing() {
  isEditing.value = false
  editableScores.value = {}
}

async function saveChanges() {
  try {
    evaluation.value!.scores = { ...editableScores.value }
    await RemoteService.updateEvaluation(evaluation.value!)
    evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
    finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
    isEditing.value = false
  } catch (err) {
    console.error('Failed to save changes:', err)
  }
}

async function submitEvaluation() {
  try {
    console.log("NEW EVALUATION: " + JSON.stringify(newEvaluation.value))
    await RemoteService.createEvaluation(newEvaluation.value)
    evaluation.value = await RemoteService.getEvaluationDetails(enrollmentId.value)
    finalResult.value = await RemoteService.getCandidateFinalEvaluation(enrollmentId.value)
  } catch (err) {
    console.error('Failed to submit evaluation:', err)
  }
}

function formatNumber(value: number | null): string {
  if (typeof value === 'number') {
    return value.toFixed(2);
  }
  return 'N/A';
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