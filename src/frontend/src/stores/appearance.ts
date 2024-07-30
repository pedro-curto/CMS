import { defineStore } from 'pinia'



export const useAppearanceStore = defineStore('appearance', {
  state: () => ({ currentTheme: 'light', isDarkTheme: false}),
  actions: {
    toggleTheme() {
      this.currentTheme = this.currentTheme === 'light' ? 'dark' : 'light'
      this.isDarkTheme = this.currentTheme === 'dark'
    }
  },
  persist: true
})
