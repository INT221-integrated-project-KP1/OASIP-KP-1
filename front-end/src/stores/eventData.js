import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref} from 'vue'
export const events = defineStore('eventListState',() => {
    const eventList = ref([])
    const page = ref(0); //page start 0
    const pageSize = ref(9); //default 4
    const checkLoaded = ref(true); //ดูว่า fetch ครบทุกหน้ายัง
    const tempOverLabCheck = ref([]);
    const boolOverlap = ref(true);

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
    
    //REET FILTER
    const resetFilter = () => {
      page.value = 0;
      eventList.value =[];
      checkLoaded.value = true; //ทำให้กลับมารับค่า fetch ต่อ
    }

// GET
    const getEvents = async () => {
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled?page=${page.value}&pageSize=${pageSize.value}`);
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

  //GET FILTER MORE
  const getEventsFilteredMorePage = async () => {
    const date = filterList.value.date==""?"":(filterList.value.date+'T00:00:00Z')
    const offsetMin = new Date().getTimezoneOffset()

    const filterPastOrFutureOrAll = filterList.value.pastOrFutureOrAll.length!=1?"all":filterList.value.pastOrFutureOrAll[0]
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/filtration?eventCategoryId=${filterList.value.eventCategoryId}&page=${page.value}&pageSize=${pageSize.value}&pastOrFutureOrAll=${filterPastOrFutureOrAll}&date=${date}&offsetMin=${offsetMin}
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


  // GET FILTER
  const getFilteredEvents = async () => {
    let date = filterList.value.date==""?"":(filterList.value.date+'T00:00:00Z')
    const offsetMin = new Date().getTimezoneOffset()
    const filterPastOrFutureOrAll = filterList.value.pastOrFutureOrAll.length!=1?"all":filterList.value.pastOrFutureOrAll[0]

    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/filtration?eventCategoryId=${filterList.value.eventCategoryId}&page=${page.value}&pageSize=${pageSize.value}&pastOrFutureOrAll=${filterPastOrFutureOrAll}&date=${date}&offsetMin=${offsetMin}
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

  // GET ALL LOAD FILTER
  const getEventsFilteredMorePageThatLoaded = async () => {
    const date = filterList.value.date==""?"":(filterList.value.date+'T00:00:00Z')
    const offsetMin = new Date().getTimezoneOffset()

    const filterPastOrFutureOrAll = filterList.value.pastOrFutureOrAll.length!=1?"all":filterList.value.pastOrFutureOrAll[0]
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/filtration?eventCategoryId=${filterList.value.eventCategoryId}&page=0&pageSize=${(pageSize.value)*(page.value+1)}&pastOrFutureOrAll=${filterPastOrFutureOrAll}&date=${date}&offsetMin=${offsetMin}
        `
      );
      if (res.status === 200) {
        const eventsToAdd = await res.json();
        if(eventsToAdd.length < pageSize.value){
          checkLoaded.value = false;
        }
        
        update(eventsToAdd);
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
    const updateEvent = async (startTime, notes, id, duration) => {
    try{
    console.log("startTimeUpdate: " + startTime)
    if(!await validateOverlab(id, 0, startTime.replace(":00", ""), duration)){
      
      return {error:"Overlap", status:-1}
    }
    if(notes.length>500){
      
      return {error : "note more than 500", status:-1}
    }
    if(!validateFutureDate(startTime)){
      return {error: "Future time only $$",status:-1}
    }
    
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
      console.log(modEvent)
      eventList.value = eventList.value.map((event) =>
        event.id === modEvent.id
          ? { ...event, eventStartTime: modEvent.eventStartTime, eventNotes: modEvent.eventNotes }
          : event
      )
      
      console.log('edited successfully')
      return {error:"", status:1};
    } else {
      console.log('error, cannot edit')
      
      return {error: await res.text(), status:-1};
    }
  } catch(err){
    console.log('catchhhhh');
    console.log(err);
    return {error:err, status:-1};
  }
  }

  //fetch to check overlab
  ///api/scheduled/overlabcheck?eventCategoryId=5&startTime=2022-06-08T22:00:00Z
  const getEventsForOverLab = async (eventId, eventCategoryId, startTime) => {
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/scheduled/overlaping?eventId=${eventId}&eventCategoryId=${eventCategoryId}&startTime=${startTime}:00Z`
      );
      if (res.status === 200) {
        const eventsOverLab = await res.json();
        tempOverLabCheck.value = [];
        eventsOverLab.forEach((e)=>{
          tempOverLabCheck.value.push(e);
        })
        console.log("อันนี้ ๆ")
        console.log(tempOverLabCheck.value)
        
      } else {
        
        console.log("error, cannot get data");
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };


  //VALIDATE TIME OVERLAB
  const validateOverlab = async (eventId, categoryId, startTime, duration) => {
    boolOverlap.value = true;
  await getEventsForOverLab(eventId, categoryId, startTime);
    let newMilli = new Date(startTime).getTime(); //new EventStartTime in milli
    let newDurationMilli = duration * 60 * 1000;
    
    console.log("อันนี้ ๆ V.2")
    console.log(tempOverLabCheck.value)
    tempOverLabCheck.value.forEach((value) => {
        console.log("Loop มั้ย")
        let milli = new Date(value.eventStartTime).getTime(); // get eventStartTime in milli
        let durationMilli = value.eventDuration * 60 * 1000;

        console.log("อันน้")
        console.log(newMilli == milli);
        console.log(newMilli < milli + durationMilli);

        if (newMilli + newDurationMilli >= milli && newMilli + newDurationMilli <= milli + durationMilli) {
          //overlab 1+4
            console.log('Overlab 1+4');
            boolOverlap.value = false;
 
          return false; //overlab
        }
        if (newMilli >= milli && newMilli < milli + durationMilli) {
            console.log('Overlab2+4');
            boolOverlap.value = false;

            return false;
        }
        if (newMilli <= milli && newMilli + newDurationMilli >= milli + durationMilli) {
            console.log('Overlab3');
            boolOverlap.value = false;
            return false;
        }
    })
    console.log('return:' + boolOverlap.value);
    return boolOverlap.value;
  

}
 

  

    //VALIDATE FUTURE DATE
    const validateFutureDate = (startTime) => {
    console.log("future");
    console.log(startTime);
    let nowDate = new Date().getTime(); //time in millisecond
    let eventDate = new Date(startTime).getTime();
    console.log(eventDate);

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
    const color = ["bg-accent","bg-primary","bg-secondary","bg-secondary-focus","bg-accent-focus"]

    getEvents();

    return { eventList, update, pageIncrement,pageSizeIncrement ,page, pageSize, getEvents, removeEvent, updateEvent, validateOverlab, validateFutureDate, validateEventNotes, createNewEvent, getFilteredEvents, filterList, getEventsFilteredMorePage, resetFilter, checkLoaded,color, boolOverlap, getEventsFilteredMorePageThatLoaded }
})


if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(events, import.meta.hot))
}