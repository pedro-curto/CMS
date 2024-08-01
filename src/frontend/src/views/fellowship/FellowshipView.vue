<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Fellowship Listing</h2>
    </v-col>
    <v-col cols="auto">
      <CreateCandidateDialog @fellowship-created="fetchFellowships" />
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
      :items="fellowships"
      :search="search"
      :custom-filter="fuzzySearch"
      class="text-left"
  >
  </v-data-table>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import CreateCandidateDialog from '@/views/candidate/CreateCandidateDialog.vue'

const search = ref('')
/*
    id?: string
    fellowshipId?: string
    startDate?: string
    endDate?: string
    monthlyValue?: number
    candidates?: Candidate[] = []
 */
const headers = [
  { title: 'Fellowship ID', value: 'fellowshipId', key: 'fellowshipId' },
  { title: 'Start Date', value: 'startDate', key: 'startDate' },
  { title: 'End Date', value: 'endDate', key: 'endDate' },
  { title: 'Monthly Value', value: 'monthlyValue', key: 'monthlyValue' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

const fellowships: FellowshipDto[] = reactive([])
const dialog = ref(false)
const selectedFellowship = ref<FellowshipDto | null>(null)

async function fetchFellowships() {
  const data = await RemoteService.getFellowships()
  fellowships.splice(0, fellowships.length, ...data) // replaces current array with new data
}

// fetches new fellowships from the backend every two seconds
onMounted(() => {
  fetchFellowships()
  const intervalId = setInterval(fetchFellowships, 2000)
  onUnmounted(() => clearInterval(intervalId))
})

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

</script>
