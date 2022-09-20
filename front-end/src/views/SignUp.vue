<script setup>
import { ref, computed, onBeforeMount } from "vue";
import { userData } from "../stores/userData.js"
import { useRouter } from 'vue-router'


const myRouter = useRouter()

const myUserData = userData()

const error = ref();
const errorWarning = ref();
const newUser = ref({ name: '', email: '', role: '', password: '', password2: '' });
const roleList = ['ADMIN', 'LECTURER', 'STUDENT']
//role: lecturer admin student

//ระเบิด 01
function checkProperties(obj) {
  //check value of object is not null
  for (let key in obj) {
    if (obj[key] !== null && obj[key] !== "" && obj[key] !== undefined) {
      if (typeof obj[key] == "object") {
        console.log("object1");
        if (!(checkProperties(obj[key]))) {
          console.log("object2++");
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }
  return true;
}

const validateUserName = computed(() => {
  //check length type bra bra brah...
  if (newUser.value.name != undefined) {
    newUser.value.name = newUser.value.name.replace("  ", " ").trimStart();
    if ((newUser.value.name.length > 100)) {
      console.log('name false');
      return false;
    }
  }
  return true;
})

const validatePassword = computed(() => {
  //check length type bra bra brah...
  if (newUser.value.password != undefined) {
    newUser.value.password = newUser.value.password.replace(" ", "")
    console.log(newUser.value.password.length);
    if(newUser.value.password.length == 0){
      return true
    }
    if(newUser.value.password.length >= 8 && newUser.value.password.length <= 14 && newUser.value.password == newUser.value.password2){
      return true
    }

  }
  return false
})

const validateUserEmail = computed(() => {
  newUser.value.email = newUser.value.email.trimStart().trimEnd();
  newUser.value.email = newUser.value.email.toLocaleLowerCase();
  console.log(newUser.value.email)
  return newUser.value.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)
})

const createNewUser = async () => {
  console.log(newUser.value);
  newUser.value.email = newUser.value.email.trimStart().trimEnd();
  newUser.value.name = newUser.value.name.trimStart().trimEnd();
  loaderInsert();
  const status = await myUserData.createNewUser(newUser.value);
  console.log(status, 'tusCheckStauts');
  errorWarning.value = status.error
  loaderEnd();
  if (status.status == 1) {
    //success
    newUser.value = { name: '', email: '', role: '' };
  }
  statusError.value = status.status
  error.value = status.error

  topFunction();
  setTimeout(() => (statusError.value = 0), 2000);
  setTimeout(() => (error.value = ""), 2000);
}

let progress = ref(0);
let isProgress = ref(false);
const loaderInsert = () => {
  isProgress.value = true;
  setTimeout(() => (progress.value = 10), 500);
  setTimeout(() => (progress.value = 20), 500);
  setTimeout(() => (progress.value = 80), 500);
}

const loaderEnd = () => {
  setTimeout(() => (progress.value = 100), 0);
  isProgress.value = false;
}

const statusError = ref(0);
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

const errorInsert = () => {
  statusError.value = -1;
  topFunction();
  setTimeout(() => (statusError.value = 0), 2000);
};

const check = async () => {
  const bool1 = checkProperties(newUser.value);
  const bool2 = validateUserEmail.value
  const bool3 = validateUserName.value
  const bool4 = !myUserData.validateUniqueEmail(0, newUser.value.email)
  const bool5 = !myUserData.validateUniqueName(0, newUser.value.name)
  const bool6 = validatePassword.value

  let er = ""
  if (!bool1) {
    er += "Please Complete Form First\n"
    error.value = er
    errorInsert();
    return ;
  }
  if (!bool2) {
    er += "Email invaild\n"
  }
  if (!bool3) {
    er += "Name > 100\n"
  } if (!bool4) {
    er += "Unique Email\n"
  } if (!bool5) {
    er += "Unique Name\n"
  } if (!bool6) {
    er += "Invalid Password\n"
  }


  //ก็อปมาใช้กับ userdata 5555567566785555
  //0 คือ eventId เราไม่เช็ค เพราะเรา create ไม่มี eventId
  if (bool1 && bool2 && bool3 && bool4 && bool5 && bool6) {
    createNewUser()
    alert('Sign up complete')
  } else {
    error.value = er
    errorInsert();
  }
}



</script>

<template>
  <div class="relative">
    <progress id="busy" v-if="isProgress" class=" progress progress-success h-6 w-56 absolute top-1/3 left-1/2" :value="progress"
      max="100"></progress>
    <div id="content" :style="isProgress ? 'opacity: 0.5;' : 'opacity: 1.0;'">
      <!-- Status -->
      <div class="p-5">
        <div class="alert alert-success shadow-lg" v-if="statusError === 1">
          <div>
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
              viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span>Registered!</span>
          </div>
        </div>

        <div class="alert alert-warning shadow-lg" v-else-if="statusError === 2">
          <div>
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
              viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
            <span>Warning: {{ errorWarning }}</span>
          </div>
        </div>

        <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
          <div>
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
              viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span>Error! Input Value Uncomplete {{ error }}</span>
          </div>
        </div>
      </div>

      <div class="grid lg:grid-cols-2 gap-2">
        <div class="grid gap-5 p-5">
        </div>
      </div>
      <div class="grid gap-5 p-5 ">
        <!-- FORM SIGN UP -->
        <div class="flex justify-center self-center z-10 ">
          <div class="p-12 bg-white mx-auto rounded-2xl w-100">
            <div class="mb-4">
              <h3 class="font-semibold text-2xl text-gray-800">Sign Up !!</h3>
              <p class="text-gray-500">Please insert information.</p>
            </div>
            <div class="space-y-5">
              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Name :
                </label>
                <input maxlength="100" :class="validateUserName && myUserData.validateUniqueName(0, name) ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="Enter your name" v-model="newUser.name" /><br>
                <span>{{ 100 - newUser.name.length }}/100</span>
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Email :<span
                    v-show="!validateUserEmail && newUser.email.length > 0 && myUserData.validateUniqueEmail(0, email)"
                    style="color: red;">*Invalid Email</span>
                </label>
                <input :class="validateUserEmail && myUserData.validateUniqueEmail(0, email) ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="mail@gmail.com" v-model="newUser.email" />
              </div>

              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Role:
                </label>
                <select v-model="newUser.role"
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400">
                  <option v-for="(role, index) in roleList" :key="index" :value="role">
                    {{ role }}
                  </option>
                </select>
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Password :<span
                    v-show="!validatePassword" style="color: red;">*Invalid Password</span>
                </label>
                <input type="password" :class="validatePassword ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="P@$$W0RD" v-model="newUser.password" />
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Confirm Password :</label>
                <input type="password" :class="validatePassword ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="P@$$W0RD" v-model="newUser.password2" />
              </div>

              <div>
                <button type="submit"
                  class="w-full flex justify-center btn-success hover:btn-accent text-gray-100 p-3 hover:text-gray-100 rounded-full tracking-wide font-semibold shadow-lg cursor-pointer transition ease-in duration-500"
                  @click="check();  myRouter.push({ name: 'SignIn' })">Register
                </button>
              </div>
            </div>
            <div class="pt-5 text-center text-gray-400 text-xs">
              <span>
                Donate By 037-7-384-30-0 Bangkok Bank</span>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
#busy {
  position: fixed;
  left: 50%;
  top: 50%;
  z-index: 1000;
}

#busy-holder {
  background: transparent;
  width: 100%;
  height: 100%;
}
</style>