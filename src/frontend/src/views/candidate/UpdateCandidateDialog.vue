<template>
  <div class="pa-4 text-center">
      <v-card prepend-icon="mdi-account" title="Update Candidate">
        <v-card-text>
          <v-text-field label="Name*" required v-model="candidate.name"></v-text-field>
          <v-text-field label="Email*" required v-model="candidate.email"></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="closeDialog"></v-btn>

          <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="saveCandidate"
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
const dialog = ref(false)
const candidate = ref<CandidateDto>({
  ...props.candidate
})

const emit = defineEmits(['candidate-updated', 'dialog-close'])

watch(props, (newProps)=> {
  candidate.value = { ...newProps.candidate }
})

const closeDialog = () => {
  emit('dialog-close')
}

const saveCandidate = async () => {
  await RemoteService.updateCandidate(candidate.value.id, candidate.value)
  emit('candidate-updated')
  closeDialog()
}

</script>
