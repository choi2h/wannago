import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../components/Button';
import QnaForm from '../components/QnaForm';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/post-write.css';
import { updateQna } from '../service/qna-service';
import { useEffect, useState } from 'react';

function QnaEditPage() {
  const navigate = useNavigate();
  const {state} = useLocation();
  const [ask, setAsk] = useState();

  useEffect(() => {
    setAsk(state.ask);
    console.log(state.ask);
  }, []);

  // Form 입력 값 변경 시 state 업데이트
  const handleChange = (e) => {
    const { name, value } = e.target;
    setAsk(prev => ({ ...prev, [name]: value }));
  };

  // 작성/수정 완료 버튼 클릭 시
  const handleSubmit = async () => {
    if(!ask.category) {
      alert('카테고리를 선택해주세요.');
      return;
    }

    if (!ask.title.trim()) {
      alert('제목을 입력해주세요.');
      return; 
    }
    
    if(!ask.contents.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }

    console.log(ask);
    await updateQna(ask.id, ask).then(() => {
      console.log("질문 수정 완료!");
      navigate(`/qna/${ask.id}`)
    }
    ).catch(() => {
      alert('질문을 작성하지 못했습니다.');
    }
    );
  };

  if(!ask) {
    return (
      <DefaultLayout>
        데이터를 불러오는중입니다..
      </DefaultLayout>
    )
  }

  return (
    <DefaultLayout>
      <div className="body">
        <QnaForm
          qnaData={ask}
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

export default QnaEditPage;