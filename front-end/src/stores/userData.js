import { defineStore, acceptHMRUpdate } from 'pinia'
import { computed, ref } from 'vue'
export const userData = defineStore('userDataState', () => {
    const userList = ref([])

    const validateUniqueName = (id, name) => {
        if (id && name != undefined) {

            return userList.value.filter((user) => user.id !== id)
                .some((user) => user.name.toLowerCase() == name.toLowerCase())
        }
    }

    const validateUniqueEmail = (id, email) => {
        if (id && email != undefined) {
            return userList.value.filter((user) => user.id !== id)
                .some((user) => user.email.toLowerCase() == email.toLowerCase())
        }
    }


    // POST
    const createNewUser = async (user) => {
        try {
            const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/`, {
                method: "POST",
                headers: {
                    "content-type": "application/json",
                },
                body: JSON.stringify({
                    name: user.name,
                    email: user.email,
                    role: user.role,
                    password: user.password
                }),
            });



            if (res.status === 201) {
                console.log("added sucessfully");
                return { error: "", status: 1 };
            } else {
                console.log("error, cannot be added");
                return { error: await res.text(), status: 2 };
            }
        } catch (err) {
            console.log(err);
            return { error: err, status: 2 };
        }
    }

    // GET
    const getUsers = async () => {
        try {
            const res = await fetch(
                `${import.meta.env.VITE_BASE_URL}/user`);
            if (res.status === 200) {
                userList.value = await res.json()
            } else {
                console.log("error, cannot get data");
            }
        } catch (err) {
            console.log("ERROR: " + err);
        }
    };

    //REMOVE
    const removeUser = async (deleteId) => {
        console.log(deleteId);
        const res = await fetch(
            `${import.meta.env.VITE_BASE_URL}/user/${deleteId}`,
            {
                method: "DELETE",
            }
        );
        if (res.status === 200) {
            userList.value = userList.value.filter((user) => user.id !== deleteId);
            console.log("deleted successfully");
            if (userList.value.length % 9 == 8) {
                getUsers();
            }

        } else console.log("error, cannot delete data");
    };

    // //PUT
    const updateUser = async (updatedUser) => {
        try {

            const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/${updatedUser.id}`, {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    id: updatedUser.id,
                    name: updatedUser.name,
                    email: updatedUser.email,
                    role: updatedUser.role
                })
            })
            if (res.status === 201) {
                const modUser = await res.json();
                console.log(modUser);
                userList.value = userList.value.map((user) =>
                    user.id === modUser.id
                        ? { ...user, name: modUser.name, email: modUser.email, role: modUser.role }
                        : user
                )

                console.log('edited successfully')
                return 1;
            } else {
                console.log('error, cannot edit')

                return -1
            }
        } catch (err) {
            console.log('catchhhhh');
            console.log(err);
            return -1
        }
    }


    getUsers();
    return { userList, createNewUser, getUsers, removeUser, updateUser, validateUniqueName, validateUniqueEmail }
})


if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(userData, import.meta.hot))
}