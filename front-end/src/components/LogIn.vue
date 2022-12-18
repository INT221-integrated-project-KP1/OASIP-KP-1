<script setup>
import { ref, computed } from "vue"
import { useRoute, useRouter } from "vue-router"
import { cookieData } from "../stores/cookieData.js"
import { userData } from "../stores/userData.js"
import { msData } from "../stores/loginMS.js"

const cookie = cookieData()
const userStore = userData()
const ms = msData()

const { params } = useRoute()
const myRouter = useRouter()

const loginuser = ref({ email: "", password: "" })

const matchstatus = ref("")

const ValidateCheckEmail = (login) => {
	if (
		!login.email.match(
			/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
		)
	) {
		matchstatus.value = matchstatus.value + "Email is not valid ;"
		return true
	}
	return false
}

const ValidateCheckPassword = (login) => {
	if (login.password.length < 8 || login.password.length > 14) {
		matchstatus.value = matchstatus.value + "Password < 0 or Password > 18 ;"
		return true
	}
	return false
}

//login
const MatchingCheck = async (login) => {
	login.email = login.email.trimStart().trimEnd()
	if (ValidateCheckPassword(login) || ValidateCheckEmail(login)) {
		statusError.value = 2
		topFunction()
		setTimeout(() => (statusError.value = 0), 2000)
	} else {
		try {
			loaderInsert()
			const res = await fetch(`${import.meta.env.VITE_BASE_URL}/jwt/login`, {
				method: "POST",
				headers: {
					"content-type": "application/json",
				},
				body: JSON.stringify({
					email: login.email,
					password: login.password,
				}),
			})
			loaderEnd()
			if (res.status === 200) {
				const objectJson = await res.json()
				////
				for (let i in Object.keys(objectJson)) {
					cookie.setCookie(
						Object.keys(objectJson)[i],
						Object.values(objectJson)[i],
						7
					)
				}
				let jsonFromToken = userStore.parseJwt(cookie.getCookie("token"))
				cookie.setCookie("name", jsonFromToken.name, 7)
				cookie.setCookie("role", jsonFromToken.role, 7)
				cookie.setCookie("email", jsonFromToken.sub, 7)

				////
				matchstatus.value = "Sucesss"
				statusError.value = 1
				topFunction()
				setTimeout(() => (statusError.value = 0), 2000)
				userStore.userList = []
				userStore.getUsers()
				myRouter.push({ name: "Home" })
			} else {
				matchstatus.value = await res.text()
				statusError.value = 2
				topFunction()
				setTimeout(() => (statusError.value = 0), 2000)
			}
		} catch (err) {
			console.log(err)
			// errorInsert()
			alert(err)
		}
	}
}

//login
const getTokenFromOurServer = async () => {
	console.log("getTokenFromOurServer")
	const tokenMS = cookie.getCookie(
		"msal.b8588d84-fe40-487c-9948-7b70a676916c.idtoken"
	)
	try {
		loaderInsert()
		const res = await fetch(`${import.meta.env.VITE_BASE_URL}/jwt/loginms`, {
			method: "POST",
			headers: {
				"content-type": "text/plain",
			},
			body: tokenMS,
		})
		loaderEnd()
		if (res.status === 200) {
			const objectJson = await res.json()
			////
			for (let i in Object.keys(objectJson)) {
				cookie.setCookie(
					Object.keys(objectJson)[i],
					Object.values(objectJson)[i],
					7
				)
			}
			let jsonFromToken = userStore.parseJwt(cookie.getCookie("token"))
			cookie.setCookie("name", jsonFromToken.name, 7)
			cookie.setCookie("role", jsonFromToken.role, 7)
			cookie.setCookie("email", jsonFromToken.sub, 7)

			////
			matchstatus.value = "Sucesss"
			statusError.value = 1
			topFunction()
			setTimeout(() => (statusError.value = 0), 2000)
			userStore.userList = []
			userStore.getUsers()
			myRouter.push({ name: "Home" })
		} else {
			matchstatus.value = await res.text()
			statusError.value = 2
			topFunction()
			setTimeout(() => (statusError.value = 0), 2000)
		}
	} catch (err) {
		console.log(err)
		// errorInsert()
		alert(err)
	}
}

const noIsFun = () => {
	alert("This version doesn't have this function.")
}

const goSignUp = () => {
	myRouter.push({ name: "SignUp" })
}

const validateEmail = computed(() => {
	loginuser.value.email = loginuser.value.email.trimStart().trimEnd()
	console.log(loginuser.value.email)
	return loginuser.value.email.match(
		/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
	)
})

const statusError = ref(0)
function topFunction() {
	document.body.scrollTop = 0
	document.documentElement.scrollTop = 0
}

const errorInsert = () => {
	statusError.value = -1
	topFunction()
	setTimeout(() => (statusError.value = 0), 2000)
}

let progress = ref(0)
let isProgress = ref(false)
const loaderInsert = () => {
	isProgress.value = true
	setTimeout(() => (progress.value = 10), 500)
	setTimeout(() => (progress.value = 20), 500)
	setTimeout(() => (progress.value = 80), 500)
}
const loaderEnd = () => {
	setTimeout(() => (progress.value = 100), 0)
	isProgress.value = false
}

const loginMS = async () => {
	await ms.loginMS()
	await getTokenFromOurServer()
}
</script>

<template class="antialiased bg-gradient-to-br from-green-100 to-white">
	<div class="p-5">
		<div class="alert alert-success shadow-lg" v-if="statusError === 1">
			<div>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					class="stroke-current flex-shrink-0 h-6 w-6"
					fill="none"
					viewBox="0 0 24 24"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						stroke-width="2"
						d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
					/>
				</svg>
				<span>{{ matchstatus }}</span>
			</div>
		</div>

		<div class="alert alert-warning shadow-lg" v-else-if="statusError === 2">
			<div>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					class="stroke-current flex-shrink-0 h-6 w-6"
					fill="none"
					viewBox="0 0 24 24"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						stroke-width="2"
						d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
					/>
				</svg>
				<span>Not Match : {{ matchstatus }}</span>
			</div>
		</div>

		<div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
			<div>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					class="stroke-current flex-shrink-0 h-6 w-6"
					fill="none"
					viewBox="0 0 24 24"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						stroke-width="2"
						d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
					/>
				</svg>
				<span>Error! For server {{ error }}</span>
			</div>
		</div>
	</div>

	<div class="relative">
		<!-- หลอดพลังรอโหลด -->
		<progress
			id="busy"
			v-if="isProgress"
			class="progress progress-success h-6 w-56 absolute top-1/3 left-1/2"
			:value="progress"
			max="100"
		></progress>

		<!-- Content -->
		<div id="content" :style="isProgress ? 'opacity: 0.5;' : 'opacity: 1.0;'">
			<div class="container px-6 mx-auto">
				<div
					class="flex flex-col text-center md:text-left md:flex-row h-screen justify-evenly md:items-center"
				>
					<div class="flex flex-col w-full">
						<div>
							<svg
								class="w-20 h-20 mx-auto md:auto fill-stroke text-gray-800"
								fill="none"
								stroke="currentColor"
								viewBox="0 0 24 24"
								xmlns="http://www.w3.org/2000/svg"
							>
								<path
									stroke-linecap="round"
									stroke-linejoin="round"
									stroke-width="2"
									d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"
								></path>
							</svg>
						</div>
						<h1 class="text-5xl text-gray-800 font-bold mx-auto md:auto">
							OASIP-KP1 LogIn
						</h1>
						<div class="mx-auto md:auto my-10">
							<img
								src="../assets/Anya.jpg"
								alt="logologin"
								width="300"
								height="300"
							/>
						</div>
					</div>

					<div class="w-full md:w-full lg:w-9/12 mx-auto md:mx-0">
						<div
							class="bg-white p-10 flex flex-col w-full shadow-xl rounded-xl"
						>
							<h2 class="text-2xl font-bold text-gray-800 text-left mb-5">
								Sign In
							</h2>
							<form action="" class="w-full">
								<div id="input" class="flex flex-col w-full my-5">
									<label for="email" class="text-gray-500 mb-2"
										>Email :<span
											v-show="!validateEmail && loginuser.email.length > 0"
											style="color: red"
											>*Invalid Email</span
										>
									</label>
									<input
										type="text"
										id="username"
										placeholder="Please insert your email"
										class="appearance-none border-2 border-gray-100 rounded-lg px-4 py-3 placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-green-600 focus:shadow-lg"
										v-model="loginuser.email"
									/>
								</div>
								<div id="input" class="flex flex-col w-full my-5">
									<label for="password" class="text-gray-500 mb-2"
										>Password
										<span
											v-show="
												loginuser.password.length < 8 ||
												loginuser.password.length > 18
											"
											style="color: red"
											>*Invalid Password</span
										>
									</label>
									<input
										type="password"
										id="password"
										placeholder="Please insert your password"
										class="appearance-none border-2 border-gray-100 rounded-lg px-4 py-3 placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-green-600 focus:shadow-lg"
										v-model="loginuser.password"
									/>
								</div>
								<div id="button" class="flex flex-col w-full my-5 ">
									<div class="grid grid-cols-2 gap-7">
                                        <button
											@click="loginMS"
											class="bg-base-300 hover:bg-secondary transition-all text-black font-bold py-2 px-4 gap-4 rounded-md flex"
										>
											<svg
												xmlns="http://www.w3.org/2000/svg"
												viewBox="0 0 23 23"
												height="40"
											>
												<path fill="#f3f3f3" d="M0 0h23v23H0z" />
												<path fill="#f35325" d="M1 1h10v10H1z" />
												<path fill="#81bc06" d="M12 1h10v10H12z" />
												<path fill="#05a6f0" d="M1 12h10v10H1z" />
												<path fill="#ffba08" d="M12 12h10v10H12z" />
											</svg>
											Login with Microsoft
										</button>
										<button
											type="button"
											class="bg-base-300 w-full py-4 bg-green-600 rounded-lg text-green-100 hover:bg-secondary transition-all"
										>
											<div class="flex flex-row items-center justify-center ">
												<div class="mr-2">
													<svg
														class="w-6 h-6"
														fill="none"
														stroke="currentColor"
														viewBox="0 0 24 24"
														xmlns="http://www.w3.org/2000/svg"
													>
														<path
															stroke-linecap="round"
															stroke-linejoin="round"
															stroke-width="2"
															d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"
														></path>
													</svg>
												</div>
												<div
													class="font-bold "
													@click="MatchingCheck(loginuser)"
												>
													Sign in
												</div>
											</div>
										</button>
										
									</div>

									<div class="flex justify-evenly mt-5">
										<a
											href="#"
											class="w-full text-center font-medium text-gray-500 hover:text-secondary transition-all"
											@click="noIsFun"
											>Recover password!</a
										>
										<a
											href="#"
											class="w-full text-center font-medium text-gray-500 hover:text-secondary transition-all"
											@click="goSignUp"
											>Sign up!</a
										>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style></style>
