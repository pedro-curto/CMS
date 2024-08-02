import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MaterialView from '@/views/materials/MaterialView.vue'
import CandidateView from '@/views/candidate/CandidateView.vue'
import FellowshipView from '@/views/fellowship/FellowshipView.vue'
import FellowshipDetails from '@/views/fellowship/FellowshipDetails.vue'
import FellowshipCandidates from '@/views/fellowship/FellowshipCandidatesManagement.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: HomeView
    },
    {
      path: '/contact',
      name: 'contact',
      component: HomeView
    },
    {
      path: '/materials',
      name: 'materials',
      component: MaterialView
    },
    {
      path: '/candidates',
      name: 'candidates',
      component: CandidateView
    },
    {
      path: '/fellowships',
      name: 'fellowships',
      component: FellowshipView
    },
    {
      path: '/fellowship/:id',
      name: 'fellowshipDetails',
      component: FellowshipDetails,
      props: true,
    },
    {
      path: '/fellowship/:id/candidateManagement',
      name: 'fellowshipCandidates',
      component: FellowshipCandidates,
      props: true
    },
    {
      path: '/register',
      name: 'register',
      component: HomeView
    },
    {
      path: '/profile',
      name: 'profile',
      component: HomeView
    },
    {
      path: '/admin',
      name: 'admin',
      component: HomeView
    }
  ]
})

export default router
