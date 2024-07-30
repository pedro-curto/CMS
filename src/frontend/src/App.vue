<template>
  <v-layout id="app">
    <TopBar />
    <v-main
      style="margin-top: 96px"
      class="pa-10 d-flex flex-column flex-grow-1 flex-shrink-1 text-center"
    >
      <v-card class="elevation-4 pa-8" width="100%">
        <RouterView />
      </v-card>
    </v-main>
  </v-layout>
</template>

<script setup lang="ts">
import TopBar from '@/components/TopBar.vue'

import { RouterLink, RouterView } from 'vue-router'
</script>
