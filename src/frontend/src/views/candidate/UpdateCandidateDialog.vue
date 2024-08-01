<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
            class="text-none font-weight-regular"
            prepend-icon="mdi-pencil"
            text="Update Candidate"
            v-bind="activatorProps"
            color="primary"
            @click="openDialog"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Update Candidate">
        <v-card-text>
          <v-text-field label="Nome*" required v-model="candidate.name"></v-text-field>
          <v-text-field label="Email*" required v-model="candidate.email"></v-text-field>
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
              saveCandidate()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from "@/models/candidate/CandidateDto"

const props = defineProps<{ candidate: CandidateDto }>()
const dialog = ref(false)
const candidate = ref<CandidateDto>({
  ...props.candidate
})

const emit = defineEmits(['candidate-updated'])

const openDialog = () => {
  dialog.value = true
}

const saveCandidate = async () => {
  await RemoteService.updateCandidate(candidate.value.id, candidate.value)
  emit('candidate-updated')
}

</script>
