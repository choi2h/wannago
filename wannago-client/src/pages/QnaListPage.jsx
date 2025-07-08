import { useState, useEffect } from 'react';
import '../assets/css/qna-list.css';
import DefaultLayout from '../layouts/DefatulLayout';
import QnaItem from '../components/QnaItem';
import { getQnaList } from '../service/qna-service';
import { useNavigate } from 'react-router';
import QNA_CATEGORY from '../utils/QnaCategory';

function QnaListPage() {
  const navigate = useNavigate();
  const [qnaList, setQnaList] = useState([]); // API로 받은 전체 질문 목록
  const [selectedCategory, setSelectedCategory] = useState(QNA_CATEGORY[0]);
  // API 호출
  useEffect(() => {
    const fetchQnaList = async () => {
      const asks = await getQnaList(selectedCategory.api);
      setQnaList(asks);
    };

    fetchQnaList();
  }, [selectedCategory]); 

  const updateSelectedCategory = (category) => {
    setSelectedCategory(category);
  };

  const moveQnaDetailPage = (id) => {
    navigate(`/qna/${id}`);
  }

  if(!qnaList) {
    return (
      <DefaultLayout>
        <div className="qna-page-container">
          <h1 className="page-main-title">Q&A</h1>
          
          <nav className="category-menu-bar">
            {QNA_CATEGORY.map((category) => (
              <div 
                key={category.name} 
                className={`menu-item ${selectedCategory.name === category.name ? "selected" : ""}`}
                onClick={() => updateSelectedCategory(category)}
              >
                {/* a 태그 대신 div나 button으로 의미를 명확히 할 수 있습니다. */}
                <span>{category.name}</span>
              </div>
            ))}
          </nav>
          <p>등록된 질문이 없습니다.</p>
      </div>
      
      </DefaultLayout>
    )
  }

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">Q&A</h1>
        
        <nav className="category-menu-bar">
          {QNA_CATEGORY.map((category) => (
            <div 
              key={category.name} 
              className={`menu-item ${selectedCategory.name === category.name ? "selected" : ""}`}
              onClick={() => updateSelectedCategory(category)}
            >
              {/* a 태그 대신 div나 button으로 의미를 명확히 할 수 있습니다. */}
              <span>{category.name}</span>
            </div>
          ))}
        </nav>

        <section className="qna-list-section">
            {qnaList.map((qna) => <QnaItem key={qna.id} qna={qna} onClick={moveQnaDetailPage}/>)}
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
