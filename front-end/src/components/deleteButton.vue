<script setup>
defineEmits(['confirmDelete'])


</script>
 
<template>

    <label for="my-modal"
        class="modal-button duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5 m-4 inline">Delete</label>

    <input type="checkbox" id="my-modal" class="modal-toggle" />

    <div class="modal ">
        <div class="modal-box bg-white">
            <div class="font-bold text-lg">
                <p>คุณเเน่ใจที่จะลบไหม</p>
            </div>

            <div class="modal-action">
                <div class="modal-action">
                    <label for="my-modal"
                        class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                        @click="$emit('confirmDelete')">Accept</label>
                    <label for="my-modal"
                        class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5 m-4 inline">Deny</label>
                </div>
            </div>
        </div>
    </div>

</template>
 
<style>

</style>

