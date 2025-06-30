import '../assets/css/qna-detail.css';

function AskViewer ({ ask }) {
  return (
    <div className="view">
      <div className="category-label">{ask.category}</div>
      <div className="question-title">Q. {ask.title}</div>

      <div className="author-info">
        <div className="author-name">{ask.author}</div>
        <div className="author-date">{ask.createdTime}</div>
      </div>

      <p className="question-contents">{ask.contents}</p>
    </div>
  );
};

export default AskViewer;