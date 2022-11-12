import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref} from 'vue'
import { cookieData } from "../stores/cookieData.js"

export const fileData = defineStore("fileState", () => {
    const myCookie = cookieData();

//upfile นะ
const uploadFile = async (newEvent) => {

    if( 
        !(document.getElementById("fileupload").files[0].size /1024/1024 > 10) 
        ){
        let data = new FormData();
        data.append(
        "file",document.getElementById("fileupload").files[0],newEvent.file
        )
        alert(newEvent.file)
      
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/file/upload`,{
          method: "POST", 
          headers: {
              Authorization: "Bearer " + myCookie.getCookie("token"),
            },
          body: data
        })
        alert(res.status)
        if (res.status === 200) {
          alert("uploaded");
        } else if (res.status === 404) {
          let resText = await res.text();
          if (resText.toUpperCase().match("TOKENEXPIRED")) {
            //ได้ละ
            console.log("real");
            myUserData.refreshToken();
          } else {
          console.log("cant upload");
        }
        }   
      } else {
          alert("File is too big!");
        }
   
  };


  return { uploadFile }
}
)

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(fileData, import.meta.hot))
}





















