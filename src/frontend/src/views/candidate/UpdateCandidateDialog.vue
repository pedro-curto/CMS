<template>
  <div class="pa-4 text-center">
      <v-card prepend-icon="mdi-account" title="Update Candidate">
        <v-card-text>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
                label="IstID*"
                required
                v-model="candidate.istId"
                :rules="istIdRules"
            ></v-text-field>
            <v-text-field
                label="Name*"
                required
                v-model="candidate.name"
                :rules="nameRules"
            ></v-text-field>
            <v-text-field
                label="Email*"
                required
                v-model="candidate.email"
                :rules="emailRules"
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
import type CandidateDto from "@/models/candidate/CandidateDto"

const props = defineProps<{ candidate: CandidateDto }>()
const valid = ref(false)
const form = ref(null)
const candidate = ref<CandidateDto>({
  ...props.candidate
})

const emit = defineEmits(['candidate-updated', 'dialog-close'])

const istIdRules = [
  (v: string) => !!v || 'IstID is required',
  (v: string) => v.length > 0 || 'IstID must not be blank'
]

const nameRules = [
  (v: string) => !!v || 'Name is required',
  (v: string) => v.length > 0 || 'Name must not be blank'
]

const emailRules = [
  (v: string) => !!v || 'Email is required',
  (v: string) => /.+@.+\..+/.test(v) || 'Email must be valid'
]

watch(props, (newProps)=> {
  candidate.value = { ...newProps.candidate }
})

const closeDialog = () => {
  emit('dialog-close')
}

const submitForm = async () => {
  if (form.value && form.value.validate()) {
    await RemoteService.updateCandidate(candidate.value.id, candidate.value)
    emit('candidate-updated')
    closeDialog()
  }
}

</script>
