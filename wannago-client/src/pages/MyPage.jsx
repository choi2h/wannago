// MyPage.jsx (이 부분은 그대로 유지해도 됩니다.)
import '../assets/css/mypage.css';
import Button from '../components/Button';
import DefaultLayout from '../layouts/DefatulLayout';
import profileImage from '../assets/images/default-profile.png';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getMyInfo } from '../api/mypage';

function MyPage() {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    loginId: '',
    name: '',
    birth: '',
    email: '',
  });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loginId = localStorage.getItem("loginId");
    // 🚨 이 부분은 디버깅용이므로 'accessToken'으로 변경해도 좋고, mypage.js의 인터셉터가 처리하므로 그대로 두어도 됩니다.
    console.log("➡️ MyPage 컴포넌트 마운트됨");
    console.log("✅ localStorage accessToken:", localStorage.getItem("accessToken") ? localStorage.getItem("accessToken").substring(0, 10) + '...' : null);
    console.log("✅ localStorage loginId:", loginId);

    if (!loginId) {
      alert("로그인 정보가 없습니다. 로그인 페이지로 이동합니다.");
      navigate("/login");
      return;
    }

    const loadMyInfo = async () => {
      setIsLoading(true);
      try {
        const data = await getMyInfo(loginId);
        if (data) {
          setUser({
            loginId: data.loginId ?? '',
            name: data.name ?? '',
            birth: data.birth ?? '',
            email: data.email ?? '',
          });
          console.log('✅ 사용자 정보 설정 완료:', data);
        } else {
          alert("사용자 정보를 불러오는데 실패했습니다.");
        }
      } catch (err) {
        console.error("❌ MyPage 컴포넌트에서 회원 정보 조회 중 에러 발생:", err);
      } finally {
        setIsLoading(false);
      }
    };

    loadMyInfo();
  }, [navigate]);

  if (isLoading) {
    return (
      <DefaultLayout>
        <div className="my-page-edit-container" style={{ textAlign: 'center', padding: '50px' }}>
          <h2>사용자 정보를 불러오는 중입니다...</h2>
        </div>
      </DefaultLayout>
    );
  }

  return (
    <DefaultLayout>
      <div className="my-page-edit-container">
        <h2 className="page-title">마이페이지</h2>
        <div className="profile-section">
          <img
            className="profile-image"
            src={profileImage}
            alt="프로필 사진"
          />
          <span className="profile-name">{user.name || '-'}</span>
        </div>

        <div className="info">
          <div className="form-row">
            <label>아이디</label>
            <span className="readonly-field">{user.loginId || '-'}</span>
          </div>

          <div className="form-row">
            <label>이름</label>
            <span className="readonly-field">{user.name || '-'}</span>
          </div>

          <div className="form-row">
            <label>생년월일</label>
            <span className="readonly-field">{user.birth || '-'}</span>
          </div>

          <div className="form-row">
            <label>이메일</label>
            <span className="readonly-field">{user.email || '-'}</span>
          </div>

          <div className="form-row">
            <label>비밀번호</label>
            <span className="readonly-field">●●●●●●</span>
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