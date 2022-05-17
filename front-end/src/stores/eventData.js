import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed ,onBeforeMount} from 'vue'
export const events = defineStore('eventListState',() => {
    const eventList = ref([])
    const page = ref(0); //page start 0
    const pageSize = ref(9); //default 4

    //เอาค่าที่ fetch ส่งมาให้ updateEvent
    const update = (updateEvent) => { eventList.value = updateEvent }

    const pageIncrement = () => {page.value++}

    const pageSizeIncrement = () => {pageSize.value+=9}
    
    console.log(eventList+"eventList");
    return { eventList, update, pageIncrement,pageSizeIncrement ,page, pageSize}
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(events, import.meta.hot))
}