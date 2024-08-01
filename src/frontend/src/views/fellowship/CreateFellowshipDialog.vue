<template>
  <v-dialog v-model="dialog" max-width="1000px" max-height="1200px">
    <template v-slot:activator="{ props: activatorProps }">
      <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Add a New Fellowship"
          v-bind="activatorProps"
          color="primary"
      ></v-btn>
    </template>

    <v-card>
      <v-card-title>
        <span class="headline">Create a new Fellowship</span>
      </v-card-title>

      <v-card-text>
        <v-text-field
            v-model="newFellowship.name"
            label="Name"
            required
        ></v-text-field>

        <div>
          <label class="v-label">Start Date</label>
          <VueDatePicker
              v-model="newFellowship.startDate"
              :enable-time-picker="false"
              :auto-apply="true"
              required
          />
        </div>

        <div>
          <label class="v-label">End Date</label>
          <VueDatePicker
              v-model="newFellowship.endDate"
              :enable-time-picker="false"
              :auto-apply="true"
              required
          />
        </div>

        <v-text-field
            v-model="newFellowship.monthlyValue"
            label="Monthly Value"
            required
        ></v-text-field>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

        <v-btn
          color="primary"
          text="Save"
          variant="tonal"
          @click="
            dialog = false;
            saveFellowship()
       "
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import {ref, reactive, watch } from 'vue'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import VueDatePicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'

const dialog = ref(false)
const emit = defineEmits(['fellowship-created'])
const newFellowship = reactive<FellowshipDto>({
  name: '',
  startDate: '',
  endDate: '',
  monthlyValue: ''
})

watch(dialog, (newVal) => {
  if (!newVal) {
    resetFellowship()
  }
})

const resetFellowship = () => {
  newFellowship.name = ''
  newFellowship.startDate = ''
  newFellowship.endDate = ''
  newFellowship.monthlyValue = ''
}

const saveFellowship = async () => {
  await RemoteService.addFellowship(newFellowship)
  dialog.value = false
  emit('fellowship-created')
}
</script>
