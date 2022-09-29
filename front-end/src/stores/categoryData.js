import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref} from 'vue'
import { useRouter } from 'vue-router'
import { cookieData } from "../stores/cookieData.js"
export const categorys = defineStore('categoryListState',() => {
    const categoryList = ref([])
    const myCookie = cookieData()
    //GET ทำเเล้ว
    const getEventCategory = async () => {
        try {
            console.log(import.meta.env.URL);
            const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategory` , {
              method: "GET",
              headers: {
                'content-type': 'application/json',
                "Authorization": "Bearer " + myCookie.getCookie("token")
              }
            });
            console.log(res.status);
            if (res.status === 200) {
                categoryList.value = await res.json();
                console.log(categoryList.value,"cat");
            }
            else if (res.status === 401) {
              let resText = await res.text();
              if (resText.toUpperCase().match("TOKENEXPIRED")) {
                  //ได้ละ
                  console.log("real");
                  myUserData.refreshToken()
              }
          }
            else {
                console.log("error, cannot get data");
            }
        } catch (err) {
            console.log(err);
        }
    };

    //PUT ทำเเล้ว
    const updateCategory = async (objectCategory) => {
      objectCategory.eventCategoryName = objectCategory.eventCategoryName.trimStart().trimEnd().replace("  ", " ");
      
        console.log(objectCategory);
        console.log(objectCategory.id);
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategory/${objectCategory.id}`, {
          method: 'PUT',
          headers: {
            'content-type': 'application/json',
            "Authorization": "Bearer " + myCookie.getCookie("token")
          },
          body: JSON.stringify(objectCategory)
        })
        if (res.status === 201) {
          const modCategory = await res.json();
          categoryList.value = categoryList.value.map((category) =>
          category.id === modCategory.id
          ? { ...category, eventCategoryName: modCategory.eventCategoryName, eventDuration: modCategory.eventDuration, eventCategoryDescription:modCategory.eventCategoryDescription }
          : category
      )
          console.log('edited successfully')
          return 1
        }
        else if (res.status === 401) {
          let resText = await res.text();
          if (resText.toUpperCase().match("TOKENEXPIRED")) {
              //ได้ละ
              console.log("real");
              myUserData.refreshToken()
          }
      }
        else {
          console.log('error, cannot edit')
          return -2
        }
      }
    
    
  const validateEventName = (newCategory) =>{
    console.log("asdad"+ newCategory)
    newCategory.eventCategoryName = newCategory.eventCategoryName.trimStart();
    
    return categoryList.value
    .filter((category) => category.id!==newCategory.id)
    .some((category)=> category.eventCategoryName.toLowerCase()===newCategory.eventCategoryName.toLowerCase())
  }



    getEventCategory();

    return { categoryList, getEventCategory, updateCategory, validateEventName}
}
)

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(categorys, import.meta.hot))
}