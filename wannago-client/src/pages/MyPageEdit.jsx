import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import DefaultLayout from '../layouts/DefatulLayout';
import Button from '../components/Button';
import '../assets/css/mypage.css';
import profileImage from '../assets/images/default-profile.png';
import { getMyInfo, updateMyInfo } from '../api/mypage';

function MyPageEdit() {
  const navigate = useNavigate();
  const loginId = localStorage.getItem('loginId');

  // 사용자 정보 상태
  const [user, setUser] = useState({
    loginId: '',
    name: '',
    birth: '',
  });

  // 수정할 입력값 상태
  const [formData, setFormData] = useState({
    password: '',
    confirmPassword: '',
    email: '',
  });

  // 로그인 안 한 경우 리디렉션
  useEffect(() => {
    if (!loginId) {
      alert('로그인이 필요합니다.');
      navigate('/login');
    }
  }, [loginId, navigate]);

  // 내 정보 조회
  useEffect(() => {
    const fetchUser = async () => {
      const info = await getMyInfo(loginId);
      if (info) {
        setUser({
          loginId: info.loginId,
          name: info.name,
          birth: info.birth ?? '생일 정보 없음', // birth가 없으면 대체 문자열
        });
        setFormData(prev => ({
          ...prev,
          email: info.email,
        }));
      }
    };
    fetchUser();
  }, [loginId]);

  // 입력값 변경 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // 수정 제출
  const handleSubmit = async (e) => {
    e.preventDefault();

    // 유효성 체크
    if (!formData.password || !formData.confirmPassword || !formData.email) {
      alert('모든 항목을 입력해주세요.');
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
      return;
    }

    const success = await updateMyInfo(loginId, {
      name: user.name,
      password: formData.password,
      passwordConfirm: formData.confirmPassword,
      email: formData.email,
    });

    if (success) {
      alert('회원 정보가 성공적으로 수정되었습니다.');
      navigate('/mypage');
    } else {
      alert('회원 정보 수정에 실패했습니다.');
    }
  };

  return (
    <DefaultLayout>
      <div className="my-page-edit-container">
        <h2 className="page-title">마이페이지</h2>

        <div className="profile-section">
          <img className="profile-image" src={profileImage} alt="프로필 사진" />
          <span className="profile-name">{user.name}</span>
        </div>

        <form className="edit-form" onSubmit={handleSubmit}>
          <div className="form-row">
            <label>아이디</label>
            <span className="readonly-field">{user.loginId}</span>
          </div>

          <div className="form-row">
            <label>생년월일</label>
            <span className="readonly-field">{user.birth}</span>
          </div>

          <div className="form-row">
            <label htmlFor="password">비밀번호</label>
            <input
              id="password"
              name="password"
              type="password"
              placeholder="비밀번호를 입력하세요"
              value={formData.password}
              onChange={handleChange}
            />
          </div>

          <div className="form-row">
            <label htmlFor="confirmPassword">비밀번호 확인</label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              placeholder="비밀번호를 다시 입력하세요"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
          </div>

          <div className="form-row">
            <label htmlFor="email">이메일</label>
            <input
              id="email"
              name="email"
              type="email"
              placeholder="이메일을 입력하세요"
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <div className="save-btn">
            <Button type="submit" text="수정완료" />
          </div>
        </form>
      </div>
    </DefaultLayout>
  );
}

export default MyPageEdit;
