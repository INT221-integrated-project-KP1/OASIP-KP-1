<script setup>
defineEmits(['confirmDelete'])

let props = defineProps({
    event: {
        default: [],
        type: Object
    }
})

console.log("TEST");
console.log(props.event)
</script>
 
<template>

    <label for="my-modal" class="btn modal-button">Delete</label>
    <input type="checkbox" id="my-modal" class="modal-toggle" />


    <div class="alert shadow-lg modal">
        <div class="modal-box bg-white">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                class="stroke-info flex-shrink-0 w-6 h-6 font-bold text-lg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span class="py-4">คุณเเน่ใจที่จะลบไหม</span>
            <div>
                <div class="flex-none">
                    <div class="modal-action">
                        <label for="my-modal" class="btn">Deny</label>
                        <label for="my-modal" class="btn" @click=" $emit('confirmDelete')">Accept</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>