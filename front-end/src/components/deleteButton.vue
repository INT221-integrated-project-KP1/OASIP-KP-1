<script setup>
defineEmits(['deleteNote'])

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
<button>Delete</button>
</template>
 
<style>

</style>