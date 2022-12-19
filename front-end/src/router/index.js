import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Booking from '../views/Booking.vue'
import Welcome from '../views/Welcome.vue'
import List from '../views/List.vue'
import AboutUs from '../views/AboutUs.vue'
import ListCategory from '../views/ListCategory.vue'
import SignUp from '../views/SignUp.vue'
import ListUser from '../views/ListUser.vue'
import SignIn from '../views/SignIn.vue'
import BlindedEvent from '../views/BlindedEvent.vue'
import listEventCategoryOwner from '../views/listEventCategoryOwner.vue'

const history = createWebHistory('/kp1')
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
        path: '/ListCategory',
        name: 'ListCategory',
        component: ListCategory
    }, {
        path: '/SignUp',
        name: 'SignUp',
        component: SignUp
    }, {
        path: '/ListUser',
        name: 'ListUser',
        component: ListUser
    },
    {
        path: '/aboutus',
        name: 'AboutUs',
        component: AboutUs
    },
    {
        path: '/SignIn',
        name: 'SignIn',
        component: SignIn
    },
    {
        path: '/BlindedEvent',
        name: 'BlindedEvent',
        component: BlindedEvent
    },
    {
        path: '/listEventCategoryOwner',
        name: 'listEventCategoryOwner',
        component: listEventCategoryOwner
    }
]

const router = createRouter({ history, routes })
export default router
