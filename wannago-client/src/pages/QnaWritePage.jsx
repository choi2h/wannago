import { useNavigate } from 'react-router-dom';
import Button from '../components/Button';
import QnaForm from '../components/QnaForm';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/post-write.css';
import { inputQna } from '../service/qna-service';
import { useState } from 'react';

function QnaWritePage() {
  const navigate = useNavigate();
  const [qnaData, setQnaData] = useState({
    category: '',
    title: '',
    content: ''
  });

  // Form 입력 값 변경 시 state 업데이트
  const handleChange = (e) => {
    const { name, value } = e.target;
    setQnaData(prev => ({ ...prev, [name]: value }));
  };

  // 작성/수정 완료 버튼 클릭 시
  const handleSubmit = async () => {
    if(!qnaData.category) {
      alert('카테고리를 선택해주세요.');
      return;
    }

    if (!qnaData.title.trim()) {
      alert('제목을 입력해주세요.');
      return; 
    }
    
    if(!qnaData.content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }

    inputQna(qnaData);
  };

  return (
    <DefaultLayout>
      <div className="body">
        <QnaForm
          qnaData={qnaData}
          handleChange={handleChange}
        />
        <div className="bottom-button">
          <Button type={'negative'} text={'작성취소'} onClick={() => navigate(-1)} />
          <Button
            type={'positive'}
            text='작성완료'
            onClick={handleSubmit}
          />
        </div>
      </div>
    </DefaultLayout>
  );
};

export default QnaWritePage;