import { defineStore, acceptHMRUpdate } from "pinia";
import { computed, ref } from "vue";
import { cookieData } from "../stores/cookieData.js";
import { useRouter } from 'vue-router'
import router from "../router/index.js"; 

export const userData = defineStore("userDataState", () => {
  const { params } = useRouter();
  const myRouter = router;

  const userList = ref([]);
  const cookie = cookieData();
  const validateUniqueName = (id, name) => {
    if (id && name != undefined) {
      return userList.value
        .filter((user) => user.id !== id)
        .some((user) => user.name.toLowerCase() == name.toLowerCase());
    }
  };

  const validateUniqueEmail = (id, email) => {
    if (id && email != undefined) {
      return userList.value
        .filter((user) => user.id !== id)
        .some((user) => user.email.toLowerCase() == email.toLowerCase());
    }
  };

  // POST
  const createNewUser = async (user) => {
    try {
      const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user`, {
        method: "POST",
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer " + cookie.getCookie("token"),
        },
        body: JSON.stringify({
          name: user.name,
          email: user.email,
          role: user.role,
          password: user.password,
        }),
      });

      if (res.status === 201) {
        console.log("added sucessfully");
        getUsers();
        alert('Sign up complete')
        router.push('/Home')
        return { error: "", status: 1 };
      } else {
        console.log("error, cannot be added");
        return { error: await res.text(), status: 2 };
      }
    } catch (err) {
      console.log(err);
      myRouter.push({ name: "SignIn" });
      return { error: err, status: 2 };
    }
  };

  // GET
  const getUsers = async () => {
    try {
      if (cookie.getCookie("role") == "ADMIN") {
        console.log(cookie.getCookie("token"));
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user`, {
          method: "GET",
          headers: {
            "content-type": "application/json",
            Authorization: "Bearer " + cookie.getCookie("token"),
          },
        });
        if (res.status === 200) {
          userList.value = await res.json();
        } else if (res.status == 401) {
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
          else {            myRouter.push({ name: "SignIn" });
        }
        } else if (res.status === 403) {
          console.log("only admin wtf dog");
        } else {
          console.log("error, cannot get data");

        }
      }
    } catch (err) {
      console.log("ERROR: " + err);
      myRouter.push({ name: "SignIn" });

    }
  };

  //REMOVE
  const removeUser = async (deleteId) => {
    console.log(deleteId);
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/user/${deleteId}`,
      {
        method: "DELETE",
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer " + cookie.getCookie("token"),
        },
      }
    );
    if (res.status === 200) {
      userList.value = userList.value.filter((user) => user.id !== deleteId);
      console.log("deleted successfully");
      if (userList.value.length % 9 == 8) {
        getUsers();
      }
    } else if (res.status === 401) {
      let resText = await res.text();
      if (resText.toUpperCase().match("TOKENEXPIRED")) {
        //ได้ละ
        console.log("real");
        refreshToken();
      }
      if (
        resText
          .toUpperCase()
          .match("cannot refresh token. need to login again".toUpperCase())
      ) {
        cookie.setCookie("token", "", -1);
        cookie.setCookie("name", "", -1);
      }
    } else {
      console.log("error, cannot delete data");
      myRouter.push({ name: "SignIn" });

    }
  };

  // //PUT
  const updateUser = async (updatedUser) => {
    try {
      const res = await fetch(
        `${import.meta.env.VITE_BASE_URL}/user/${updatedUser.id}`,
        {
          method: "PUT",
          headers: {
            "content-type": "application/json",
            Authorization: "Bearer " + cookie.getCookie("token"),
          },
          body: JSON.stringify({
            id: updatedUser.id,
            name: updatedUser.name,
            email: updatedUser.email,
            role: updatedUser.role,
          }),
        }
      );
      if (res.status === 201) {
        const modUser = await res.json();
        console.log(modUser);
        userList.value = userList.value.map((user) =>
          user.id === modUser.id
            ? {
                ...user,
                name: modUser.name,
                email: modUser.email,
                role: modUser.role,
              }
            : user
        );

        console.log("edited successfully");
        return 1;
      } else if (res.status === 401) {
        let resText = await res.text();
        if (resText.toUpperCase().match("TOKENEXPIRED")) {
          //ได้ละ
          console.log("real");
          refreshToken();
        }
        if (
          resText
            .toUpperCase()
            .match("cannot refresh token. need to login again".toUpperCase())
        ) {
          cookie.setCookie("token", "", -1);
          cookie.setCookie("name", "", -1);
        }
      } else {
        console.log("error, cannot edit");

        return -1;
      }
    } catch (err) {
      console.log("catchhhhh");
      console.log(err);
      myRouter.push({ name: "SignIn" });

      return -1;
    }
  };
  //refreshtoken

  const refreshToken = async () => {
    try {
      console.log(cookie.getCookie("token"));
      const res = await fetch(`${import.meta.env.VITE_BASE_URL}/jwt/refresh`, {
        method: "GET",
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer " + cookie.getCookie("refreshtoken"),
          isRefreshToken: true,
        },
      });
      if (res.status === 200) {
        const objectJson = await res.json();
        ////
        console.log("setcookie test");
        cookie.setCookie("token", objectJson.token, 7);
        return true;
      } else if (res.status === 403) {
        //refresh ไม่ได้ ให้ login ใหม่ครับ
        let resJson = await res.json();
        if (
          resJson.message
            .toUpperCase()
            .match("cannot refresh token. need to login again".toUpperCase) || resJson.message.toUpperCase().match("Claims == null, Cant't Refresh".toUpperCase())
        ) {
          cookie.setCookie("token", "", -1);
          cookie.setCookie("refreshtoken", "", -1);
          cookie.setCookie("name", "", -1);
          cookie.setCookie("role", "", -1);
          alert("cannot refresh token. need to login again");
        }
        return false;
      } else {
        console.log("error, cannot get data");
        myRouter.push({ name: "SignIn" });

      }
    } catch (err) {
      console.log("ERROR: " + err);
      myRouter.push({ name: "SignIn" });
    }
  };

  function parseJwt(token) {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(
      window
        .atob(base64)
        .split("")
        .map(function (c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join("")
    );
    return JSON.parse(jsonPayload);
  }

  const isLogin = () => {
    let token = cookie.getCookie("token");
    let refreshTokenn = cookie.getCookie("refreshtoken");
    if (new Date(parseJwt(token).exp * 1000 > new Date())) {
      return true;
    } else if (new Date(parseJwt(refreshTokenn).exp * 1000 > new Date())) {
      if (refreshToken()) {
        return true;
      }
    }
    return myRouter.push({ name: "Welcome" });
  };

  getUsers();
  return {
    userList,
    createNewUser,
    getUsers,
    removeUser,
    updateUser,
    validateUniqueName,
    validateUniqueEmail,
    refreshToken,
    parseJwt,
    isLogin,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(userData, import.meta.hot));
}
