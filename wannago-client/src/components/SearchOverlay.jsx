import React, { useState, useEffect } from 'react';
import '../assets/css/search-overlay.css';
import { useNavigate } from 'react-router';

const SearchOverlay = ({ isOpen, onClose }) => {
  const [searchValue, setSearchValue] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (isOpen) {
      const timer = setTimeout(() => {
        document.getElementById('searchInput')?.focus();
      }, 100);
      return () => clearTimeout(timer);
    }
  }, [isOpen]);

  useEffect(() => {
    const handleEscape = (e) => {
      if (e.key === 'Escape' && isOpen) {
        onClose();
      }
    };

    document.addEventListener('keydown', handleEscape);
    return () => document.removeEventListener('keydown', handleEscape);
  }, [isOpen, onClose]);

  const handleSearch = (e) => {
    if (e.key === 'Enter') {
      const query = searchValue.trim();
      if (query) {
        console.log('검색어:', query);
        navigate(`/posts/search?keyword=${query}&page=0`, {replace: true})
        setSearchValue(''); // 검색 후 입력값만 초기화
        onClose();
      }
    }
  };

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  const handleClose = () => {
    setSearchValue('');
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="search-overlay" onClick={handleOverlayClick}>
      <div className="search-container">
        <input
          type="text"
          className="search-input"
          id="searchInput"
          placeholder="검색어를 입력하세요..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)}
          onKeyPress={handleSearch}
        />
        <button className="close-btn" onClick={handleClose}>
          ×
        </button>
      </div>
    </div>
  );
};

export default SearchOverlay;