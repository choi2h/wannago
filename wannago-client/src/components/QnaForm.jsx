/*import { useState } from 'react';
import '../assets/css/input-post.css';

function QnaForm () {
  const categories = ["맛집", "액티비티", "기타", "명소"];
  const [selectedCategory, setSelectedCategory] = useState('');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
    setIsDropdownOpen(false);
  };

  return (
     <div className="input-post">
        <div className="category-dropdown">
          <div 
            className="category-button"
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          >
            <span className={selectedCategory ? 'selected' : 'placeholder'}>
              {selectedCategory || '카테고리를 선택해주세요.'}
            </span>
            <svg 
              className={`dropdown-arrow ${isDropdownOpen ? 'open' : ''}`}
              viewBox="0 0 24 24" 
              fill="none" 
              stroke="currentColor" 
              strokeWidth="2"
            >
              <polyline points="6,9 12,15 18,9"/>
            </svg>
          </div>
          
          {isDropdownOpen && (
            <div className="dropdown-menu">
              {categories.map((category, index) => (
                <div 
                  key={index}
                  className="dropdown-item"
                  onClick={() => handleCategorySelect(category)}
                >
                  {category}
                </div>
              ))}
            </div>
          )}
        </div>

        <input 
          type="text" 
          placeholder="제목을 입력하세요." 
          className="title-input"
        />

        <div className="divider" />
        
        <textarea 
          placeholder="내용을 입력하세요."
          className="content-input"
          rows="15"
        />
    </div>
  );
};

export default QnaForm;
*/

import { useState } from 'react';
import '../assets/css/input-post.css';

// 부모 컴포넌트(QnaWritePage)로부터 필요한 데이터와 함수, 상태를 props로 받아옵니다.
function QnaForm({ qnaData, handleChange, isEditMode }) {
  const categories = ["FOOD", "ACTIVITY", "OTHER", "LOCATION"];
  
  // 드롭다운 메뉴가 열렸는지 닫혔는지만 관리하는 내부 state
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  // 커스텀 드롭다운에서 카테고리를 선택했을 때 실행될 함수
  const handleCategorySelect = (category) => {
    // 부모의 handleChange 함수를 호출하여 'category' 상태를 업데이트합니다.
    // HTML select 태그의 동작을 모방하여 event 객체 형식으로 전달합니다.
    handleChange({
      target: {
        name: 'category',
        value: category,
      },
    });
    setIsDropdownOpen(false); // 드롭다운 메뉴를 닫습니다.
  };

  return (
    // 시맨틱한 구조와 기본 동작 방지를 위해 form 태그로 감쌉니다.
    <form className="input-post" onSubmit={(e) => e.preventDefault()}>
      <h1 style={{ marginBottom: '2rem', width: '100%', textAlign: 'center' }}>
        {isEditMode ? '질문 수정' : '질문 작성'}
      </h1>

      {/* --- 커스텀 카테고리 드롭다운 (UI 유지) --- */}
      <div className="category-dropdown">
        <div 
          className="category-button"
          onClick={() => setIsDropdownOpen(!isDropdownOpen)}
        >
          {/* 선택된 값은 부모로부터 받은 qnaData.category를 사용합니다. */}
          <span className={qnaData.category ? 'selected' : 'placeholder'}>
            {qnaData.category || '카테고리를 선택해주세요.'}
          </span>
          <svg 
            className={`dropdown-arrow ${isDropdownOpen ? 'open' : ''}`}
            viewBox="0 0 24 24" 
            fill="none" 
            stroke="currentColor" 
            strokeWidth="2"
          >
            <polyline points="6,9 12,15 18,9"/>
          </svg>
        </div>
        
        {isDropdownOpen && (
          <div className="dropdown-menu">
            {categories.map((category, index) => (
              <div 
                key={index}
                className="dropdown-item"
                onClick={() => handleCategorySelect(category)}
              >
                {category}
              </div>
            ))}
          </div>
        )}
      </div>

      {/* --- 제목 입력 (로직 연결) --- */}
      <input 
        type="text" 
        placeholder="제목을 입력하세요." 
        className="title-input"
        name="title" // ★★★ 상태 업데이트를 위한 name 속성
        value={qnaData.title} // 부모로부터 받은 값
        onChange={handleChange} // 부모로부터 받은 함수
      />

      <div className="divider" />
      
      {/* --- 내용 입력 (로직 연결) --- */}
      <textarea 
        placeholder="내용을 입력하세요."
        className="content-input"
        rows="15"
        name="content" // ★★★ 상태 업데이트를 위한 name 속성
        value={qnaData.content} // 부모로부터 받은 값
        onChange={handleChange} // 부모로부터 받은 함수
      />
    </form>
  );
};

export default QnaForm;
