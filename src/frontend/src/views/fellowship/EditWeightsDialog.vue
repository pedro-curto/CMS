<template>
  <v-dialog v-model="dialogVisible" max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">Update Fellowship Weights</span>
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
              v-for="(weight, category) in tempWeights"
              :key="category"
              :label="category"
              v-model.number="tempWeights[category]"
              :rules="weightRules"
              type="number"
              step="0.1"
              min="0"
              max="1"
              :append-icon="'mdi-trash-can'"
              @click:append="removeCategory(category)"
              required
          ></v-text-field>
          <v-text-field
              v-model="newCategory"
              label="New Category"
          ></v-text-field>
          <v-btn @click="addCategory">Add Category</v-btn>
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
import { ref, watch, onMounted, computed } from 'vue';
import RemoteService from '@/services/RemoteService';

const props = defineProps<{ fellowshipId: number }>();
const emit = defineEmits(['dialog-close', 'weights-updated']);

const dialogVisible = ref(true);
const valid = ref(false);
const form = ref(null);
const weights = ref<{ [key: string]: number }>({});
const tempWeights = ref<{ [key: string]: number }>({});
const newCategory = ref('');

const weightRules = [
  (v: number) => !!v || 'Weight is required',
  (v: number) => v >= 0 && v <= 1 || 'Weight must be between 0 and 1'
];

const fetchWeights = async () => {
  try {
    weights.value = await RemoteService.getEvaluationWeights(props.fellowshipId);
    tempWeights.value = { ...weights.value };
  } catch (error) {
    console.error('Failed to fetch weights', error);
  }
};

const addCategory = () => {
  if (newCategory.value && !tempWeights.value[newCategory.value]) {
    tempWeights.value[newCategory.value] = 0;
    newCategory.value = '';
  }
};

const removeCategory = (category: string) => {
  delete tempWeights.value[category];
};

const updateWeights = async () => {
  try {
    console.log("tempWeights.value", tempWeights.value);
    await RemoteService.updateFellowshipWeights(props.fellowshipId, tempWeights.value);
    alert('Weights updated successfully');
    emit('weights-updated', tempWeights.value);
    closeDialog();
  } catch (error) {
    console.error('Failed to update weights', error);
  }
};

const submitForm = () => {
  const diff = 0.01;
  if (valid.value && Math.abs(totalWeight.value - 1) <= diff) {
    updateWeights();
  } else {
    alert('Total sum of weights must be 1.');
  }
};

const totalWeight = computed(() => {
  return Object.values(tempWeights.value).reduce((sum, weight) => sum + weight, 0);
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