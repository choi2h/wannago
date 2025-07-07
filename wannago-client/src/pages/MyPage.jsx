// 📁 src/pages/MyPage.jsx

import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import '../assets/css/mypage.css';
import Button from '../components/Button';
import DefaultLayout from '../layouts/DefatulLayout';
import profileImage from '../assets/images/default-profile.png';
import { getMyInfo } from '../api/mypage';

function MyPage() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const [user, setUser] = useState({
    loginId: '',
    name: '',
    birth: '',
    email: '',
  });

  // ✅ 로그인 여부 확인
  useEffect(() => {
    if (!token) {
      alert('로그인이 필요합니다.');
      navigate('/login');
    }
  }, [token, navigate]);

  // ✅ 유저 정보 불러오기
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const info = await getMyInfo(); // ✅ loginId 넘기지 않음
        setUser({
          loginId: info.loginId,
          name: info.name,
          birth: info.birth ?? '미입력',
          email: info.email ?? '미입력',
        });
      } catch (e) {
        console.error('회원 정보 불러오기 실패:', e);
        alert('회원 정보를 불러오지 못했습니다.');
        navigate('/');
      }
    };
    fetchUser();
  }, [navigate]);

  return (
    <DefaultLayout>
      <div className="my-page-edit-container">
        <h2 className="page-title">마이페이지</h2>

        <div className="profile-section">
          <img className="profile-image" src={profileImage} alt="프로필 사진" />
          <span className="profile-name">{user.name}</span>
        </div>

        <div className="info">
          <div className="form-row">
            <label>아이디</label>
            <span className="readonly-field">{user.loginId}</span>
          </div>

          <div className="form-row">
            <label>이름</label>
            <span className="readonly-field">{user.name}</span>
          </div>

          <div className="form-row">
            <label>이메일</label>
            <span className="readonly-field">{user.email}</span>
          </div>

          <div className="form-row">
            <label>생년월일</label>
            <span className="readonly-field">{user.birth}</span>
          </div>

          <div className="save-btn">
            <Button
              type="positive"
              text="수정하기"
              onClick={() => navigate('/mypage/edit')}
            />
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}

export default MyPage;
