import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref} from 'vue'
import { useRouter } from 'vue-router'
import { cookieData } from "../stores/cookieData.js"
export const ownerData = defineStore('ownerDataState', () => {
    // GET
  const getOwner = async (userId) => {
    try {
      if (cookie.getCookie("role") == "ADMIN") {
        console.log(cookie.getCookie("token"));
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategoryowner/${userId}`, {
          method: "GET",
          headers: {
            "content-type": "application/json",
            Authorization: "Bearer " + cookie.getCookie("token"),
          },
        });
        if (res.status === 200) {
          userList.value = await res.json();
        } else if (res.status === 401) {
          let resText = await res.text();
          if (
            resText
              .toLowerCase()
              .match("please send refresh token to /refresh to refresh token")
          ) {
            //ได้ละ
            console.log("real");
            refreshToken();
            getUsers();
          }
          if (
            resText
              .toUpperCase()
              .match("cannot refresh token. need to login again".toUpperCase())
          ) {
            cookie.setCookie("token", "", -1);
            cookie.setCookie("name", "", -1);
          }
        } else if (res.status === 403) {
          console.log("only admin wtf dog");
        } else {
          console.log("error, cannot get data");
        }
      }
    } catch (err) {
      console.log("ERROR: " + err);
    }
  };
    return {}});

  if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(ownerData, import.meta.hot));
  }