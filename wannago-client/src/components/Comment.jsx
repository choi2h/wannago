import { useState } from 'react'; // useState 임포트
import '../assets/css/comment.css';

// SubComment 컴포넌트도 같은 파일 내에 정의되어 있으므로 함께 수정합니다.
// SubComment는 이 파일의 가장 하단에 위치

function Comment({ comment, postId, onAddReply, onUpdateComment, onDeleteComment }) {
  const [showReplyInput, setShowReplyInput] = useState(false); // 대댓글 입력창 표시 여부
  const [replyContent, setReplyContent] = useState(''); // 대댓글 내용
  const [isEditing, setIsEditing] = useState(false); // 댓글 수정 모드 여부
  const [editedContent, setEditedContent] = useState(comment.contents); // 수정 중인 댓글 내용

  // CommentResponse DTO에 parentId가 null이면 최상위 댓글, 아니면 대댓글
  // 이 정보를 바탕으로 UI를 조건부로 렌더링합니다.
  const isTopLevelComment = comment.parentId === null;

  const handleReplyClick = () => {
    setShowReplyInput(!showReplyInput);
    setReplyContent(''); // 입력창 열 때 내용 초기화
  };

  const handleReplySubmit = () => {
    console.log('Comment 컴포넌트 - 대댓글 제출: parentId =', comment.id); // 디버깅용
    onAddReply(comment.id, replyContent); // comment.id 사용
    setShowReplyInput(false);
    setReplyContent('');
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setEditedContent(comment.contents); // 수정 모드 진입 시 기존 내용으로 초기화
  };

  const handleSaveEdit = () => {
    console.log('Comment 컴포넌트 - 댓글 수정: commentId =', comment.id, 'editedContent =', editedContent); // 디버깅용
    onUpdateComment(comment.id, editedContent); // comment.id 사용
    setIsEditing(false);
  };

  const handleDeleteClick = () => {
    console.log('Comment 컴포넌트 - 댓글 삭제: commentId =', comment.id); // 디버깅용
    onDeleteComment(comment.id); // comment.id 사용
  };

  return (
    <div className="comment-wrapper">
      <div className="comment">
        <div className="comment-header">
          <span className="comment-author">{comment.name || comment.author}</span> {/* 백엔드 응답에 따라 name 또는 author */}
          <span className="comment-separator">·</span>
          <span className="comment-time">{comment.time || comment.createdAt}</span> {/* 백엔드 응답에 따라 time 또는 createdAt */}
        </div>
        
        {isEditing ? (
          <textarea 
            className="comment-edit-textarea"
            value={editedContent}
            onChange={(e) => setEditedContent(e.target.value)}
          />
        ) : (
          <p className="comment-content" style={{ whiteSpace: 'pre-line' }}>
            {comment.contents}
          </p>
        )}
        
        <div className="comment-actions">
          {isEditing ? (
            <>
              <button className="action-button" onClick={handleSaveEdit}>저장</button>
              <button className="action-button" onClick={() => setIsEditing(false)}>취소</button>
            </>
          ) : (
            <>
              {isTopLevelComment && ( // 최상위 댓글일 경우에만 '답글' 버튼 렌더링
                <button className="action-button" onClick={handleReplyClick}>답글</button>
              )}
              {isTopLevelComment && ( // 최상위 댓글일 경우에만 '수정' 버튼 렌더링
                <button className="action-button" onClick={handleEditClick}>수정</button>
              )}
              <button className="action-button" onClick={handleDeleteClick}>삭제</button> {/* 댓글/대댓글 모두 삭제 가능 */}
            </>
          )}
        </div>
      </div>
      
      {/* 대댓글 입력 필드 (최상위 댓글에만 표시) */}
      {showReplyInput && isTopLevelComment && (
        <div className="reply-input-container">
          <textarea
            className="reply-textarea"
            placeholder="대댓글을 입력하세요..."
            value={replyContent}
            onChange={(e) => setReplyContent(e.target.value)}
          />
          <button className="reply-submit-button" onClick={handleReplySubmit}>
            대댓글 등록
          </button>
        </div>
      )}

      {/* SubComments 렌더링 */}
      {comment.replies && comment.replies.length > 0 && (
        <div className="subcomments-container">
          {comment.replies.map((subComment, index) => (
            <SubComment 
                key={subComment.id || index} 
                comment={subComment} 
                postId={postId} 
                // 대댓글은 수정 기능이 없으므로 onUpdateComment는 SubComment로 전달하지 않아도 됨
                // 하지만 onUpdateComment는 부모 Comment 컴포넌트에서 사용될 수 있으므로, 굳이 제거하지는 않습니다.
                // SubComment 내부에서 사용하지 않도록만 처리합니다.
                onDeleteComment={onDeleteComment}
            />
          ))}
        </div>
      )}
    </div>
  );
}

// SubComment 컴포넌트: 대댓글은 삭제만 가능합니다.
function SubComment({ comment, postId, onDeleteComment }) { // onUpdateComment는 더 이상 사용되지 않으므로 제거
    const handleDeleteClick = () => {
        console.log('SubComment 컴포넌트 - 대댓글 삭제: commentId =', comment.id); // 디버깅용
        onDeleteComment(comment.id); // comment.id 사용
    };

  return (
    <div className="subcomment">
      <div className="subcomment-header">
        <span className="subcomment-author">{comment.name || comment.author}</span>
        <span className="subcomment-separator">·</span>
        <span className="subcomment-time">{comment.time || comment.createdAt}</span>
      </div>
      
      {/* isEditing 상태가 없으므로 항상 <p> 태그만 렌더링 */}
      <p className="subcomment-content" style={{ whiteSpace: 'pre-line' }}>
        {comment.contents}
      </p>
      
      <div className="subcomment-actions">
        {/* 대댓글은 수정 기능이 없으므로 수정 관련 버튼을 렌더링하지 않음 */}
        <button className="action-button" onClick={handleDeleteClick}>삭제</button> {/* 대댓글은 삭제만 가능 */}
      </div>
    </div>
  );
}

export default Comment;