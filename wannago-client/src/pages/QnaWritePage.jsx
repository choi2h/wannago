import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Button from '../components/Button';
import QnaForm from '../components/QnaForm';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/post-write.css';

function QnaWritePage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = Boolean(id);

  const [qnaData, setQnaData] = useState({
    category: '맛집',
    title: '',
    content: ''
  });
  const [isLoading, setIsLoading] = useState(false);

  // 수정 모드일 때, 기존 데이터를 불러옵니다.
  useEffect(() => {
    if (isEditMode) {
      const fetchQnaDetail = async () => {
        setIsLoading(true);
        try {
          const response = await fetch(`http://localhost:8080/qna/${id}`);
          if (!response.ok) {
            throw new Error('질문 정보를 불러올 수 없습니다.');
          }
          const data = await response.json();
          setQnaData({
            // 백엔드 응답에 category가 없을 수 있으므로, 있는 필드만 채웁니다.
            // 만약 응답에 category가 있다면 data.category로 설정하세요.
            category: data.category || '맛집', 
            title: data.title,
            content: data.content
          });
        } catch (error) {
          console.error(error);
          alert(error.message);
          navigate('/qna'); // 에러 발생 시 목록으로 이동
        } finally {
          setIsLoading(false);
        }
      };
      fetchQnaDetail();
    }
  }, [id, isEditMode, navigate]);

  // Form 입력 값 변경 시 state 업데이트
  const handleChange = (e) => {
    const { name, value } = e.target;
    setQnaData(prev => ({ ...prev, [name]: value }));
  };

  // 작성/수정 완료 버튼 클릭 시
  const handleSubmit = async () => {
    if (!qnaData.title.trim() || !qnaData.content.trim()) {
      alert('제목과 내용을 모두 입력해주세요.');
      return;
    }

    setIsLoading(true);
    const url = isEditMode ? `http://localhost:8080/qna/${id}` : 'http://localhost:8080/qna';
    const method = isEditMode ? 'PUT' : 'POST';

    try {
      const response = await fetch(url, {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(qnaData),
      });

      if (!response.ok) {
        throw new Error('질문 저장에 실패했습니다.');
      }

      const result = await response.json();
      alert(`성공적으로 ${isEditMode ? '수정' : '등록'}되었습니다.`);
      navigate(`/qna/${result.id}`); // 성공 시 상세 페이지로 이동
    } catch (error) {
      console.error(error);
      alert(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <DefaultLayout>
      <div className="body">
        <QnaForm
          qnaData={qnaData}
          handleChange={handleChange}
          isEditMode={isEditMode}
        />
        <div className="bottom-button">
          <Button type={'negative'} text={'작성취소'} onClick={() => navigate(-1)} />
          <Button
            type={'positive'}
            text={isEditMode ? '수정완료' : '작성완료'}
            onClick={handleSubmit}
            disabled={isLoading}
          />
        </div>
      </div>
    </DefaultLayout>
  );
};

export default QnaWritePage;