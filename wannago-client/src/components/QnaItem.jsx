import '../assets/css/qna-list-frame.css';

function QnaItem ({qna}) {
  return (
    <article className="qna-frame"> {/* div 대신 article 태그 사용 */}
      <header className="qna-header">
        <h2 className="qna-title">{qna.title}</h2> {/* 제목은 h2 태그로 */}
        <div className={`qna-status ${qna.isAccepted ? "accepted" : "pending"}`}> {/* 상태에 따라 클래스 동적 할당 */}
          {qna.isAccepted ? "채택완료" : "채택대기"}
        </div>
      </header>
      <div className="qna-meta">
          <span className="qna-author">{qna.author}</span>
          <time className="qna-date">{qna.createdDate}</time> {/* time 태그 사용 */}
        </div>
      <p className="qna-contents">{qna.contents}</p>
    </article>
  );
};

export default QnaItem;