import axios from "axios";

const inputNewPost = (newPost) => {
    console.log('Add post!!!');
    console.log(JSON.stringify(newPost));

    axios.post(`${import.meta.env.VITE_API_SERVER_ADDRESS}/post`, {...newPost, author: 'me'})
    .then((response) => {
        console.log("Success to add new post!!");
        console.log(response.status);
    })
    .catch((err)=> console.log(err));
}

export {inputNewPost};