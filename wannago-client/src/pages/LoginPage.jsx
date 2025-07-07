import DefaultLayout from '../layouts/DefatulLayout';
import { useState } from 'react';
import axios from 'axios';
import '../assets/css/login.css';

const API_BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;

function LoginPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Login attempt:', { name, password });
    // 여기에 로그인 로직 추가

    try {
      const response = await axios.post(`${API_BASE_URL}/login`, {
        name,
        password,
      });

      // 토큰 받아오기
      const { accessToken, refreshToken } = response.data;
      
      // LocalStorage에 저장
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);

      alert('로그인 성공!');
      window.location.href = '/';
    } catch (error) {
      console.log('로그인 실패:');
       console.error('📦 응답 데이터:', error.response.data);
       console.error('📡 상태 코드:', error.response.status);
       console.error('📨 응답 헤더:', error.response.headers);
      console.error('로그인 실패:', error);
      alert('로그인에 실패했습니다. 아이디 또는 비밀번호를 확인하세요.');
    }
  };

  return (
    <DefaultLayout>
      <div className="login-container">
        <div className="login-card">
          <h1 className="login-title">로그인</h1>
          
          <form onSubmit={handleSubmit} className="login-form">
            <div className="form-group">
              <label htmlFor="name" className="form-label">이름</label>
              <input
                id="name"
                type="text"
                className="form-input"
                placeholder="이름을 입력하세요"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="password" className="form-label">Password</label>
              <div className="password-input-wrapper">
                <div className="password-icon">
                  <svg className="lock-icon" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 1a4.5 4.5 0 00-4.5 4.5V9H5a2 2 0 00-2 2v6a2 2 0 002 2h10a2 2 0 002-2v-6a2 2 0 00-2-2h-.5V5.5A4.5 4.5 0 0010 1zm3 8V5.5a3 3 0 10-6 0V9h6z" clipRule="evenodd" />
                  </svg>
                </div>
                <input
                  id="password"
                  type={showPassword ? "text" : "password"}
                  className="form-input password-input"
                  placeholder="비밀번호를 입력하세요."
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
                <button
                  type="button"
                  className="password-toggle"
                  onClick={togglePasswordVisibility}
                >
                  <svg className="eye-icon" viewBox="0 0 20 20" fill="currentColor">
                    {showPassword ? (
                      <path fillRule="evenodd" d="M3.707 2.293a1 1 0 00-1.414 1.414l14 14a1 1 0 001.414-1.414l-1.473-1.473A10.014 10.014 0 0019.542 10C18.268 5.943 14.478 3 10 3a9.958 9.958 0 00-4.512 1.074l-1.78-1.781zm4.261 4.26l1.514 1.515a2.003 2.003 0 012.45 2.45l1.514 1.514a4 4 0 00-5.478-5.478z" clipRule="evenodd" />
                    ) : (
                      <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                    )}
                    <path fillRule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" clipRule="evenodd" />
                  </svg>
                </button>
              </div>
            </div>

            <button type="submit" className="login-button" disabled={!name || !password}>
              로그인
            </button>
          </form>

          <p className="register-link">
            <span className="register-text">아직 계정이 없으신가요?</span>
            <a href="/signup" className="register-link-text">회원가입</a>
          </p>
        </div>
      </div>
    </DefaultLayout>
  );
}

export default LoginPage;