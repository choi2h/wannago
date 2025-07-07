import axios from 'axios';

// ✅ 1. 내 정보 조회 API
export const getMyInfo = async () => {
  try {
    const response = await axios.get(`/mypage/info`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('회원 정보 조회 실패:', error);
    return null;
  }
};

// ✅ 2. 내 정보 수정 API
export const updateMyInfo = async (updateData) => {
  try {
    const response = await axios.put(`/mypage/info`, updateData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response.status === 200;
  } catch (error) {
    console.error('회원 정보 수정 실패:', error);
    return false;
  }
};
