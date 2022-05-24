<script setup>
import { ref, } from "vue";
import { events } from "../stores/eventData.js"
import { categorys } from "../stores/categoryData.js"

const myEvents = events()
const myCategorys = categorys()

 const filterList = ref({
      eventCategoryId:-1,
      pastOrFutureOrAll:[],
      date:"",
  });

const reset = () => {
    filterList.value.eventCategoryId = -1;
    filterList.value.pastOrFutureOrAll = [];
    filterList.value.date = "";
    filtering();
}

const filtering = () => {
    for(const key in filterList.value){
        myEvents.filterList[key] = filterList.value[key]
    }
    
    myEvents.resetFilter();
    myEvents.getFilteredEvents();
}

const dateOrPastFuture = ()=>{
    if(filterList.value.pastOrFutureOrAll.length != 0){
        filterList.value.date = "";
    }
}

const dateOrPastFuture2 = ()=>{
    if(filterList.value.date != ""){
        filterList.value.pastOrFutureOrAll.length = [];
    }
}


defineEmits(['Fillter'])
//2022-05-24
//2022-05-24T07:00:00Z
//{{new Date(filterList.date)}}
//2022-05-24T00:00:00Z
</script>
 
<template>
    <div class="card bg-white p-2 m-5">

        <div class="form-control ">
            <div class="lg:flex lg:justify-center hidden">
                <div class="px-5">
                    <select class="select select-bordered " v-model="filterList.eventCategoryId" >
                        <option disabled value=-1>Pick category</option>
                        <option value=0>none</option>
                        <option v-for="(eventCategory, index) in myCategorys.categoryList" :key="index" :value="eventCategory.id">{{ eventCategory.eventCategoryName }}</option>
                    </select>
                </div>

                <input type="date" class="input input-bordered" @change="dateOrPastFuture2" v-model="filterList.date" />

                
                <label class="label cursor-pointer">

                    <input type="checkbox" v-model="filterList.pastOrFutureOrAll" value="past"
                        class="checkbox checkbox-primary" @change="dateOrPastFuture"/>
                    <span class="label-text p-2">Past events</span>
                </label>

                <label class="label cursor-pointer">

                    <input type="checkbox" v-model="filterList.pastOrFutureOrAll" value="future"
                        class="checkbox checkbox-primary" @change="dateOrPastFuture"/>
                    <span class="label-text p-2">Upcoming events</span>
                </label>

                <button class="btn btn-square bg-primary" @click="filtering">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                        stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                </button>

                <button class="btn btn-square mx-2 " @click="reset">
                    RESET
                </button>

            </div>

        </div>
    </div>

</template>
 
<style>

</style>