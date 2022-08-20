import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
export const userData = defineStore('eventListState', () => {
    const userList = ref([])

    // POST
    const createNewUser = async (user) => {
        try {
            const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/`, {
                method: "POST",
                headers: {
                    "content-type": "application/json",
                },
                body: JSON.stringify({
                    name: user.name,
                    email: user.email
                }),
            });
            if (res.status === 201) {
                console.log("added sucessfully");
                return { error: "", status: 1 };
            } else {
                console.log("error, cannot be added");
                return { error: await res.text(), status: 2 };
            }
        } catch (err) {
            console.log(err);
            return { error: err, status: 2 };
        }
    }

    // GET
    const getUsers = async () => {
        try {
          const res = await fetch(
            `${import.meta.env.VITE_BASE_URL}/user`);
          if (res.status === 200) {
            userList.value = res.json()
          } else {
            console.log("error, cannot get data");
          }
        } catch (err) {
          console.log("ERROR: " + err);
        }
      };

    // //REMOVE
    // const removeEvent = async (deleteId) => {
    //     console.log(deleteId);
    //     const res = await fetch(
    //         `${import.meta.env.VITE_BASE_URL}/event/${deleteId}`,
    //         {
    //             method: "DELETE",
    //         }
    //     );
    //     if (res.status === 200) {
    //         eventList.value = eventList.value.filter((event) => event.id !== deleteId);
    //         console.log("deleted successfully");
    //         if (eventList.value.length % 9 == 8) {
    //             getEvents();
    //         }

    //     } else console.log("error, cannot delete data");
    // };

    // //PUT
    // const updateEvent = async (startTime, notes, id, duration) => {
    //     try {
    //         console.log("startTimeUpdate: " + startTime)
    //         if (!await validateOverlab(id, 0, startTime.replace(":00", ""), duration)) {

    //             return { error: "Overlap", status: -1 }
    //         }
    //         if (notes.length > 500) {

    //             return { error: "note more than 500", status: -1 }
    //         }
    //         if (!validateFutureDate(startTime)) {
    //             return { error: "Future time only $$", status: -1 }
    //         }

    //         console.log("startTime: " + startTime)
    //         console.log("Notes: " + notes)
    //         console.log("id: " + id)
    //         const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/${id}`, {
    //             method: 'PUT',
    //             headers: {
    //                 'content-type': 'application/json'
    //             },
    //             body: JSON.stringify({
    //                 eventStartTime: new Date(startTime).toISOString().replace(".000Z", "Z"),
    //                 eventNotes: notes,
    //             })
    //         })
    //         if (res.status === 201) {
    //             const modEvent = await res.json();
    //             console.log(modEvent)
    //             eventList.value = eventList.value.map((event) =>
    //                 event.id === modEvent.id
    //                     ? { ...event, eventStartTime: modEvent.eventStartTime, eventNotes: modEvent.eventNotes }
    //                     : event
    //             )

    //             console.log('edited successfully')
    //             return { error: "", status: 1 };
    //         } else {
    //             console.log('error, cannot edit')

    //             return { error: await res.text(), status: -1 };
    //         }
    //     } catch (err) {
    //         console.log('catchhhhh');
    //         console.log(err);
    //         return { error: err, status: -1 };
    //     }
    // }

    return { userList, createNewUser, getUsers}
})


if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(userData, import.meta.hot))
}