import { useState } from 'react';
import '../assets/css/qna-detail.css';

function AnswerItem({
  answer,
  isQuestionAuthor,
  hasAcceptedAnswer,
  onAccept,
  onDelete,
  onEdit,
  currentUser,
  isFirstAccepted
}) {
  const [isEditing, setIsEditing] = useState(false);
  const [editContent, setEditContent] = useState(answer.contents);

  const handleAccept = () => {
    if (window.confirm('이 답변을 채택하시겠습니까? 채택 후에는 변경할 수 없습니다.')) {
      onAccept(answer.answerId);
    }
  };

  const handleDelete = () => {
    if (window.confirm('정말로 이 답변을 삭제하시겠습니까?')) {
      onDelete(answer.answerId);
    }
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleSaveEdit = () => {
    if (!editContent || editContent.trim() === '') {
      alert('답변 내용을 입력해주세요.');
      return;
    }

    if (editContent.trim().length < 1) {
      alert('답변은 최소 10자 이상 입력해주세요.');
      return;
    }

    onEdit(answer.answerId, editContent.trim());
    setIsEditing(false);
  };

  const handleCancelEdit = () => {
    setEditContent(answer.contents);
    setIsEditing(false);
  };

  // 답변 작성자 여부 확인
  const isAnswerAuthor = currentUser && (
    answer.author === currentUser.name ||
    answer.author === currentUser.id ||
    answer.userId === currentUser.id
  );

  // IS_Accept 값 확인 (DB에서 1이면 채택, 0이면 미채택)
  const isAccepted = answer.accepted === true

  // 답변 상태 결정
  const getAnswerStatus = () => {
    if (isAccepted) {
      return { text: "✅ 채택됨", className: "accepted" };
    } else if (hasAcceptedAnswer) {
      // 다른 답변이 채택된 경우
      return { text: "", className: "not-accepted" };
    } else {
      // 아직 채택된 답변이 없는 경우
      return { text: "⏳ 채택대기", className: "waiting" };
    }
  };

  const answerStatus = getAnswerStatus();

  return (
    <div className={`answer-item ${isAccepted ? 'accepted' : ''} ${hasAcceptedAnswer && !isAccepted ? 'not-accepted' : ''} ${isFirstAccepted ? 'first-accepted' : ''}`}>

      {/* 채택된 답변 최상단 뱃지 */}
      {isAccepted && (
        <div className="accepted-top-badge">
          <span className="accepted-icon"></span>
          <span className="accepted-text">✅ 채택된 답변 </span>
          <span className="accepted-pin"></span> {/* 📌 상단고정 */}
        </div>
      )}

      <div className="answer-header">
        <div className="author-info">
          <div className="author-details">
            <div className="author-name">
              {answer.author}
              {isAnswerAuthor && <span className="author-badge">내 답변</span>}
              {isAccepted && <span className="accepted-author-badge"></span>}
            </div>
            <div className="author-date">{answer.createdDate}</div>
          </div>
        </div>

        <div className="answer-actions">
          <div className={`status-badge ${answerStatus.className}`}>
            {answerStatus.text}
          </div>

          {/* 채택 버튼 - 질문 작성자만 보임, 아직 채택된 답변이 없을 때만 */}
          {isQuestionAuthor && !hasAcceptedAnswer && !isAccepted && (
            <button
              className="accept-button"
              onClick={handleAccept}
              title="답변 채택"
            >
              채택하기
            </button>
          )}

          {/* 수정/삭제 버튼 - 답변 작성자만 보임, 편집 모드가 아닐 때만 */}
          {isAnswerAuthor && !isEditing && (
            <div className="answer-controls">
              <button
                className="edit-button"
                onClick={handleEdit}
                title="답변 수정"
                disabled={isAccepted} // 채택된 답변은 수정 불가
              >
                수정
              </button>
              <button
                className="delete-button"
                onClick={handleDelete}
                title="답변 삭제"
                disabled={isAccepted} // 채택된 답변은 삭제 불가
              >
                삭제
              </button>
            </div>
          )}
        </div>
      </div>

      {/* 답변 내용 */}
      <div className="answer-content">
        {isEditing ? (
          <div className="edit-form">
            <textarea
              value={editContent}
              onChange={(e) => setEditContent(e.target.value)}
              className="edit-textarea"
              rows={6}
              placeholder="답변을 입력하세요..."
              maxLength={2000}
            />
            <div className="edit-actions">
              <div className="char-count">
                {editContent.length}/2000자
                <span className="min-length">
                  {editContent.trim().length < 1 ? ' (최소 1자)' : ''}
                </span>
              </div>
              <div className="edit-buttons">
                <button
                  className="save-button"
                  onClick={handleSaveEdit}
                  disabled={editContent.trim().length < 1}
                >
                  저장
                </button>
                <button
                  className="cancel-button"
                  onClick={handleCancelEdit}
                >
                  취소
                </button>
              </div>
            </div>
          </div>
        ) : (
          <div className="answer-text">
            {answer.contents}
          </div>
        )}
      </div>

      {/* 채택된 답변 하단 뱃지 */}
      {isAccepted && (
        <div className="accepted-bottom-badge">
          <div className="accepted-line"></div>
          <div className="accepted-info">
            {/* <span className="accepted-icon">🎉</span>
            <span className="accepted-message">이 답변이 채택되었습니다!</span>
            <span className="accepted-reward">+50 포인트</span> */}
          </div>
        </div>
      )}

      {/* 채택된 답변 수정/삭제 제한 안내 */}
      {isAccepted && isAnswerAuthor && (
        <div className="accepted-restriction-notice">
          <small>💡 채택된 답변은 수정 및 삭제가 제한됩니다.</small>
        </div>
      )}
    </div>
  );
}

export default AnswerItem;