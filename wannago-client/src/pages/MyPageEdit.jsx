import { useState, useEffect } from 'react';
import DefaultLayout from '../layouts/DefatulLayout';
import Button from '../components/Button';
import '../assets/css/mypage.css';
import profileImage from '../assets/images/default-profile.png';
import { useNavigate } from 'react-router-dom';
import { getMyInfo, updateMyInfo } from '../api/mypage';

function MyPageEdit() {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    loginId: '',
    name: '',
    birth: '',
    email: '',
    phoneNumber: '',
  });

  const [formData, setFormData] = useState({
    password: '',
    confirmPassword: '',
    phoneNumber: '',
  });

  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loginId = localStorage.getItem('loginId');
    const token = localStorage.getItem('accessToken');

    console.log("➡️ MyPageEdit 컴포넌트 마운트됨");
    console.log("✅ localStorage accessToken:", token ? token.substring(0, 10) + '...' : null);
    console.log("✅ localStorage loginId:", loginId);

    if (!loginId || !token) {
      alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');
      navigate('/login');
      return;
    }

    const fetchUserInfo = async () => {
      setIsLoading(true);
      try {
        const userInfo = await getMyInfo(loginId);

        if (userInfo) {
          const formattedBirth = userInfo.birth instanceof Date 
                                 ? userInfo.birth.toISOString().split('T')[0] 
                                 : userInfo.birth ?? '';

          setUser({
            loginId: userInfo.loginId ?? '',
            name: userInfo.name ?? '',
            birth: formattedBirth,
            email: userInfo.email ?? '',
            phoneNumber: userInfo.phoneNumber ?? '',
          });
          setFormData(prev => ({
            ...prev,
            phoneNumber: userInfo.phoneNumber ?? '',
          }));
          console.log('✅ 회원 정보 불러오기 성공:', userInfo);
        } else {
          alert('회원 정보를 불러올 수 없습니다. 다시 시도해주세요.');
          navigate('/mypage');
        }
      } catch (err) {
        console.error('❌ 회원 정보 불러오기 실패 (MyPageEdit):', err);
        alert('회원 정보를 불러오는 중 오류가 발생했습니다.');
        navigate('/mypage');
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserInfo();
  }, [navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // 폼 제출 시 페이지 새로고침 방지

    console.log('➡️ handleSubmit 함수 호출됨'); // 이 로그가 찍히는지 다시 확인!

    const loginId = localStorage.getItem('loginId');
    if (!loginId) {
      alert('사용자 ID를 찾을 수 없습니다. 다시 로그인 해주세요.');
      navigate('/login');
      return;
    }

    if (formData.password) {
      if (formData.password !== formData.confirmPassword) {
        alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
        return;
      }
      if (formData.password.length < 4) {
        alert('비밀번호는 최소 4자 이상이어야 합니다.');
        return;
      }
    }
    
    if (formData.phoneNumber && !/^\d{10,11}$/.test(formData.phoneNumber)) {
        alert('유효한 전화번호 형식을 입력해주세요 (숫자만, 10~11자리).');
        return;
    }

    const updateData = {};
    if (formData.password) {
      updateData.password = formData.password;
      updateData.passwordConfirm = formData.confirmPassword;
    }
    if ((formData.phoneNumber ?? '') !== (user.phoneNumber ?? '')) {
        updateData.phoneNumber = formData.phoneNumber;
    }

    console.log('➡️ 서버로 전송될 수정 데이터:', updateData);
    console.log('➡️ 수정 대상 loginId:', loginId);

    if (Object.keys(updateData).length === 0) {
      alert('변경된 내용이 없습니다. (변경할 내용이 없어서 업데이트 요청을 보내지 않습니다.)');
      navigate('/mypage');
      return;
    }

    try {
      const success = await updateMyInfo(loginId, updateData);

      if (success) {
        alert('수정이 완료되었습니다!');
        navigate('/mypage');
      } else {
        alert('회원 정보 수정에 실패했습니다. 다시 시도해주세요.');
      }
    } catch (err) {
      console.error('❌ 회원 정보 수정 실패 (MyPageEdit handleSubmit):', err);
      if (err.response) {
        console.error('서버 응답 오류:', err.response.status, err.response.data);
        alert(`수정 중 오류 발생: ${err.response.data.message || err.response.statusText || '알 수 없는 서버 오류'}`);
      } else {
        alert('회원 정보 수정 중 네트워크 오류가 발생했습니다.');
      }
    }
  };

  if (isLoading) {
    return (
      <DefaultLayout>
        <div className="my-page-edit-container" style={{ textAlign: 'center', padding: '50px' }}>
          <h2>회원 정보를 불러오는 중입니다...</h2>
        </div>
      </DefaultLayout>
    );
  }

  return (
    <DefaultLayout>
      <div className="my-page-edit-container">
        <h2 className="page-title">마이페이지 정보 수정</h2>
        <div className="profile-section">
          <img className="profile-image" src={profileImage} alt="프로필 사진" />
          <span className="profile-name">{user.name || '-'}</span>
        </div>

        <form className="edit-form" onSubmit={handleSubmit}> {/* 🚨 onSubmit은 여기에만! */}
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
            <label htmlFor="password">새 비밀번호</label>
            <input
              id="password"
              name="password"
              type="password"
              placeholder="새 비밀번호를 입력하세요 (변경 시에만)"
              value={formData.password}
              onChange={handleChange}
              autoComplete="new-password"
            />
          </div>

          <div className="form-row">
            <label htmlFor="confirmPassword">새 비밀번호 확인</label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              placeholder="새 비밀번호를 다시 입력하세요"
              value={formData.confirmPassword}
              onChange={handleChange}
              autoComplete="new-password"
            />
          </div>

          <div className="form-row">
            <label htmlFor="phoneNumber">전화번호</label>
            <input
              id="phoneNumber"
              name="phoneNumber"
              type="tel"
              placeholder="전화번호를 입력하세요"
              value={formData.phoneNumber}
              onChange={handleChange}
            />
          </div>

          <div className="save-btn">
            {/* 🚨 이곳에서 onClick={handleSubmit}을 제거했습니다! */}
            <Button
                type="positive" // 폼 제출을 위한 'submit' 타입으로 명시. Button 컴포넌트가 잘 전달한다면 이것만으로 충분.
                text="수정완료"
            />
            <Button type="negative" text="취소" onClick={() => navigate('/mypage')} />
          </div>
        </form>
      </div>
    </DefaultLayout>
  );
}

export default MyPageEdit;