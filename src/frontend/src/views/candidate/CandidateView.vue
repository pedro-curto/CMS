<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Candidate Listing</h2>
    </v-col>
    <v-col cols="auto">
      <CreateCandidateDialog @candidate-created="fetchCandidates" />
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
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="openUpdateDialog(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deleteCandidate(item)" class="mr-2">mdi-delete</v-icon>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="400">
    <UpdateCandidateDialog
        v-if="selectedCandidate"
        :candidate="selectedCandidate"
        @candidate-updated="fetchCandidates"
        @dialog-close="closeUpdateDialog"
    />
  </v-dialog>

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
const dialog = ref(false)
const selectedCandidate = ref<CandidateDto | null>(null)

async function fetchCandidates() {
  const data = await RemoteService.getCandidates()
  candidates.splice(0, candidates.length, ...data) // replaces current array with new data
}

// fetches new candidates from the backend every two seconds
onMounted(() => {
  fetchCandidates()
  const intervalId = setInterval(fetchCandidates, 2000)
  onUnmounted(() => clearInterval(intervalId))
})

// Edit candidate dialog
function openUpdateDialog(candidate: CandidateDto) {
  selectedCandidate.value = { ...candidate }
  dialog.value = true
}

function closeUpdateDialog() {
  dialog.value = false
}

function deleteCandidate(candidate: CandidateDto) {
  RemoteService.deleteCandidate(candidate.id).then(() => {
    fetchCandidates()
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

</script>
