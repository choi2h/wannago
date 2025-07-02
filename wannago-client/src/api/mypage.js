import axios from 'axios';

export const fetchMyPosts = async (loginId) => {
  const response = await axios.get(`/api/mypage/posts`, { params: { loginId } });
  return response.data;
};

export const fetchMyQnas = async (loginId) => {
  const response = await axios.get(`/api/mypage/qnas`, { params: { loginId } });
  return response.data;
};
