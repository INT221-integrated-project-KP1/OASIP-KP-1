import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed ,onBeforeMount} from 'vue'
export const events = defineStore('eventListState',() => {
    const eventList = ref([])
    const page = ref(0); //page start 0
    const pageSize = ref(9); //default 4
    const checkLoaded = ref(true); //ดูว่า fetch ครบทุกหน้ายัง
    const filterList = ref({
      eventCategoryId:0,
      pastOrFutureOrAll:[],
      date:"",
  });

    //เอาค่าที่ fetch ส่งมาให้ updateEvent
    const update = (updateEvents) => { eventList.value = updateEvents }
    const pageIncrement = () => {page.value++}
    const pageSizeIncrement = () => {pageSize.value+=9}

    const addNewEvent = (eventsToAdd) => {
      eventsToAdd.forEach((e) => {
        if (eventList.value.every((e1)=>e.id != e1.id)) {
          eventList.value.push(e); 
        }
      });
    }
    
    console.log(eventList.value+"eventList");
    
    const resetFilter = () => {
      page.value = 0;
      eventList.value =[];
      checkLoaded.value = true; //ทำให้กลับมารับค่า fetch ต่อ
    }

// GET
    const getEvents = async () => {
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled?page=${page.value}&pageSize=${pageSize.value
        }`
      );
      if (res.status === 200) {
        const eventsToAdd = await res.json();
        // events << eventToAdd
        // eventToAdd อันที่โหลดเพิ่ม
        // events ของที่แสดงอยู่
        // เอาอันที่โหลดเพิ่มมาใส่
        //ตัสแก้
        addNewEvent(eventsToAdd.content)
      } else {
        console.log("error, cannot get data");
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };

  const getEventsFilteredMorePage = async () => {
    const date = filterList.value.date==""?"":(filterList.value.date+'T00:00:00Z')
    const offsetMin = new Date().getTimezoneOffset()
    // const filterPastOrFutureOrAll = ref('');
    // if(filterList.value.pastOrFutureOrAll===undefined || filterList.value.pastOrFutureOrAll.length!=1){
    //   filterPastOrFutureOrAll.value = "all"
    // }else{
    //   filterPastOrFutureOrAll.value = filterList.value.pastOrFutureOrAll[0]
    // }
    const filterPastOrFutureOrAll = filterList.value.pastOrFutureOrAll.length!=1?"all":filterList.value.pastOrFutureOrAll[0]
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/filter?eventCategoryId=${filterList.value.eventCategoryId}&page=${page.value}&pageSize=${pageSize.value}&pastOrFutureOrAll=${filterPastOrFutureOrAll}&date=${date}&offsetMin=${offsetMin}
        `
      );
      if (res.status === 200) {
        const eventsToAdd = await res.json();
        if(eventsToAdd.length < pageSize.value){
          checkLoaded.value = false;
        }
        addNewEvent(eventsToAdd)
      } else {
        console.log("error, cannot get data");
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };


  // GET
  const getFilteredEvents = async () => {
    const date = filterList.value.date==""?"":(filterList.value.date+'T00:00:00Z')
    const offsetMin = new Date().getTimezoneOffset()
    const filterPastOrFutureOrAll = filterList.value.pastOrFutureOrAll.length!=1?"all":filterList.value.pastOrFutureOrAll[0]
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/filter?eventCategoryId=${filterList.value.eventCategoryId}&page=${page.value}&pageSize=${pageSize.value}&pastOrFutureOrAll=${filterPastOrFutureOrAll}&date=${date}&offsetMin=${offsetMin}
        `
      );
      if (res.status === 200) {
        const eventsToAdd = await res.json();
        update(eventsToAdd);
      } else {
        console.log("error, cannot get data");
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };

  // GET ALL LOAD
const getEventsAllPageThatLoaded = async () => {
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled?page=0&pageSize=${(pageSize.value)*(page.value+1)}`
      );
      if (res.status === 200) {
        const eventsToAdd = await res.json();
        addNewEvent(eventsToAdd.content)
      } else {
        console.log("error, cannot get data");
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };

  // POST
const createNewEvent = async (event) => {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        bookingName: event.name,
        bookingEmail: event.email,
        eventNotes: event.notes,
        eventStartTime: new Date(event.startTime)
          .toISOString()
          .replace(".000Z", "Z"),
        eventCategory: { id: event.eventCategory.id },
      }),
    });
    if (res.status === 201) {
      console.log("added sucessfully");
      return {error:"", status:1};
    } else {
      console.log("error, cannot be added");
      return {error:await res.text(), status:2};
    }
  } catch (err) {
    // error.value = await res.text()
    // console.log(await res.text())
    console.log(err);
    return {error:err, status:2};
}
  }
  

    //REMOVE
    const removeEvent = async (deleteId) => {
    console.log(deleteId);
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/scheduled/${deleteId}`,
      {
        method: "DELETE",
      }
    );
    if (res.status === 200) {
      eventList.value = eventList.value.filter((event) => event.id !== deleteId);
      console.log("deleted successfully");
      if (eventList.value.length%9 == 8) {
        getEvents();
      }
  
    } else console.log("error, cannot delete data");
    };

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
      eventList.value = eventList.value.map((event) =>
        event.id === modEvent.id
          ? { ...event, eventStartTime: modEvent.eventStartTime, eventNotes: modEvent.eventNotes }
          : event
      )
  
      console.log('edited successfully')
    } else {
      console.log('error, cannot edit')
    }
  }

  //VALIDATE TIME OVERLAB
    function validateOverlab(categoryId, startTime, duration) {
    getEvents();
    let newMilli = new Date(startTime).getTime(); //new EventStartTime in milli
    let newDurationMilli = duration * 60 * 1000;
    let bool = ref(true);
    eventList.value.forEach((value) => {
      if (categoryId == value.eventCategory.id) {
        let milli = new Date(value.eventStartTime).getTime(); // get eventStartTime in milli
        let durationMilli = value.eventDuration * 60 * 1000;

        if (newMilli + newDurationMilli + 60000 > milli && newMilli + newDurationMilli - 60000 < milli + durationMilli) {
          //overlab 1+4
            console.log('Overlab 1+4');
            bool.value = false;
          return false; //overlab
        }
        else if (newMilli + 60000 > milli && newMilli - 60000 < milli + durationMilli) {
          //System.out.println("Overlab2+4");
            console.log('Overlab2+4');
            bool.value = false;
            return false;
          //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
        }
        else if (newMilli - 60000 < milli && newMilli + newDurationMilli + 60000 > milli + durationMilli) {
          //System.out.println("Overlab3");
            console.log('Overlab3');
            bool.value = false;
            return false;
          //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
        }
        }
    })
    console.log('return:' + bool.value);
    return bool.value;
    }

    //VALIDATE FUTURE DATE
    const validateFutureDate = (eventToCheck) => {
    let nowDate = new Date().getTime(); //time in millisecond
    let eventDate = new Date(eventToCheck.startTime).getTime();
    console.log('in Date')
    if (eventDate < nowDate) {
      console.log('future false');
      return false;
    }
    return true;
    };


    //VALIDATE EVENT NOTE
    const validateEventNotes = (eventToCheck) => {
    //undefine ไม่มี length
    console.log("check notes")
    console.log(eventToCheck);
    if (eventToCheck.notes.length > 500) {
        console.log('notes false');
        return false;
    }
    return true;
    }

    getEvents();

    return { eventList, update, pageIncrement,pageSizeIncrement ,page, pageSize, getEvents, removeEvent, updateEvent, getEventsAllPageThatLoaded, validateOverlab, validateFutureDate, validateEventNotes, createNewEvent, getFilteredEvents, filterList, getEventsFilteredMorePage, resetFilter, checkLoaded }
})


if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(events, import.meta.hot))
}