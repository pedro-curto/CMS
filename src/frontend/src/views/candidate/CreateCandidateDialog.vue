<template>
  <v-dialog v-model="dialog" max-width="500px">
    <template v-slot:activator="{ props: activatorProps }">
      <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Add a New Candidate"
          v-bind="activatorProps"
          color="primary"
      ></v-btn>
    </template>

    <div class="pa-4 text-center">
    <v-card prepend-icon="mdi-account-plus" title="Add a New Candidate">
      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
              v-model="newCandidate.istId"
              label="IstID"
              :rules="istIdRules"
              required
          ></v-text-field>
          <v-text-field
              v-model="newCandidate.name"
              label="Name"
              :rules="nameRules"
              required
          ></v-text-field>
          <v-text-field
              v-model="newCandidate.email"
              label="Email"
              :rules="emailRules"
              required
          ></v-text-field>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn text="Close" variant="plain" @click="closeDialog "></v-btn>

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
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto'

const dialog = ref(false)
const valid = ref(false)
const form = ref(null)
const emit = defineEmits(['candidate-created'])

const newCandidate = reactive<CandidateDto>({
  istId: '',
  name: '',
  email: ''
})

const istIdRules = [
  (v: number) => !!v || 'IstID is required',
  (v: number) => v > 0 || 'IstID must be a positive number'
]

const nameRules = [
  (v: string) => !!v || 'Name is required',
  (v: string) => v.length > 0 || 'Name must not be blank'
]

const emailRules = [
  (v: string) => !!v || 'Email is required',
  (v: string) => /.+@.+\..+/.test(v) || 'Email must be valid: name@example.com'
]

const resetForm = () => {
  newCandidate.istId = ''
  newCandidate.name = ''
  newCandidate.email = ''
  if (form.value) {
    form.value.reset()
  }
}

const closeDialog = () => {
  dialog.value = false
  resetForm()
}

const submitForm = async () => {
  if (form.value && form.value.validate()) {
    try {
      await RemoteService.addCandidate(newCandidate)
      dialog.value = false
      emit('candidate-created')
      resetForm()
    } catch (error) {
      alert(error.response.data.message)
    }
  }
}
</script>
