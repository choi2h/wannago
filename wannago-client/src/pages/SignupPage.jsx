import { useState } from 'react';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/join.css';

function SignupPage() {
  const [formData, setFormData] = useState({
    email: '',
    confirmEmail: '',
    password: '',
    confirmPassword: '',
    birthDate: ''
  });
  
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [emailVerified, setEmailVerified] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleEmailVerification = () => {
    // 이메일 인증 로직
    console.log('이메일 인증 요청:', formData.email);
    setEmailVerified(true);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('회원가입 데이터:', formData);
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  return (
    <DefaultLayout>
      <div className="signup-container">
        <div className="signup-card">
          <h1 className="signup-title">회원가입</h1>
          
          <form onSubmit={handleSubmit} className="signup-form">
            {/* 이메일 입력 */}
            <div className="form-group">
              <label htmlFor="email" className="form-label">이메일</label>
              <div className="email-input-group">
                <input
                  id="email"
                  name="email"
                  type="email"
                  className="form-input email-input"
                  placeholder="이메일을 입력하세요"
                  value={formData.email}
                  onChange={handleInputChange}
                  required
                />
                <button
                  type="button"
                  className={`verify-button ${emailVerified ? 'verified' : ''}`}
                  onClick={handleEmailVerification}
                  disabled={!formData.email || emailVerified}
                >
                  {emailVerified ? '인증완료' : '중복확인'}
                </button>
              </div>
            </div>

            {/* 이메일 확인 */}
            <div className="form-group">
              <label htmlFor="confirmEmail" className="form-label">이메일 확인</label>
              <input
                id="confirmEmail"
                name="confirmEmail"
                type="email"
                className="form-input"
                placeholder="이메일을 다시 입력하세요"
                value={formData.confirmEmail}
                onChange={handleInputChange}
                required
              />
            </div>

            {/* 비밀번호 */}
            <div className="form-group">
              <label htmlFor="password" className="form-label">비밀번호</label>
              <div className="password-input-wrapper">
                <div className="password-icon">
                  <svg className="lock-icon" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 1a4.5 4.5 0 00-4.5 4.5V9H5a2 2 0 00-2 2v6a2 2 0 002 2h10a2 2 0 002-2v-6a2 2 0 00-2-2h-.5V5.5A4.5 4.5 0 0010 1zm3 8V5.5a3 3 0 10-6 0V9h6z" clipRule="evenodd" />
                  </svg>
                </div>
                <input
                  id="password"
                  name="password"
                  type={showPassword ? "text" : "password"}
                  className="form-input password-input"
                  placeholder="비밀번호를 입력하세요"
                  value={formData.password}
                  onChange={handleInputChange}
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

            {/* 비밀번호 확인 */}
            <div className="form-group">
              <label htmlFor="confirmPassword" className="form-label">비밀번호 확인</label>
              <div className="password-input-wrapper">
                <div className="password-icon">
                  <svg className="lock-icon" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 1a4.5 4.5 0 00-4.5 4.5V9H5a2 2 0 00-2 2v6a2 2 0 002 2h10a2 2 0 002-2v-6a2 2 0 00-2-2h-.5V5.5A4.5 4.5 0 0010 1zm3 8V5.5a3 3 0 10-6 0V9h6z" clipRule="evenodd" />
                  </svg>
                </div>
                <input
                  id="confirmPassword"
                  name="confirmPassword"
                  type={showConfirmPassword ? "text" : "password"}
                  className="form-input password-input"
                  placeholder="비밀번호를 다시 입력하세요"
                  value={formData.confirmPassword}
                  onChange={handleInputChange}
                  required
                />
                <button
                  type="button"
                  className="password-toggle"
                  onClick={toggleConfirmPasswordVisibility}
                >
                  <svg className="eye-icon" viewBox="0 0 20 20" fill="currentColor">
                    {showConfirmPassword ? (
                      <path fillRule="evenodd" d="M3.707 2.293a1 1 0 00-1.414 1.414l14 14a1 1 0 001.414-1.414l-1.473-1.473A10.014 10.014 0 0019.542 10C18.268 5.943 14.478 3 10 3a9.958 9.958 0 00-4.512 1.074l-1.78-1.781zm4.261 4.26l1.514 1.515a2.003 2.003 0 012.45 2.45l1.514 1.514a4 4 0 00-5.478-5.478z" clipRule="evenodd" />
                    ) : (
                      <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                    )}
                    <path fillRule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" clipRule="evenodd" />
                  </svg>
                </button>
              </div>
            </div>

            {/* 생년월일 */}
            <div className="form-group">
              <label htmlFor="birthDate" className="form-label">생년월일</label>
              <input
                id="birthDate"
                name="birthDate"
                type="date"
                className="form-input"
                value={formData.birthDate}
                onChange={handleInputChange}
                required
              />
            </div>

            <button type="submit" className="signup-button">
              회원가입
            </button>
          </form>

          <div className="login-link">
            <span className="login-text">이미 계정이 있으신가요?</span>
            <a href="/login" className="login-link-text">로그인</a>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}

export default SignupPage;