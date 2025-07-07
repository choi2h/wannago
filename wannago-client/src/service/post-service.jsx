import { HttpStatusCode } from "axios";
import api from "../utils/axios";

const POST_API = `${import.meta.env.VITE_API_SERVER_ADDRESS}/post`;
const POSTS_API = `${import.meta.env.VITE_API_SERVER_ADDRESS}/posts`;


const inputNewPost = (newPost) => {
    console.log('Add post!!!');
    console.log(JSON.stringify(newPost));

    return api.post('/post', {...newPost, author: 'me'})
    .then((response) => {
        console.log(response.status);
        return response;
    })
    .catch((err)=> console.log(err));
}

const selectPostById = async (id) => {
    return await api.get(`/post/${id}`)
    .then((response) => {
        console.log(response.status);
        console.log(response.data);

        return response.data;
    });
}

const deletePost = async (id) => {
    return await api.delete(`/post/${id}`)
    .then((response) => {
        console.log(response);
        if(response.status === HttpStatusCode.Ok) {
            console.log("게시글 삭제 완료");
        }

        return response;
    }).catch((error) => {
        console.log("게시글 삭제 실패", error);
    })
}

const selectPosts = async (pageNo, orderCriteria) => {
    return await api.get(`${POSTS_API}?page=${pageNo}&criteria=${orderCriteria}`)
    .then((response) => {
        console.log(response.status);
        console.log(response.data);

        return response.data;
    })
    .catch((err) => console.log(err));
}

const modifyPost = async (post) => {
    return await api.put(`${POST_API}/${post.id}`, {...post, author: 'me'})
    .then((response) => {
        console.log("Success to add new post!!");
        console.log(response.status);
    })
    .catch((err)=> console.log(err));
}

// 좋아요 토글 (좋아요 등록/취소)
const togglePostLike = async (postId) => {
     console.log('좋아요 토글');
  try {
    // 백엔드의 POST /post/{postId}/like 엔드포인트 호출
    const response = await api.post(`${POST_API}/${postId}/like`);
    return response.data.liked; // 백엔드가 반환하는 'liked' 상태 (true/false)
  } catch (error) {
    console.error('좋아요 토글 실패:', error);
    throw error;
  }
};

// 북마크 등록 요청 (POST)
const addPostBookmark = async (postId) => {
     console.log('북마크 요청');
  try {
    const response = await api.post(`${POST_API}/${postId}/bookmark`);
    return response.status === 201; 
  } catch (error) {
    console.error('북마크 등록 실패:', error);
    throw error;
  }
};

// 북마크 삭제 요청 (DELETE)
const removePostBookmark = async (postId) => {
     console.log('북마크 삭제');
  try {
    // 백엔드에 DELETE /post/{postId}/bookmark
    const response = await api.delete(`${POST_API}/${postId}/bookmark`);
    return response.status === 200; 
  } catch (error) {
    console.error('북마크 삭제 실패:', error);
    throw error;
  }
};

// 댓글 작성
const addComment = async (postId, commentData) => {
     console.log('댓글 작성');
    try {
        // 백엔드 URL: /post/{postId}/comment
        const response = await api.post(`${POST_API}/${postId}/comment`, commentData);
        return response.data; // CommentResponse 반환 예상
    } catch (error) {
        console.error('댓글 작성 실패:', error);
        throw error;
    }
};

// 대댓글 작성
const addReply = async (postId, parentId, commentData) => {
     console.log('대댓글 작성');
    try {
        // 백엔드 URL: /post/{postId}/comment/{parentId}/reply
        const response = await api.post(`${POST_API}/${postId}/comment/${parentId}/reply`, commentData);
        return response.data; // CommentResponse 반환 예상
    } catch (error) {
        console.error('대댓글 작성 실패:', error);
        throw error;
    }
};

// 댓글 전체 조회 (대댓글 포함)
const getAllCommentsWithReplies = async (postId) => {
     console.log('댓글 전체 조회!!');
    try {
        // 백엔드 URL: /post/{postId}/comment
        const response = await api.get(`${POST_API}/${postId}/comment`);
        return response.data; // List<CommentResponse> 반환 예상
    } catch (error) {
        console.error('댓글 전체 조회 실패:', error);
        throw error;
    }
};

// 특정 댓글의 대댓글만 조회 (PostDetailPage에서는 사용하지 않을 수도 있지만, 함수 정의)
const getRepliesForComment = async (postId, parentId) => { // postId 추가
    try {
        // 백엔드 URL: /post/{postId}/comment/{parentId}/reply
        const response = await api.get(`${POST_API}/${postId}/comment/${parentId}/reply`);
        return response.data; // List<CommentResponse> 반환 예상
    } catch (error) {
        console.error('특정 댓글의 대댓글 조회 실패:', error);
        throw error;
    }
};

// 댓글 수정
const updateComment = async (postId, commentId, commentData) => { // postId 추가
    try {
        // 백엔드 URL: /post/{postId}/comment/{commentId}
        const response = await api.put(`${POST_API}/${postId}/comment/${commentId}`, commentData);
        return response.data; // CommentResponse 반환 예상
    } catch (error) {
        console.error('댓글 수정 실패:', error);
        throw error;
    }
};

// 댓글 삭제
const deleteComment = async (postId, commentId) => {
    try {
        // 백엔드 URL: /post/{postId}/comment/{commentId}
        const response = await api.delete(`${POST_API}/${postId}/comment/${commentId}`);
        return response.status === 204; // 204 No Content 반환 예상
    } catch (error) {
        console.error('댓글 삭제 실패:', error);
        throw error;
    }
};


export {
    inputNewPost, 
    selectPostById, 
    selectPosts, 
    modifyPost, 
    deletePost,
    togglePostLike,
    addPostBookmark,
    removePostBookmark,
    addComment,
    addReply,
    getAllCommentsWithReplies,
    getRepliesForComment,
    updateComment,
    deleteComment
};