import axios from 'axios';

// 내 게시글 조회
export const fetchMyPosts = async (loginId) => {
  try {
    const response = await axios.get(`/api/mypage/${loginId}/posts`);
    return response.data;
  } catch (error) {
    console.error('내 글 조회 중 에러 발생:', error);
    return null;
  }
};

// 내 QnA 조회
export const fetchMyQnas = async (loginId) => {
  try {
    const response = await axios.get(`/api/mypage/${loginId}/qnas`);
    return response.data;
  } catch (error) {
    console.error('내 질문 목록 조회 중 에러 발생:', error);
    return null;
  }
};
