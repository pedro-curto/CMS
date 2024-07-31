<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'

const navbarItems = [
  { name: 'Bolsas', path: '/admin', icon: 'mdi-briefcase' },
  { name: 'Candidatos', path: '/candidates', icon: 'mdi-account-group' },
]
</script>
