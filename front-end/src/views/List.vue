<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import EventList from "../components/EventList.vue";
import {events} from "../stores/eventData.js"

const myEvents = events()

// const events = ref([]);
// GET
const getEvents = async () => {
  try {
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/scheduled?page=${myEvents.page.value}&pageSize=${myEvents.pageSize.value
      }`
    );
    if (res.status === 200) {
      const eventsToAdd = await res.json();
      // events << eventToAdd
      // eventToAdd อันที่โหลดเพิ่ม
      // events ของที่แสดงอยู่
      // เอาอันที่โหลดเพิ่มมาใส่
      eventsToAdd.content.forEach((e) => {
        if (e.id != myEvents.eventList.value.id) {
          myEvents.eventList.value.push(e); 
        }
      });
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log("ERROR: " + err);
  }
};

onBeforeMount(async () => {
  console.log(myEvents.pageSize)
  await getEvents();
});

//PUT
const updateEvent = async (startTime, notes, id) => {
  console.log("startTime: " + startTime)
  console.log("Notes: " + notes)
  console.log("id: " + id)
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({
      eventStartTime: new Date(startTime).toISOString().replace(".000Z", "Z"),
      eventNotes: notes,
    })
  })
  if (res.status === 201) {
    const modEvent = await res.json();
    myEvents.eventList.value = myEvents.eventList.value.map((event) =>
      event.id === modEvent.id
        ? { ...event, eventStartTime: modEvent.eventStartTime, eventNotes: modEvent.eventNotes }
        : event
    )

    console.log('edited successfully')
  } else {
    console.log('error, cannot edit')
  }
}

const removeEvent = async (deleteId) => {
  console.log(deleteId);
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/scheduled/${deleteId}`,
    {
      method: "DELETE",
    }
  );
  if (res.status === 200) {
    myEvents.eventList.value = myEvents.eventList.value.filter((event) => event.id !== deleteId);
    console.log("deleted successfully");
    if (myEvents.value.length <= 8) {
      myEvents.getEvents();
    }

  } else console.log("error, cannot delete data");
};



// const page = ref(0); //page start 0
// const pageSize = ref(9); //default 4

window.onscroll = () => {
  //const bottomOfWindow
  const scrollTop = document.documentElement.scrollTop;
  const innerHeight = window.innerHeight;
  const offsetHeight = document.documentElement.offsetHeight;
  if (scrollTop + innerHeight - offsetHeight >= 0) {
    //bottomOfWindow
    console.log("bottomOfWindow");
    //do tood
    myEvents.pageIncrement();
    myEvents.getEvents();
  }
  console.log("scroll");
  console.log("scroll: " + document.documentElement.scrollTop + window.innerHeight);
  console.log("scroll(Height): " + document.documentElement.offsetHeight);
};
</script>

<template>
  {{myEvents.eventList}}
  <div>
    <div>
      <EventList :events="myEvents.eventList" @deleteEvent="removeEvent" @updateEvent="updateEvent"></EventList>
    </div>


  </div>
</template>

<style scoped>
</style>
