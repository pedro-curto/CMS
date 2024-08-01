<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="text-center">Fellowship Details</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card v-if="fellowship" class="pa-4">
          <v-card-title class="headline">{{ fellowship.name }}</v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <strong>Start Date:</strong> {{ fellowship.startDate }}
              </v-col>
              <v-col cols="12" sm="6">
                <strong>End Date:</strong> {{ fellowship.endDate }}
              </v-col>
              <v-col cols="12" sm="6">
                <strong>Monthly Value:</strong> {{ fellowship.monthlyValue }}
              </v-col>
            </v-row>
            <v-divider></v-divider>
            <h2 class="subtitle-1">Candidates</h2>
            <v-list>
              <v-list-item v-for="candidate in fellowship.candidates" :key="candidate.id">
                <v-list-item-content>
                  <v-list-item-title>{{ candidate.name }}</v-list-item-title>
                  <v-list-item-subtitle>{{ candidate.email }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="goBack">Back</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'

const route = useRoute()
const router = useRouter()
const fellowship = ref<FellowshipDto | null>(null)

async function fetchFellowship(id: string) {
  fellowship.value = await RemoteService.getFellowshipById(id)
}

function goBack() {
  router.back()
}

onMounted(() => {
  const fellowshipId = route.params.id as string
  fetchFellowship(fellowshipId)
})
</script>
