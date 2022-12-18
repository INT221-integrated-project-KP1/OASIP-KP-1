import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { cookieData } from "./cookieData.js"
import { UserAgentApplication } from "msal";


export const msData = defineStore('msDataState', () => {
  const cookie = cookieData()
  //copy from azure
  const msalConfig = {
    auth: {
      clientId: "b8588d84-fe40-487c-9948-7b70a676916c",
      authority: "https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d",
      redirectURI: "http://localhost:3000/"
    },
    cache: {
      cacheLocation: "localStorage", // This configures where your cache will be stored
      storeAuthStateInCookie: true,
      popUp: true // Set this to "true" if you are having issues on IE11 or Edge
    }
  };


  var requestObj = {
    scopes: ["user.read"]
  };

  var myMSALObj = new UserAgentApplication(msalConfig);

  async function loginMS() {
    console.log("test");
    var authResult = await myMSALObj.loginPopup(requestObj);
    const cookieMS = cookie.getCookie("msal.b8588d84-fe40-487c-9948-7b70a676916c.idtoken")
    alert("success")
    console.log(authResult);
    console.log("test");
    console.log(authResult.account);


    return authResult.account;
  };

  var getAccount = async () => {
    var account = await myMSALObj.getAccount();
    return account;
  };

  var logoffMS = () => {
    myMSALObj.logout();
  };




  return { loginMS, logoffMS, getAccount }
}
)

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(msData, import.meta.hot))
}