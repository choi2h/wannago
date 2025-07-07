import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; // API_BASE_URL과 동일하게 설정하는 것이 좋습니다.

// Axios 인스턴스 생성 및 공통 헤더 설정
const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터를 사용하여 모든 요청에 토큰 추가
api.interceptors.request.use(
  (config) => {
    // 🚨 여기서 'token' 대신 'accessToken'을 가져오도록 수정했습니다.
    const token = localStorage.getItem('accessToken'); 
    if (token) {
      config.headers['X-ACCESS-TOKEN'] = token; // 서버가 'X-ACCESS-TOKEN' 헤더를 기대한다면 이대로 유지
      console.log('➡️ API 요청에 Access Token 포함:', token.substring(0, 10) + '...');
    } else {
      console.warn('⚠️ localStorage에 Access Token이 없습니다. 비인증 요청일 수 있습니다.');
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터를 사용하여 401 에러(인증 실패) 처리
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.error('⛔ 401 Unauthorized 에러 발생: 로그인 세션이 만료되었거나 유효하지 않습니다.');
      alert('로그인이 만료되었거나 권한이 없습니다. 다시 로그인 해주세요.');
      // 유효하지 않은 토큰 및 loginId 삭제
      localStorage.removeItem('accessToken'); 
      localStorage.removeItem('refreshToken'); // refreshToken도 함께 삭제
      localStorage.removeItem('loginId'); 
      window.location.href = '/login'; // 로그인 페이지로 리다이렉트
    }
    return Promise.reject(error);
  }
);

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
 * @param {string} loginId - 사용자 로그인 ID
 * @returns {Promise<Array|null>} 게시글 목록 또는 null
 */
export const fetchMyPosts = async (loginId) => {
  try {
    const response = await api.get(`/mypage/${loginId}/posts`);
    console.log('✅ 내 게시글 조회 성공:', response.data);
    return response.data;
  } catch (error) {
    console.error('❌ 내가 작성한 게시글 조회 중 에러 발생:', error.message);
    return null;
  }
};

/**
 * 내가 작성한 질문(QnA) 조회
 * @param {string} loginId - 사용자 로그인 ID
 * @returns {Promise<Array|null>} QnA 목록 또는 null
 */
export const fetchMyQnas = async (loginId) => {
  try {
    const response = await api.get(`/mypage/${loginId}/qnas`);
    console.log('✅ 내 질문 목록 조회 성공:', response.data);
    return response.data;
  } catch (error) {
    console.error('❌ 내 질문 목록 조회 중 에러 발생:', error.message);
    return null;
  }
};