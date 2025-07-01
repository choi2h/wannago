import { useState } from 'react';
import DefaultLayout from '../layouts/DefatulLayout';
import Button from '../components/Button';
import '../assets/css/mypage.css';
import profileImage from '../assets/images/default-profile.png';

const user = {
    loginId : 'hahahahaha',
    name : 'name',
    birth : '2025.12.23',
    phoneNumber : '010-0000-0000',
  }

function MyPageEdit() {
  const [formData, setFormData] = useState({
    password: '',
    confirmPassword: '',
    phoneNumber: user.phoneNumber || '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
      return;
    }
    // // 저장 처리 콜백 호출
    // onSave && onSave(formData);
  };

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
            <Button type="positive" text="수정완료" onClick={() => {console.log('회원정보 수정 버튼 클릭!!')}} />
          </div>
        </form>
      </div>
    </DefaultLayout>
  );
}

export default MyPageEdit;