import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // ❗ URL의 파라미터(id)를 가져오기 위해 import
import AskViewer from '../components/AskViewer';
// import Input from '../components/Input';
// import AnswerItem from '../components/AnswerItem';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/qna-detail.css';

// ❗ 답변(Answer) 관련 기능은 현재 백엔드에 없으므로, 주석 처리하거나 삭제해야 합니다.
/*
const answers = [ ... ];
*/

function QnaDetailPage() {
  const { id } = useParams(); // ❗ URL에서 질문의 id 값을 가져옵니다. (예: /qna/1 -> id는 1)
  const [askDetail, setAskDetail] = useState(null); // API로 받은 질문 상세 데이터
  const [isLoading, setIsLoading] = useState(true); // 로딩 상태

  useEffect(() => {
    const fetchAskDetail = async () => {
      setIsLoading(true);
      try {
        // ❗ 백엔드의 GET /qna/{id} 엔드포인트를 호출합니다.
        const response = await fetch(`http://localhost:8080/qna/${id}`);
        if (!response.ok) {
          throw new Error('질문을 불러오는 데 실패했습니다.');
        }
        const data = await response.json();
        setAskDetail(data); // 상태에 데이터 저장
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchAskDetail();
  }, [id]); // id값이 바뀔 때마다 API를 다시 호출합니다.

  if (isLoading) {
    return (
      <DefaultLayout>
        <div className="qna-detail">
          <p>질문 내용을 불러오는 중입니다...</p>
        </div>
      </DefaultLayout>
    );
  }

  if (!askDetail) {
    return (
      <DefaultLayout>
        <div className="qna-detail">
          <p>해당 질문을 찾을 수 없습니다.</p>
        </div>
      </DefaultLayout>
    );
  }

  return (
    <DefaultLayout>
      <div className="qna-detail">
        {/* ❗ API로 받아온 askDetail 데이터를 AskViewer 컴포넌트에 전달합니다. */}
        <AskViewer ask={askDetail} />

        {/* --- 답변 기능 관련 안내 ---
          현재 Spring Boot 백엔드에는 답변을 조회하거나 작성하는 API가 없습니다.
          따라서 이 부분은 백엔드에 답변(Answer) 관련 CRUD 기능이 추가된 후에 연동할 수 있습니다.
          우선은 UI에서 제외하거나 주석 처리해두는 것을 권장합니다.
        */}
        <div className="answers-section">
          <div className="answers-count">0개 답변</div>
          <div className="answers-list">
            {/* 답변 데이터가 없으므로 비워둡니다. */}
          </div>
          <div className="reply-section">
            <div className="reply-title">답변 작성하기</div>
            {/* <Input typeText="답변" onSubmit={() => {}}/> */}
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default QnaDetailPage;