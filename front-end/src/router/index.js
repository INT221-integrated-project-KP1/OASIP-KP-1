import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Booking from '../views/Booking.vue'

const history = createWebHistory('/kp1/')
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {

        path: '/booking',
        name: 'Booking',
        component: Booking
    }
]

const router = createRouter({ history, routes })
export default router
