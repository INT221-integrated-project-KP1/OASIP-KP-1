<script setup>
import { ref, computed, onBeforeMount } from "vue";
import { events } from "../stores/eventData.js"
import { categorys } from "../stores/categoryData.js"

const myEvents = events()
const myCategorys = categorys()

const filtering = () => {
    myEvents.resetFilter();
    myEvents.getFilteredEvents();
}


defineEmits(['Fillter'])
//2022-05-24
//2022-05-24T07:00:00Z
//{{new Date(filterList.date)}} 
//2022-05-24T00:00:00Z
</script>
 
<template>

    {{myEvents.filterList.pastOrFutureOrAll}}
    <br>{{myEvents.filterList.date.toString()}}
    <br>{{new Date(myEvents.filterList.date)}}
    <br>{{myEvents.filterList.date==""}}
    <br>{{new Date(myEvents.filterList.date).toDateString()}}
    <div class="card bg-white p-2 m-5">

        <div class="form-control ">
            <div class="flex justify-center">
                <input type="date" class="input input-bordered" v-model="myEvents.filterList.date"/>
                
                <div class="px-5">
                    <select class="select select-bordered " v-model="myEvents.filterList.eventCategoryId">
                        <option disabled selected>Pick category</option>
                        <option v-for="(eventCategory, index) in myCategorys.categoryList" :key="index" :value="eventCategory.id">
                            {{ eventCategory.eventCategoryName }}</option>
                    </select>

                </div>
                <label class="label cursor-pointer">
                    <span class="label-text p-2">Past events</span>
                    <input type="checkbox" v-model="myEvents.filterList.pastOrFutureOrAll" value="past" class="checkbox checkbox-primary" />
                </label>

                <label class="label cursor-pointer">
                    <span class="label-text p-2">Upcoming events</span>
                    <input type="checkbox" v-model="myEvents.filterList.pastOrFutureOrAll" value="future" class="checkbox checkbox-primary" />
                </label>

                <button class="btn btn-square" @click="filtering">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                        stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                </button>


            </div>

        </div>
    </div>

</template>
 
<style>
</style>