<template>
  <v-app-bar color="secondary" :height="36" class="px-2">
    <v-toolbar-items>
      <v-btn
        href="https://dei.tecnico.ulisboa.pt/"
        selected-class="no-active"
        class="dei-title"
        size="small"
      >
        Departamento de Engenharia Informática
      </v-btn>
    </v-toolbar-items>
    <v-spacer />
    <v-toolbar-items class="align-center">
      <DarkModeSwitch />
    </v-toolbar-items>
    <v-toolbar-items class="ms-2">
      <v-btn size="small" variant="text">
      <!-- NOTE: no need to implement the auth functionality -->
      <!-- This is just a placeholder -->
        Terminar sessão
        <v-icon size="small" class="ms-1" icon="mdi-logout"></v-icon>
      </v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script setup lang="ts">
import DarkModeSwitch from './DarkModeSwitch.vue'
</script>

<style scoped>
.dei-title:hover {
  background-color: transparent !important;
}
</style>
