import{ defineStore,  acceptHMRUpdate}  from'pinia'
import{ ref  , computed}  from'vue'
exportconstuseCounter= defineStore('eventListState', () =>{
    const eventsState =  ref ([])
    return{ eventsState }

})
    if (import.meta.hot ) {
        import.meta.hot.accept(acceptHMRUpdate(useCounter, import.meta.hot  ))
    }