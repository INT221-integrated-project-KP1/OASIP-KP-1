<script setup>
import { ref, computed } from "vue";
import DeleteButton from "../components/deleteButton.vue";
import ShadowEventVue from "./ShadowEvent.vue";
import { useRoute, useRouter } from 'vue-router'
import { categorys } from "../stores/categoryData.js"

const myCategorys = categorys()

defineEmits(["updateEvent"]);

const myRouter = useRouter()
const goBooking = () => {
    myRouter.push({ name: 'Booking' })
}

const selectedCategory = ref({ id: '', eventCategoryName: '', eventCategorDuration: '', eventCategoryDescription: '' });

//GET BY ID
const getEventCategoryById = ((id) => {
    //selectedCategory.value = myCategorys.categoryList.find((ele) => ele.id == id);
    myCategorys.categoryList.foreach((ele) => {
        if(ele.id == id){
            selectedCategory.id = ele.id
            selectedCategory.eventCategoryName = ele.eventCategoryName
            selectedCategory.eventCategorDuration = ele.eventCategorDuration
            selectedCategory.eventCategoryDescription = ele.eventCategoryDescription
        }
    });
})

</script>

<template>
    <div class="flex justify-center">
        <div class="m-10">
            <div id="HaveEvent">
                <div v-if="myCategorys.categoryList.length != 0">
                    <!-- <Fillter /> -->
                    <div id="ListEvent">
                        <div>
                            <ol class="">
                                <div class="grid grid-cols-3 gap-2 ">
                                    <li v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
                                        class="card w-96 bg-base-100 shadow-xl space-x-5">
                                        <div class="card-body bg-white">
                                            <p class="card-title"> Event Category Name : {{
                                                    eventCategory.eventCategoryName
                                            }} </p>
                                            <p>Event Category Description:{{ eventCategory.eventCategoryDescription }}
                                            </p>
                                            <p> Event Duration: {{ eventCategory.eventDuration }} Minutes</p>
                                            <div class="card-actions justify-end">
                                                <label @click="getEventCategoryById(eventCategory.id)" for="my-modal-6"
                                                    class="modal-button duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline">edit</label>
                                            </div>
                                        </div>
                                    </li>
                                    <!-- <ShadowEventVue /> -->
                                </div>
                            </ol>
                            <input type="checkbox" id="my-modal-6" class="modal-toggle " />
                            <div class="modal modal-bottom sm:modal-middle ">
                                <div class="modal-box bg-white">
                                    <p class="py-2">Event Category Name : </p><input
                                        class="border-4 border-primary"
                                        v-model="selectedCategory.eventCategoryName" placeholder="Nameee ..."/><br>



                                    <p class="py-2">Event Category Description : </p><textarea
                                        class="border-4 border-primary" rows="4" cols="50" maxlength="500"
                                        v-model="selectedCategory.eventCategoryDescription" placeholder="Descriptionnnnn ..."></textarea><br>
                                        
                                    <p class="py-2">Event Duration : </p><input max="480" type="number" min="1"
                                        v-model="selectedCategory.eventDuration"/> <br>
                                    <label
                                        class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                                        for="my-modal-6"
                                        @click="myCategorys.updateCategory(selectedCategory)">
                                        Update
                                    </label>
                                    <label for="my-modal-6"
                                        class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else>
                    <div class="card w-96 glass">
                        <figure><img src="../assets/gif2.gif" alt="gif2"></figure>
                        <div class="card-body">
                            <h2 class="card-title">No EventCategory </h2>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>


</template>

<style>
</style>
