import '../assets/css/qna-detail.css';
import QNA_CATEGORY from '../utils/QnaCategory';

function AskViewer ({ ask, onClickEdit, onClickDelete }) {

  const getCategoryName = (category) => {
    let name = "기타";
    QNA_CATEGORY.forEach((qnaCategory) => {
      if(qnaCategory.api.toUpperCase() === category.toUpperCase()) name = qnaCategory.name;
    })

    return name;
  }
  
  return (
    <div className="view">
      <div className="category-label">{getCategoryName(ask.category)}</div>
      <div className="question-title">Q. {ask.title}</div>
        <div className="edit-delete-section">
          <span className="text-wrapper-2 edit-btn" onClick={onClickEdit}>수정</span>
            <span className="text-wrapper-2">|</span>
          <span className="text-wrapper-2 delete-btn" onClick={onClickDelete}>삭제</span>
        </div>

      <div className="author-info">
        <div className="author-name">{ask.author}</div>
        <div className="author-date">{ask.createdTime}</div>
      </div>

      <p className="question-contents">{ask.contents}</p>
    </div>
  );
};

export default AskViewer;