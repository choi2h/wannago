import React, { useState } from 'react';
import '../assets/css/input-comment.css';

function Button({ type, text, onClick, disabled }) {
  return (
    <button 
      className={`btn btn-${type}`}
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </button>
  );
}

function InputComment({ typeText, onSubmit }) {
  const [comment, setComment] = useState('');

  const handleSubmit = () => {
    if (comment.trim()) {
      onSubmit && onSubmit(comment);
      setComment(''); // 댓글 작성 후 입력 필드 초기화
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSubmit();
    }
  };

  return (
    <div className="input-comment">
      <div className="comment-input-row">
        <textarea
          className="comment-input"
          placeholder={`${typeText}을 입력하세요...`}
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          onKeyDown={handleKeyPress}
          rows="3"
        />
        <Button 
          type="positive" 
          text={`${typeText} 작성`}
          onClick={handleSubmit}
          disabled={!comment.trim()}
        />
      </div>
    </div>
  );
}

export default InputComment;