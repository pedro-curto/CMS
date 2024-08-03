<template>
  <v-dialog v-model="dialogVisible" max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">Update Fellowship Weights</span>
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
              v-for="(weight, category) in weights"
              :key="category"
              :label="category"
              v-model.number="weights[category]"
              :rules="weightRules"
              type="number"
              step="0.01"
              min="0"
              max="1"
              required
          ></v-text-field>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="closeDialog">Close</v-btn>
        <v-btn
            color="primary"
            :disabled="!valid"
            @click="submitForm"
        >Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import {ref, watch, onMounted, computed} from 'vue';
import RemoteService from '@/services/RemoteService';

const props = defineProps<{ fellowshipId: number }>();
const emit = defineEmits(['dialog-close']);

const dialogVisible = ref(true);
const valid = ref(false);
const form = ref(null);
const weights = ref<{ [key: string]: number }>({});

const weightRules = [
  (v: number) => !!v || 'Weight is required',
  (v: number) => v >= 0 && v <= 1 || 'Weight must be between 0 and 1'
    // total sum of weights must be 1

];

const fetchWeights = async () => {
  try {
    const response = await RemoteService.getEvaluationWeights(props.fellowshipId);
    weights.value = response;
  } catch (error) {
    console.error('Failed to fetch weights', error);
  }
};

const updateWeights = async () => {
  try {
    await RemoteService.updateFellowshipWeights(props.fellowshipId, weights.value);
    alert('Weights updated successfully');
    closeDialog();
  } catch (error) {
    console.error('Failed to update weights', error);
  }
};

const submitForm = () => {
  if (valid.value && totalWeight.value === 1) {
    updateWeights();
  } else {
    alert('Total sum of weights must be 1.');
  }
};

const totalWeight = computed(() => {
  return Object.values(weights.value).reduce((sum, weight) => sum + weight, 0);
});

const closeDialog = () => {
  dialogVisible.value = false;
  emit('dialog-close');
};

onMounted(() => {
  fetchWeights();
});

watch(() => props.fellowshipId, () => {
  fetchWeights();
});
</script>
