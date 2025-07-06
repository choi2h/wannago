import axios from 'axios';

// ✅ 내 정보 수정 요청 함수
export const updateMyInfo = async (loginId, updateData) => {
  try {
    const response = await axios.put(`/api/mypage/info`, updateData, {
      params: { loginId },  // ✏️ 쿼리 파라미터로 전달
    });
    return response.status === 200;
  } catch (error) {
    console.error('회원 정보 수정 실패:', error);
    return false;
  }
};
// 내 정보 조회 API
export const getMyInfo = async (loginId) => {
  try {
    const response = await axios.get(`/api/mypage/info`, {
      params: { loginId },
    });
    return response.data;
  } catch (error) {
    console.error('회원 정보 조회 실패:', error);
    return null;
  }
};
