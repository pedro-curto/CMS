<template>
  <div class="pa-4 text-center">
      <v-card prepend-icon="mdi-briefcase-edit" title="Update Fellowship Details">
        <v-card-text>
          <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
              label="Name*"
              required
              :rules="nameRules"
              v-model="fellowship.name"
          ></v-text-field>
          <div>
            <label class="v-label">Start Date</label>
            <VueDatePicker
                v-model="fellowship.startDate"
                :enable-time-picker="false"
                :auto-apply="true"
                :rules="dateRules"
                required
            />
            <span v-if="!fellowship.startDate" class="error-message">Start Date is required</span>
          </div>
          <div>
            <label class="v-label">End Date</label>
            <VueDatePicker
                v-model="fellowship.endDate"
                :enable-time-picker="false"
                :auto-apply="true"
                :rules="endDateRules"
                required
            />
            <span v-if="!fellowship.endDate" class="error-message">End Date is required</span>
          </div>
          <v-text-field
              label="Monthly Value*"
              required
              :rules="monthlyValueRules"
              v-model="fellowship.monthlyValue"
          ></v-text-field>
          </v-form>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="closeDialog"></v-btn>

          <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              :disabled="!valid"
              @click="submitForm"
          ></v-btn>
        </v-card-actions>
      </v-card>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import RemoteService from '@/services/RemoteService'
import type FellowshipDto from '@/models/fellowship/FellowshipDto'
import VueDatePicker from '@vuepic/vue-datepicker'

const form = ref(null)
const valid = ref(false)
const props = defineProps<{ fellowship: FellowshipDto }>()
const dialog = ref(false)
const fellowship = ref<FellowshipDto>({
  ...props.fellowship
})

const emit = defineEmits(['fellowship-updated', 'dialog-close'])

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

const endDateRules = [
  (v: string) => !!v || 'End Date is required',
]

watch(props, (newProps)=> {
  fellowship.value = { ...newProps.fellowship }
})

const closeDialog = () => {
  emit('dialog-close')
}

const submitForm = async () => {
  console.log(valid.value)
  if (form.value && form.value.validate()) {
    await RemoteService.updateFellowship(fellowship.value)
    emit('fellowship-updated')
    closeDialog()
  }
}

</script>

<style scoped>
.error-message {
  color: red;
}
</style>