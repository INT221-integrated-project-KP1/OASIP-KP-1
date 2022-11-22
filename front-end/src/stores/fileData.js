import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref} from 'vue'
import { cookieData } from "../stores/cookieData.js"
import { useRouter } from "vue-router";

export const fileData = defineStore("fileState", () => {
    const myCookie = cookieData();

//upfile นะ
const uploadFile = async (newEvent) => {

  const { params } = useRouter();
  const myRouter = useRouter();

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
          myRouter.push({ name: "SignIn" });
        }
        }   
      } else {
          alert("File is too big!");
          
        }
        document.getElementById("fileupload").value = null;
  };

  //delete นะ
const deleteFile = async (name) => {

  // if( 
  //     !(document.getElementById("fileupload").files[0].size /1024/1024 > 10) 
  //     ){
  //     let data = new FormData();
  //     data.append(
  //     "file",document.getElementById("fileupload").files[0],newEvent.file
  //     )
      alert(name)
    
      const res = await fetch(`${import.meta.env.VITE_BASE_URL}/file/${name}`,{
        method: "DELETE", 
        headers: {
            Authorization: "Bearer " + myCookie.getCookie("token"),
          }
      })
      alert(res.status)
      if (res.status === 200) {
        alert("Delete File");
      } else if (res.status === 404) {
        let resText = await res.text();
        if (resText.toUpperCase().match("TOKENEXPIRED")) {
          //ได้ละ
          console.log("real");
          myUserData.refreshToken();
        } else {
        console.log("cant delete");
        myRouter.push({ name: "SignIn" });

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
    const blob = await res.blob();
    const newBlob = new Blob([blob]);

    const blobUrl = window.URL.createObjectURL(newBlob);

    const link = document.createElement('a');
    link.href = blobUrl;
    link.setAttribute('download', `${name}`);
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link);

    // clean up Url
    window.URL.revokeObjectURL(blobUrl);
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
  myRouter.push({ name: "SignIn" });

}
}


  return { uploadFile , deleteFile , getFile}
}
)

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(fileData, import.meta.hot))
}





















