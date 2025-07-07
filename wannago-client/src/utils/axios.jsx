import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;

// api 인스턴스 생성
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 새로운 AccessToken을 발급받는 함수
/*const refreshAccessToken = async () => {
  const refreshToken = getRefreshToken();
  try {
    const response = await axios.get(`${API_BASE_URL}v1/oauth/refresh-token`, {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
      },
    });
    const { accessToken: newAccessToken } = response.data.data;
    setAccessToken(newAccessToken);
    return newAccessToken;
  } catch (error) {
    console.error(error);
  }
};*/

// 요청 보낼 때 headers에 accesstoken 추가
api.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      config.headers['X-ACCESS-TOKEN'] = accessToken;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답을 가로채고 처리하는 인터셉터 설정
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    // AccessToken이 만료되었을 경우
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const refreshToken = localStorage.getItem('refreshToken');
      const accessToken = localStorage.getItem('accessToken');
      // 새로운 AccessToken을 발급받습니다.
      try {
        const res = await axios.post(`${API_BASE_URL}/reissue`, null, {
            headers: {
                Authorization: `Bearer ${refreshToken}`,
                'X-ACCESS-TOKEN': accessToken
            }
        });

        const {accessToken: newAccessToken, refreshToken: newRefreshToken} = res.data;

        localStorage.setItem('accessToken', newAccessToken);
        localStorage.setItem('refreshToken', newRefreshToken);

        originalRequest.headers['X-ACCESS-TOKEN'] = newAccessToken;
        
        return api(originalRequest);
      } catch (reissueError) {
        alert('로그인 시간이 만료되었습니다.');

        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
        
        return Promise.reject(reissueError);
      }
    }
    return Promise.reject(error);
  }
);

export default api;