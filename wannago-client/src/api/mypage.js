import api from '../utils/axios';

/**
 * 내 정보 조회
 * @param {string} loginId - 사용자 로그인 ID
 * @returns {Promise<Object|null>} 사용자 정보 또는 null
 */
export const getMyInfo = async (loginId) => {
  try {
    const response = await api.get(`/mypage/info`, {
      params: { loginId },
    });
    console.log('✅ 내 정보 조회 성공:', response.data);
    return response.data;
  } catch (error) {
    console.error('❌ 회원 정보 조회 실패:', error.message);
    return null;
  }
};

/**
 * 내 정보 수정
 * @param {string} loginId - 사용자 로그인 ID
 * @param {Object} updateData - 업데이트할 사용자 데이터
 * @returns {Promise<boolean>} 수정 성공 여부
 */
export const updateMyInfo = async (loginId, updateData) => {
  try {
    const response = await api.put(`/mypage/info`, updateData, {
      params: { loginId },
    });
    console.log('✅ 내 정보 수정 성공:', response.status);
    return response.status === 200;
  } catch (error) {
    console.error('❌ 회원 정보 수정 실패:', error.message);
    return false;
  }
};

/**
 * 내가 작성한 게시글 조회
 * @returns {Promise<Array|null>} 게시글 목록 또는 null
 */
export const fetchMyPosts = async () => {
  try {
    const response = await api.get(`/mypage/posts`);
    console.log('✅ 내 게시글 조회 성공:', response.data);
    return response.data.posts;
  } catch (error) {
    console.log(error);
    console.error('❌ 내가 작성한 게시글 조회 중 에러 발생:', error.message);
    return null;
  }
};

/**
 * 내가 작성한 질문(QnA) 조회
 * @returns {Promise<Array|null>} QnA 목록 또는 null
 */
export const fetchMyQnas = async () => {
  try {
    const response = await api.get(`/mypage/qnas`);
    console.log('✅ 내 질문 목록 조회 성공:', response.data);
    return response.data.asks;
  } catch (error) {
    console.error('❌ 내 질문 목록 조회 중 에러 발생:', error.message);
    return null;
  }
};