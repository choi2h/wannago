import { useState } from 'react';
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