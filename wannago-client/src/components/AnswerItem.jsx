import '../assets/css/qna-detail.css';

function AnswerItem ({ answer }) {
  return (
    <div className="answer-item">
      <div className="answer-header">
        <div className="author-info">
          <div className="author-details">
            <div className="author-name">{answer.author}</div>
            <div className="author-date">{answer.createdTime}</div>
          </div>
        </div>
        <div className="answer-status">
          <div className={`status-badge ${answer.isAccepted ? 'accepted' : 'waiting'}`}>
            {answer.isAccepted ? "채택답글" : "채택대기"}
          </div>
        </div>
      </div>
      <div className="answer-content">{answer.contents}</div>
    </div>
  );
};

export default AnswerItem;