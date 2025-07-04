import axios from 'axios';

// 내가 쓴 글 조회
export const fetchMyPosts = async (loginId) => {
  try {
    const response = await axios.get(`/api/mypage/posts`, {
      params: { loginId },
    });

    if (response.status === 200) {
      return response.data;
    } else {
      console.error('내 글 조회 실패: 응답 상태 코드', response.status);
      return null;
    }
  } catch (error) {
    console.error('내 글 조회 중 에러 발생:', error);
    return null;
  }
};

// 내가 작성한 질문(QnA) 조회
export const fetchMyQnas = async (loginId) => {
  try {
    const response = await axios.get(`/api/mypage/qnas`, {
      params: { loginId },
    });

    if (response.status === 200) {
      return response.data;
    } else {
      console.error('내 질문 목록 조회 실패: 응답 상태 코드', response.status);
      return null;
    }
  } catch (error) {
    console.error('내 질문 목록 조회 중 에러 발생:', error);
    return null;
  }
};