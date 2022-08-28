<script setup>
import { ref, computed } from "vue";
import { categorys } from "../stores/categoryData.js"
import { events } from "../stores/eventData.js"

const myCategorys = categorys()
const myEvents = events()

defineEmits(["updateEvent"]);



const selectedCategory = ref({ id: '', eventCategoryName: '', eventDuration: '', eventCategoryDescription: '' });

//GET BY ID
const getEventCategoryById = ((id) => {
  const temp = myCategorys.categoryList.find((ele) => ele.id == id);
  selectedCategory.value.id = temp.id
  selectedCategory.value.eventCategoryName = temp.eventCategoryName
  selectedCategory.value.eventDuration = temp.eventDuration
  selectedCategory.value.eventCategoryDescription = temp.eventCategoryDescription
})

const validateEventDuration = computed(() => {
  if (selectedCategory.value.eventDuration > 480) {
    selectedCategory.value.eventDuration = 480;
  }
  else if (selectedCategory.value.eventDuration < 0) {
    selectedCategory.value.eventDuration = 0;
  }
})

const validateEventName = computed(() => {
  //check length type bra bra brah...
  if (selectedCategory.eventCategoryName != undefined) {
    nselectedCategory.eventCategoryName = selectedCategory.eventCategoryName.replace("  ", " ").trimStart();
    if ((selectedCategory.eventCategoryName.length > 100)) {
      console.log('name falsee');
      return false;
    }
  }
  return true;
})

const alertError = () => { alert("dawd") }

const statusError = ref(0)
const error = ref('')
const EditCategory = async (Category) => {
  error.value = ``
  if (myCategorys.validateEventName(selectedCategory.value)) {
    error.value += "Unique eventName only $$"
    console.log("uniqueeeeeeeeeeeeeeeeeeee")
    return errorInsert()
  }
  if(!validateEventName.value){
    error.value += "categoryname Length must be < 100 $$"
    return errorInsert()
  }
  if (selectedCategory.value.eventCategoryDescription.length > 500) {
    error.value += "Length of EventName > 500"
    console.log('ssssss')
    return errorInsert()
  }
  let status = await myCategorys.updateCategory(Category)
  statusError.value = status
  console.log(status)
  topFunction()
  setTimeout(() => (statusError.value = 0), 2000);
}

const errorInsert = () => {
  topFunction()
  statusError.value = -1
  setTimeout(() => (statusError.value = 0), 2000);
};
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}


</script>

<template>
  <div class="flex justify-center">
    <div class="m-10">

      <div id="HaveEvent">
        <div class="p-5">
          <div class="alert alert-success shadow-lg" v-if="statusError === 1">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <span>Your Edit has been confirmed!</span>
            </div>
          </div>

          <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <span>{{ error }}</span>
            </div>
          </div>
          <div class="alert alert-error shadow-lg" v-else-if="statusError === -2">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <span>Can't Update</span>
            </div>
          </div>

          <div v-if="myCategorys.categoryList.length != 0">
            <div id="ListEvent">
              <div>
                <ol class="">
                  <div class="grid xl:grid-cols-3 lg:grid-cols-2 grid-cols-1 gap-10 justify-items-center">
                    <li v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
                      class="card w-96 bg-base-100 shadow-xl space-x-5 
                                        transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">
                      <div class="card-body bg-white">
                        <p class="card-title rounded-md p-6" :class="myEvents.color[eventCategory.id - 1]"> Event Category
                          Name : {{
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
                    <span v-show="myCategorys.validateEventName(selectedCategory)" style="color: red;">*Name
                      unique</span>
                    <span v-show="!validateEventName" style="color: red;">*Name > 100</span>
                    <p class="py-2">Event Category Name : </p><input maxlength="100" :class="!myCategorys.validateEventName(selectedCategory) ?
                      ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                      : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                    " v-model="selectedCategory.eventCategoryName" placeholder="Nameee ..." /><br>
                    <span>{{ 100 - selectedCategory.eventCategoryName.length }}/100</span>


                    <p class="py-2">Event Category Description : </p><textarea class="border-4 border-primary" rows="4"
                      cols="50" maxlength="500" v-model="selectedCategory.eventCategoryDescription"
                      placeholder="Descriptionnnnn ..."></textarea><br>
                    <span>{{ 500 - selectedCategory.eventCategoryDescription.length }}/500</span>
                    <p class="py-2">Event Duration : </p><input @input="validateEventDuration" max="480" type="number"
                      min="1" class="border-4 border-primary" v-model="selectedCategory.eventDuration" /> <br>
                    <div class="modal-action">
                      <label
                        class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                        for="my-modal-6" @click="
                        EditCategory(selectedCategory)">
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
  </div>

</template>

<style>
</style>
