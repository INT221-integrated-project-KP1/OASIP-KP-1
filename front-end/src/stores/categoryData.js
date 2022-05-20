import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed ,onBeforeMount} from 'vue'
export const categorys = defineStore('categoryListState',() => {
    const categoryList = ref([])
    const page = ref(0); //page start 0
    const pageSize = ref(9); //default 4

    //GET
    const getEventCategory = async () => {
        try {
            console.log(import.meta.env.URL);
            const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategory`);
            console.log(res.status);
            if (res.status === 200) {
                categoryList.value = await res.json();
                console.log(categoryList.value,"cat");
            } else {
                console.log("error, cannot get data");
            }
        } catch (err) {
            console.log(err);
        }
    };

    //UPDATEselectedCategory.eventCategoryName, selectedCategory.eventCategoryDescription, selectedCategory.eventDuration,
    //PUT
    const updateCategory = async (objectCategory) => {
        console.log(objectCategory);
        console.log(objectCategory.id);
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategory/${objectCategory.id}`, {
          method: 'PUT',
          headers: {
            'content-type': 'application/json'
          },
          body: JSON.stringify(objectCategory)
        })
        if (res.status === 201) {
          //
          console.log('edited successfully')
        } else {
          console.log('error, cannot edit')
        }
      }
    
    




    getEventCategory();

    return { categoryList, page, pageSize, getEventCategory, updateCategory}
}
)

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(categorys, import.meta.hot))
}