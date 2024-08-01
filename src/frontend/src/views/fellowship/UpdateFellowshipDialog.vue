<template>
  <div class="pa-4 text-center">
      <v-card prepend-icon="mdi-briefcase-edit" title="Update Fellowship Details">
        <v-card-text>
          <v-text-field label="Name*" required v-model="fellowship.name"></v-text-field>
          <div>
            <label class="v-label">Start Date</label>
            <VueDatePicker
                v-model="fellowship.startDate"
                :enable-time-picker="false"
                :auto-apply="true"
                required
            />
          </div>
          <div>
            <label class="v-label">End Date</label>
            <VueDatePicker
                v-model="fellowship.endDate"
                :enable-time-picker="false"
                :auto-apply="true"
                required
            />
          </div>
          <v-text-field label="Monthly Value*" required v-model="fellowship.monthlyValue"></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="closeDialog"></v-btn>

          <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="saveFellowship"
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

const props = defineProps<{ fellowship: FellowshipDto }>()
const dialog = ref(false)
const fellowship = ref<FellowshipDto>({
  ...props.fellowship
})

const emit = defineEmits(['fellowship-updated', 'dialog-close'])

watch(props, (newProps)=> {
  fellowship.value = { ...newProps.fellowship }
})

const closeDialog = () => {
  emit('dialog-close')
}

const saveFellowship = async () => {
  await RemoteService.updateFellowship(fellowship.value.id, fellowship.value)
  emit('fellowship-updated')
  closeDialog()
}

</script>
