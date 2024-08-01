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

    <v-card>
      <v-card-title>
        <span class="headline">Create a new candidate</span>
      </v-card-title>

      <v-card-text>
        <v-text-field
          v-model="newCandidate.name"
          label="Name"
          required
        ></v-text-field>
        <v-text-field
          v-model="newCandidate.email"
          label="Email"
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
            saveCandidate()
          "
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import {ref, reactive } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidate/CandidateDto';
import type MaterialDto from "@/models/materials/MaterialDto";

const dialog = ref(false);
const emit = defineEmits(['candidate-created'])

const newCandidate = reactive<CandidateDto>({
  name: '',
  email: ''
})


const saveCandidate = async () => {
  await RemoteService.addCandidate(newCandidate)
  dialog.value = false
  emit('candidate-created')
}
</script>
