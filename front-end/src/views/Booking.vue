<script setup>
import { ref, onBeforeMount, computed, reactive} from 'vue'

let categorys = ref([]);

const getEventCategory = async () => {
    try {
        console.log(import.meta.env.URL);
        // const res = await fetch('http://localhost:5000/api/scheduled')
        // const res = await fetch('http://10.4.56.84:5000/api/scheduled')
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/EventCategory`)
        // const res = await fetch('/api/scheduled')

        console.log(res.status)
        if (res.status === 200) {
            categorys.value = await res.json()
            console.log(categorys.value)
        } else {
            console.log('error, cannot get data')
        }
    }
    catch (err) {
        console.log(err)
    }
}

onBeforeMount(async () => {
    await getEventCategory()
})


let newEvent = reactive({
    name: '', email: '', detail: '', startTime: '', eventCategory: { id: '', duration: '' }
})

function test() {
    console.log(new Date(newEvent.startTime).toISOString())
}

// POST
const createNewEvent = async (newNote) => {
  console.log(newNote)
  const res = await fetch('http://localhost:5000/notes', {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({ noteDetail: newNote })
  })
  if (res.status === 201) {
    const addedNote = await res.json()
    notes.value.push(addedNote)
    console.log('added sucessfully')
  } else console.log('error, cannot be added')
}

</script>
 
<template>
    <div id="BookingForm">
        <div>
            <p><span>Name: <input type="text" v-model="newEvent.name"></span></p>
            <p><span>Email: <input type="text" v-model="newEvent.email"></span></p>
            <p><span>Details: <input type="text" v-model="newEvent.detail"></span></p>
            <p><span>Start Time: <input type="datetime-local" v-model="newEvent.startTime"></span></p>
            <p><span>Event Category:
                    <select v-model="newEvent.eventCategory">
                        <option v-for="(category, index) in categorys" :key="index"
                            :value="{ 'id': category.id, 'duration': category.eventDuration }">
                            {{ category.eventCategoryName }}</option>
                    </select>
                </span>
            </p>
            <p><span>Event durations: <input type="text" disabled v-model="newEvent.eventCategory.duration"></span></p>
            <button @click="test">Book</button>
        </div>
    </div>
</template>
 
<style>
</style>