import axios from "axios";

const POST_API = `${import.meta.env.VITE_API_SERVER_ADDRESS}/post`;
const POSTS_API = `${import.meta.env.VITE_API_SERVER_ADDRESS}/posts`;

const inputNewPost = (newPost) => {
    console.log('Add post!!!');
    console.log(JSON.stringify(newPost));

    axios.post(POST_API, {...newPost, author: 'me'})
    .then((response) => {
        console.log("Success to add new post!!");
        console.log(response.status);
    })
    .catch((err)=> console.log(err));
}

const selectPostById = async (id) => {
    return await axios.get(`${POST_API}/${id}`)
    .then((response) => {
        console.log(response.status);
        console.log(response.data);

        return response.data;
    });
}

const selectPosts = async (pageNo, orderCriteria) => {
    return await axios.get(`${POSTS_API}?page=${pageNo}&criteria=${orderCriteria}`)
    .then((response) => {
        console.log(response.status);
        console.log(response.data);

        return response.data;
    })
    .catch((err) => console.log(err));
}

const modifyPost = async (post) => {
    return await axios.put(`${POST_API}/${post.id}`, {...post, author: 'me'})
    .then((response) => {
        console.log("Success to add new post!!");
        console.log(response.status);
    })
    .catch((err)=> console.log(err));
}



export {inputNewPost, selectPostById, selectPosts, modifyPost};