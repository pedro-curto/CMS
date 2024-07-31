<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Candidatos</h2>
    </v-col>
    <v-col cols="auto">
      <CreateCandidateDialog @candidate-created="getCandidates" />
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    :headers="headers"
    :items="candidates"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
  </v-data-table>
</template>

<script setup lang="ts">
import CreateCandidateDialog from '@/views/candidate/CreateCandidateDialog.vue'
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type CandidateDto from "@/models/candidate/CandidateDto";

const search = ref('')
const headers = [
  { title: 'IstID', value: 'id', key: 'id' },
  { title: 'Nome', value: 'name', key: 'name ' },
  { title: 'Email', value: 'email', key: 'email' },
]

const candidates: CandidateDto[] = reactive([])

getCandidates()
async function getCandidates() {
  candidates.splice(0, candidates.length)
  RemoteService.getCandidates().then(async (data) => {
    data.forEach((candidate: CandidateDto) => {
      candidates.push(candidate)
    })
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
