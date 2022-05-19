<script setup>
import { ref, computed, onBeforeMount } from "vue";
import { categorys } from "../stores/categoryData"

const myCategorys = categorys()
const filterList = ref({
    eventCategoryId:0,
    pastOrFutureOrAll:"all",
    date:"",

});

defineEmits(['Fillter'])


</script>
 
<template>

    <div class="card bg-white p-2 m-5">

        <div class="form-control ">
            <div class="flex justify-center">
                <input type="text" placeholder="Search…" class="input input-bordered" />
                <input type="date" class="input input-bordered" v-model="filterList.date"/>
                {{new Date(filterList.date)}}
                <div class="px-5">
                    <select class="select select-bordered ">
                        <option disabled selected>Pick category</option>
                        <option v-for="(EventCategory, index) in myCategorys.categoryList" :key="index">
                            {{ EventCategory.eventCategoryName }}</option>
                    </select>

                </div>
                <label class="label cursor-pointer">
                    <span class="label-text p-2">Past events</span>
                    <input type="checkbox" class="checkbox checkbox-primary" />
                </label>

                <label class="label cursor-pointer">
                    <span class="label-text p-2">Upcoming events</span>
                    <input type="checkbox" class="checkbox checkbox-primary" />
                </label>

                <button class="btn btn-square">
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