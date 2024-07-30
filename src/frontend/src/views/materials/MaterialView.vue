<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Materiais</h2>
    </v-col>
    <v-col cols="auto">
      <CreateMaterialDialog @material-created="getMaterials" />
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    :headers="headers"
    :items="materials"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:[`item.available`]="{ item }">
      <v-icon v-if="item.available" color="success">mdi-check</v-icon>
      <v-icon v-else color="error">mdi-close</v-icon>
    </template>
    <template v-slot:[`item.type`]="{ item }">
      <v-chip>{{ item.type }}</v-chip>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="editMaterial(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deleteMaterial(item)">mdi-delete</v-icon>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import CreateMaterialDialog from '@/views/materials/CreateMaterialDialog.vue'
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type MaterialDto from '@/models/materials/MaterialDto'

const search = ref('')
const headers = [
  { title: 'ID', value: 'id', key: 'id' },
  { title: 'Name', value: 'name', key: 'name ' },
  { title: 'Type', value: 'type', key: 'type' },
  { title: 'Available', value: 'available', key: 'available' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

const materials: MaterialDto[] = reactive([])

const materialTypes = {
  KEY: 'Chave',
  LOCKER: 'Cacifo',
  GENERIC: 'Genérico'
}

getMaterials()
async function getMaterials() {
  materials.splice(0, materials.length)
  RemoteService.getMaterials().then(async (data) => {
    data.forEach((material: MaterialDto) => {
      material.type = materialTypes[material.type as keyof typeof materialTypes]
      materials.push(material)
    })
  })
}

function editMaterial(material: MaterialDto) {
  RemoteService.updateMaterial(material).then(() => {
    getMaterials()
  })
}

function deleteMaterial(material: MaterialDto) {
  RemoteService.deleteMaterial(material).then(() => {
    getMaterials()
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
