import { useState, useEffect } from 'react';
import '../assets/css/qna-list.css';
import DefaultLayout from '../layouts/DefatulLayout';
import QnaItem from '../components/QnaItem';

function QnaListPage() {
  const [qnaList, setQnaList] = useState([]); // API로 받은 전체 질문 목록
  const [isLoading, setIsLoading] = useState(true); // 로딩 상태 추가
  const categories = ["전체", "맛집", "액티비티", "기타", "명소"]; // "전체" 카테고리 추가
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);

  // API 호출
  useEffect(() => {
    const fetchQnaList = async () => {
      setIsLoading(true); // 데이터 불러오기 시작
      try {
        // 백엔드의 GET /qna 엔드포인트 호출
        const response = await fetch('http://localhost:8080/qna'); 
        if (!response.ok) {
          throw new Error('네트워크 응답이 올바르지 않습니다.');
        }
        const data = await response.json();
        setQnaList(data); // 상태에 데이터 저장
      } catch (error) {
        console.error("질문 목록을 가져오는 중 오류가 발생했습니다:", error);
      } finally {
        setIsLoading(false); // 데이터 불러오기 완료
      }
    };

    fetchQnaList();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  const updateSelectedCategory = (category) => {
    setSelectedCategory(category);
  };

  // 선택된 카테고리에 따라 qnaList를 필터링
  const filteredList = selectedCategory === "전체"
    ? qnaList
    : qnaList.filter(qna => qna.category === selectedCategory);

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">Q&A</h1>
        
        <nav className="category-menu-bar">
          {categories.map((category) => (
            <div 
              key={category} 
              className={`menu-item ${selectedCategory === category ? "selected" : ""}`}
              onClick={() => updateSelectedCategory(category)}
            >
              {/* a 태그 대신 div나 button으로 의미를 명확히 할 수 있습니다. */}
              <span>{category}</span>
            </div>
          ))}
        </nav>

        <section className="qna-list-section">
          {isLoading ? (
            <p>로딩 중...</p>
          ) : filteredList.length > 0 ? (
            // ❗ 주석 처리된 Qnas 대신, API로 받아와 필터링된 filteredList를 사용합니다.
            // ❗ key는 고유한 값인 qna.id를 사용해야 합니다.
            filteredList.map((qna) => <QnaItem key={qna.id} qna={qna} />)
          ) : (
            <p>등록된 질문이 없습니다.</p> // 빈 목록일 때 표시할 메시지
          )}
        </section>
        
        {/* 페이지네이션은 백엔드 API에 페이지 기능이 추가된 후 구현해야 합니다. */}
        <nav className="pagination"> 
          {/* ... 페이지네이션 UI ... */}
        </nav>
      </div>
    </DefaultLayout>
  );
};

export default QnaListPage;
