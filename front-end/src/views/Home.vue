<script setup>
import { ref, onBeforeMount, reactive } from 'vue'
import EventList from '../components/EventList.vue'
import Event from '../components/Event.vue'

const events = ref([]);
const eventsToAdd = reactive([]);
// GET
const getEvents = async () => {
    try {
        console.log(import.meta.env.URL);     
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/?page=${page}&pageSize=&${pageSize}`)
        console.log(res.status)
        if (res.status === 200) {
            eventsToAdd = await res.json()
            console.log(eventsToAdd)
            eventsToAdd.forEach((e)=>{
                events.value.push(e);
            })
        } else {
            console.log('error, cannot get data')
        }
    }
    catch (err) { console.log(err) 
    }
}

const event = ref();
const getEventById = async (id) => {
    try {
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`)
        console.log(res.status)
        if (res.status === 200) {
            event.value = await res.json()
            console.log(event.value)
            showModal()
        } else {
            console.log('error, cannot get data')
        }
    }
    catch (err) { console.log('Error: ', err.message) }
}

const removeEvent = async (deleteId) => {
    console.log(deleteId)
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${deleteId}`, {
        method: 'DELETE'
    })
    if (res.status === 200) {
     events.value = events.value.filter((event) => event.id !== deleteId)
        console.log('deleted successfully')
    } else console.log('error, cannot delete data')
}

onBeforeMount(async () => {
    await getEvents()
})

const a = ref('display:none');
function showModal() {
    if (a.value == "display:block") {
        a.value = "display:none";
    } else {
        a.value = "display:block"
    }
}

let page = ref('0') //page start 0
let pageSize = ref('4') //default 4
window.onscroll = () => {
  let bottomOfWindow = document.documentElement.scrollTop + window.innerHeight === document.documentElement.offsetHeight;

  if (bottomOfWindow) {
    console.log("bottomOfWindow")
    //do tood
    page++;
    getEvents();
  }
};



</script>

<template>
    <div>
        <div>
            <!-- The Modal -->
            <div id="myModal" :style="a" class="modal">
                <!-- Modal content -->
                <div class="modal-content bg-neutral-content">
                    <span @click="showModal()">&times;</span>
                    <Event :event="event"></Event>
                </div>
            </div>
        </div>
        <EventList :events="events" @selectedEventId="getEventById" @deleteEvent="removeEvent"></EventList>
    </div>

</template>
 
<style scoped>
/* The Modal (background) */
.modal {
    display: none;
    /* Hidden by default */
    position: fixed;
    /* Stay in place */
    z-index: 1;
    /* Sit on top */
    padding-top: 100px;
    /* Location of the box */
    left: 0;
    top: 0;
    width: 100%;
    /* Full width */
    height: 100%;
    /* Full height */
    overflow: auto;
    /* Enable scroll if needed */
    background-color: rgb(0, 0, 0);
    /* Fallback color */
    background-color: rgba(0, 0, 0, 0.4);
    /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

/* The Close Button */

</style>