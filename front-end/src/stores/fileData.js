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
        document.getElementById("fileupload").value = null;
  };

  //delete นะ
const deleteFile = async (newEvent) => {

  // if( 
  //     !(document.getElementById("fileupload").files[0].size /1024/1024 > 10) 
  //     ){
  //     let data = new FormData();
  //     data.append(
  //     "file",document.getElementById("fileupload").files[0],newEvent.file
  //     )
      alert(newEvent.file)
    
      const res = await fetch(`${import.meta.env.VITE_BASE_URL}/file/${newEvent.file}`,{
        method: "PUT", 
        headers: {
            Authorization: "Bearer " + myCookie.getCookie("token"),
          }
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
     else {
        alert("File is too big!");
      }
 
};

const getFile = async (name) =>{
 try{ const res = await fetch(`${import.meta.env.VITE_BASE_URL}/file/get/${name}`,{
    method: "GET", 
    headers: {
        Authorization: "Bearer " + myCookie.getCookie("token"),
      }
  })
  alert(res.status)
  if(res.status === 200 ){
    const url = window.URL.createObjectURL(new Blob([res.data]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', name)
    document.body.appendChild(link)
    link.click()
  }
  else if (res.status === 401) {
    let resText = await res.text();
    if (resText.toUpperCase().match("TOKENEXPIRED")) {
      //ได้ละ
      console.log("real");
      myUserData.refreshToken();
    }
  } else {
    console.log("error, cannot get data");
  }
} catch (err) {
  console.log("ERROR: " + err);
}
}


  return { uploadFile , deleteFile , getFile}
}
)

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(fileData, import.meta.hot))
}





















