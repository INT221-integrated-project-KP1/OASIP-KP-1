<script setup>
import { ref, onBeforeMount } from 'vue'
import EventList from '../components/EventList.vue'
import Event from '../components/Event.vue'

const events = ref([]);
// GET
const getEvents = async () => {
    try {
        console.log(import.meta.env.URL);

        // const res = await fetch('http://localhost:5000/api/scheduled')
        // const res = await fetch('http://10.4.56.84:5000/scheduled')
// const res = await fetch(`/api/scheduled`)
const res = await fetch(`/api/events`)


        console.log(res.status)
        if (res.status === 200) {
            events.value = await res.json()
            console.log(events.value)
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
        // const res = await fetch(`http://localhost:5000/api/scheduled/${id}`)

        // const res = await fetch(`http://10.4.56.84:3000/api/scheduled/${id}`)
        // const res = await fetch(`/api/scheduled/${id}`)
        const res = await fetch(`/api/events/${id}`)

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


</script>

<template>
    <div>
        <div>
            <!-- The Modal -->
            <div id="myModal" :style="a" class="modal">
                <!-- Modal content -->
                <div class="modal-content">
                    <span @click="showModal()" class="close">&times;</span>
                    <Event :event="event"></Event>
                </div>
            </div>
        </div>
        <EventList :events="events" @selectedEventId="getEventById"></EventList>
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
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>