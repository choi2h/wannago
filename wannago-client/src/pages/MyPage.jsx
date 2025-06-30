import '../assets/css/mypage.css';
import Button from '../components/Button';
import DefaultLayout from '../layouts/DefatulLayout';
import profileImage from '../assets/images/default-profile.png';
import { useNavigate } from 'react-router';

function MyPage() {
  const navigate = useNavigate();

  const user = {
    loginId : '로그인아이디',
    name : '이름출력',
    birth : '2025.12.23',
    phoneNumber : '010-0000-0000',
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
          <span className="profile-name">{user.name}</span>
        </div>

        <div className="info">
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
              <span className="readonly-field">{user.birth}</span>
            </div>

            <div className="form-row">
              <label htmlFor="phoneNumber">전화번호</label>
              <span className="readonly-field">{user.phoneNumber}</span>
            </div>

            <div className="save-btn">
              <Button type="positive" text="수정하기" onClick={() => {
                console.log('회원정보 수정 버튼 클릭!!');
                navigate('/mypage/edit');
              }} />
            </div>
        </div>
      </div>
    </DefaultLayout>
  );
}

export default MyPage;