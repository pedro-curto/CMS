<template>
  <v-dialog v-model="dialog" max-width="600px" max-height="700px">
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
        <v-row align="center">
          <v-col class="d-flex align-center justify-center">
            <v-icon class="mr-2">mdi-briefcase-plus</v-icon>
            <span class="headline">Create a New Fellowship</span>
          </v-col>
        </v-row>
      </v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
              v-model="newFellowship.name"
              label="Name"
              :rules="nameRules"
              required
          ></v-text-field>
          <v-textarea
            v-model="newFellowship.description"
            label="Description"
            :rules="descriptionRules"
            rows="2"
            auto-grow
          ></v-textarea>

          <div>
            <label class="v-label">Start Date</label>
            <div class="date-picker-container">
              <VueDatePicker
                  v-model="newFellowship.startDate"
                  :enable-time-picker="false"
                  :auto-apply="true"
                  :rules="dateRules"
                  required
              />
              <span v-if="!newFellowship.startDate" class="error-message">Start Date is required</span>
            </div>
          </div>

          <div>
            <label class="v-label">End Date</label>
            <div class="date-picker-container">
              <VueDatePicker
                  v-model="newFellowship.endDate"
                  :enable-time-picker="false"
                  :auto-apply="true"
                  :rules="dateRules"
                  required
              />
              <span v-if="!newFellowship.endDate" class="error-message">End Date is required</span>
            </div>
          </div>

          <v-text-field
              v-model="newFellowship.monthlyValue"
              label="Monthly Value"
              :rules="monthlyValueRules"
              required
          ></v-text-field>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

        <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            :disabled="!valid"
            @click="submitForm"
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import {ref, reactive, watch} from 'vue'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import VueDatePicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import {de} from "vuetify/locale";

const dialog = ref(false)
const valid = ref(false)
const form = ref(null)
const emit = defineEmits(['fellowship-created'])
const newFellowship = reactive<FellowshipDto>({
  name: '',
  description: '',
  startDate: '',
  endDate: '',
  monthlyValue: ''
})

// validation rules
const nameRules = [
  (v: string) => !!v || 'Name is required',
  (v: string) => v.length > 2 || 'Fellowship name must have at least 3 characters'
]

const monthlyValueRules = [
  (v: string) => !!v || 'Monthly Value is required',
  (v: string) => v.length > 0 || 'Monthly Value must not be blank',
  (v: string) => /^\d+(\.\d{1,2})?$/.test(v) || 'Monthly Value must be a valid monetary amount'
]

const dateRules = [
  (v: string) => !!v || 'Date is required'
]

const descriptionRules = [
  (v: string) => v.length <= 255 || 'Description must be less than 255 characters'
]

watch(dialog, (newVal) => {
  if (!newVal) {
    resetFellowship()
  }
})

const resetFellowship = () => {
  newFellowship.name = ''
  newFellowship.description = ''
  newFellowship.startDate = ''
  newFellowship.endDate = ''
  newFellowship.monthlyValue = ''
}

const submitForm = async () => {
  if (form.value && form.value.validate() && validateDates()) {
    await RemoteService.addFellowship(newFellowship)
    dialog.value = false
    emit('fellowship-created')
  }
}

const validateDates = () => {
  if (!newFellowship.startDate || !newFellowship.endDate) {
    return false
  }
  if (new Date(newFellowship.startDate) > new Date(newFellowship.endDate)) {
    alert('End Date must be after Start Date')
    return false
  }
  return true
}

</script>

<style scoped>
.error-message {
  color: red;
  font-size: 0.75rem;
}
</style>