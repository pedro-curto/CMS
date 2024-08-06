<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'

const navbarItems = [
  { name: 'Fellowships', path: '/fellowships', icon: 'mdi-briefcase' },
  { name: 'Candidates', path: '/candidates', icon: 'mdi-account-group' },
]
</script>
