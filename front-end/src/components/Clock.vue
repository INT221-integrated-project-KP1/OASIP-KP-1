<script>
import { ref,onMounted } from "vue";
//มาร์คไปก็อปมานะ

const hours = ref(0)
const minutes = ref(0)
const seconds = ref(0)

onMounted( setInterval(() => setTime(), 1000)) 

const setTime = () => {
      const date = new Date();
      hours.value = date.getHours();
      minutes.value = date.getMinutes();
      seconds.value  = date.getSeconds();
    }

</script>

<template>
  <div>
    <div>
      <div>{{ hours }} : {{ minutes }} : {{ seconds }}</div>
    </div>
  </div>
</template>

<style >
</style>