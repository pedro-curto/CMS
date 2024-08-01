<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Candidate Listing</h2>
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
    <template v-slot:item.actions="{ item }">
      <UpdateCandidateDialog :candidate="item" @candidate-updated="getCandidates" />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto'
import CreateCandidateDialog from '@/views/candidate/CreateCandidateDialog.vue'
import UpdateCandidateDialog from '@/views/candidate/UpdateCandidateDialog.vue'

const search = ref('')
const headers = [
  { title: 'IstID', value: 'id', key: 'id' },
  { title: 'Name', value: 'name', key: 'name' },
  { title: 'Email', value: 'email', key: 'email' },
  { title: 'Actions', value: 'actions', key: 'actions' }
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

/*async function fetchCandidates() {
  const data = await RemoteService.getCandidates()
  candidates.splice(0, candidates.length, ...data)
}

// fetches new candidates from the backend every second
onMounted(() => {
  fetchCandidates()
  const intervalId = setInterval(fetchCandidates, 1000) //
  onUnmounted(() => clearInterval(intervalId))
})*/

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
