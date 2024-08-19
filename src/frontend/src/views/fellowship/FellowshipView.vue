<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Fellowship Listing</h2>
    </v-col>
    <v-col cols="auto">
      <CreateFellowshipDialog @fellowship-created="fetchFellowships" />
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
      :items="computedFellowships"
      :search="search"
      :custom-filter="fuzzySearch"
      class="text-left"
  >
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="openDetailsDialog(item)" class="mr-2">mdi-eye</v-icon>
      <v-icon v-if="!item?.closed"  @click="openCandidatesPage(item)" class="mr-2">mdi-account-group</v-icon>
      <v-icon @click="openUpdateDialog(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deleteFellowship(item)" class="mr-2">mdi-delete</v-icon>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="600px" max-height="700px">
    <UpdateFellowshipDialog
        v-if="selectedFellowship"
        :fellowship="selectedFellowship"
        @fellowship-updated="fetchFellowships"
        @dialog-close="closeUpdateDialog"
    />
  </v-dialog>

</template>

<script setup lang="ts">
import {ref, reactive, onMounted, onUnmounted, computed} from 'vue'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import CreateFellowshipDialog from '@/views/fellowship/CreateFellowshipDialog.vue'
import UpdateFellowshipDialog from '@/views/fellowship/UpdateFellowshipDialog.vue'
import router from '@/router'

const search = ref('')
const headers = [
  { title: 'Name', value: 'name', key: 'name' },
  { title: 'Start Date', value: 'startDate', key: 'startDate' },
  { title: 'End Date', value: 'endDate', key: 'endDate' },
  { title: 'Monthly Value', value: 'monthlyValue', key: 'monthlyValue' },
  { title: 'Active', value: 'active', key: 'active' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

const fellowships: FellowshipDto[] = reactive([])
const dialog = ref(false)
const selectedFellowship = ref<FellowshipDto | null>(null)
const computedFellowships = computed(() => {
  return fellowships.map(fellowship => ({
    ...fellowship,
    active: fellowship.closed ? 'No' : 'Yes'
  }))
})

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

function openDetailsDialog(fellowship: FellowshipDto) {
  router.push({ name: 'fellowshipDetails', params: { id: fellowship.id } })
}

function openCandidatesPage(fellowship: FellowshipDto) {
  router.push({ name: 'fellowshipCandidates', params: { id: fellowship.id } })
}

function openUpdateDialog(fellowship: FellowshipDto) {
  selectedFellowship.value = { ...fellowship }
  dialog.value = true
}

function closeUpdateDialog() {
  dialog.value = false
}

function deleteFellowship(fellowship: FellowshipDto) {
  RemoteService.deleteFellowship(fellowship.id).then(() => {
    fetchFellowships()
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

</script>
