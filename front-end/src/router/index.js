import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Booking from '../views/Booking.vue'
import Welcome from '../views/Welcome.vue'
import List from '../views/List.vue'
import AboutUs from '../views/AboutUs.vue'

const history = createWebHistory('/kp1/')
const routes = [
    {
        path: '/',
        name: 'Welcome',
        component: Welcome
    },
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/booking',
        name: 'Booking',
        component: Booking
    },
    {
        path: '/list',
        name: 'List',
        component: List
    },
    {
        path: '/aboutus',
        name: 'AboutUs',
        component: AboutUs
    }
]

const router = createRouter({ history, routes })
export default router
