<script setup>
import { ref} from "vue";
import { useRouter } from 'vue-router'
import { categorys } from "../stores/categoryData.js"
import { events } from "../stores/eventData.js"

const myCategorys = categorys()
const myEvents = events()

defineEmits(["updateEvent"]);

const myRouter = useRouter()
const goBooking = () => {
    myRouter.push({ name: 'Booking' })
}

const selectedCategory = ref({ id: '', eventCategoryName: '', eventDuration: '', eventCategoryDescription: '' });

//GET BY ID
const getEventCategoryById = ((id) => {
    const temp = myCategorys.categoryList.find((ele) => ele.id == id);
    selectedCategory.value.id = temp.id
    selectedCategory.value.eventCategoryName = temp.eventCategoryName
    selectedCategory.value.eventDuration = temp.eventDuration
    selectedCategory.value.eventCategoryDescription = temp.eventCategoryDescription
})

const alertError = () =>{alert("dawd")}


</script>

<template>
    <div class="flex justify-center">
        <div class="m-10">
            <div id="HaveEvent">
                <div v-if="myCategorys.categoryList.length != 0">
                    <div id="ListEvent">
                        <div>
                            <ol class="">
                                <div class="grid xl:grid-cols-3 lg:grid-cols-2 md:grid-cols-1 gap-10 justify-items-center">
                                    <li v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
                                        class="card w-96 bg-base-100 shadow-xl space-x-5 
                                        transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">
                                        <div class="card-body bg-white">
                                            <p class="card-title rounded-md p-6" :class="myEvents.color[eventCategory.id-1]" > Event Category Name : {{
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
                                </div>
                            </ol>
                            
                            <!-- modal -->
                            <input type="checkbox" id="my-modal-6" class="modal-toggle " />
                            <div class="modal modal-bottom sm:modal-middle ">
                                <div class="modal-box bg-white">
                                    <span v-show="myCategorys.validateEventName(selectedCategory)" style="color: red;">*Name unique</span>
                                    <p class="py-2">Event Category Name : </p><input
                                    :class="!myCategorys.validateEventName(selectedCategory) ?
                                    ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                                     : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                                     "
                                        v-model="selectedCategory.eventCategoryName" placeholder="Nameee ..."/><br>



                                    <p class="py-2">Event Category Description : </p><textarea
                                        class="border-4 border-primary" rows="4" cols="50" maxlength="500"
                                        v-model="selectedCategory.eventCategoryDescription" placeholder="Descriptionnnnn ..."></textarea><br>
                                        
                                    <p class="py-2">Event Duration : </p><input max="480" type="number" min="1"
                                        v-model="selectedCategory.eventDuration"/> <br>
                                                   <div class="modal-action">
                                                          <label
                                        class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                                        for="my-modal-6"
                                        @click="!myCategorys.validateEventName(selectedCategory) ? myCategorys.updateCategory(selectedCategory):alertError()">
                                        Update
                                    </label>
                                    <label for="my-modal-6"
                                        class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                                        </div>                 
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
