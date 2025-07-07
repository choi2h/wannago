import { useState } from 'react';

// 아이콘 SVG 컴포넌트들
const MessageCircleIcon = ({ size = 16 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"/>
  </svg>
);

const Edit3Icon = ({ size = 16 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M12 20h9"/>
    <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/>
  </svg>
);

const Trash2Icon = ({ size = 16 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <polyline points="3,6 5,6 21,6"/>
    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
    <line x1="10" y1="11" x2="10" y2="17"/>
    <line x1="14" y1="11" x2="14" y2="17"/>
  </svg>
);

function Comment({ comment, postId, onAddReply, onUpdateComment, onDeleteComment }) {
  const [showReplyInput, setShowReplyInput] = useState(false);
  const [replyContent, setReplyContent] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [editedContent, setEditedContent] = useState(comment?.contents || '');

  const isTopLevelComment = comment?.parentId === null;

  // comment가 없는 경우 처리
  if (!comment) {
    return null;
  }

  const handleReplyClick = () => {
    setShowReplyInput(!showReplyInput);
    setReplyContent('');
  };

  const handleReplySubmit = () => {
    console.log('Comment 컴포넌트 - 대댓글 제출: parentId =', comment.id);
    onAddReply(comment.id, replyContent);
    setShowReplyInput(false);
    setReplyContent('');
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setEditedContent(comment?.contents || '');
  };

  const handleSaveEdit = () => {
    console.log('Comment 컴포넌트 - 댓글 수정: commentId =', comment.id, 'editedContent =', editedContent);
    onUpdateComment(comment.id, editedContent);
    setIsEditing(false);
  };

  const handleDeleteClick = () => {
    console.log('Comment 컴포넌트 - 댓글 삭제: commentId =', comment.id);
    onDeleteComment(comment.id);
  };

  return (
    <div className="comment-wrapper">
      <div className="comment">
        <div className="comment-header">
          <span className="comment-author">{comment?.name || comment?.author || '익명'}</span>
          <span className="comment-separator">·</span>
          <span className="comment-time">{comment?.time || comment?.createdAt || ''}</span>
        </div>
        
        {/* 우측 상단 액션 버튼들 */}
        <div className="comment-actions-top">
          {!isEditing && (
            <>
              {isTopLevelComment && (
                <button 
                  className="action-icon-button" 
                  onClick={handleReplyClick}
                  title="답글"
                >
                  <MessageCircleIcon size={16} />
                </button>
              )}
              {isTopLevelComment && (
                <button 
                  className="action-icon-button" 
                  onClick={handleEditClick}
                  title="수정"
                >
                  <Edit3Icon size={16} />
                </button>
              )}
              <button 
                className="action-icon-button" 
                onClick={handleDeleteClick}
                title="삭제"
              >
                <Trash2Icon size={16} />
              </button>
            </>
          )}
        </div>
        
        {isEditing ? (
          <div className="comment-edit-section">
            <textarea 
              className="comment-edit-textarea"
              value={editedContent}
              onChange={(e) => setEditedContent(e.target.value)}
            />
            <div className="comment-edit-buttons">
              <button className="action-button save" onClick={handleSaveEdit}>저장</button>
              <button className="action-button cancel" onClick={() => setIsEditing(false)}>취소</button>
            </div>
          </div>
        ) : (
          <p className="comment-content" style={{ whiteSpace: 'pre-line' }}>
            {comment?.contents || ''}
          </p>
        )}
      </div>
      
      {/* 대댓글 입력 필드 */}
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
      {comment?.replies && comment.replies.length > 0 && (
        <div className="subcomments-container">
          {comment.replies.map((subComment, index) => (
            <SubComment 
              key={subComment?.id || index} 
              comment={subComment} 
              postId={postId} 
              onDeleteComment={onDeleteComment}
            />
          ))}
        </div>
      )}
    </div>
  );
}

function SubComment({ comment, postId, onDeleteComment }) {
  // comment가 없는 경우 처리
  if (!comment) {
    return null;
  }

  const handleDeleteClick = () => {
    console.log('SubComment 컴포넌트 - 대댓글 삭제: commentId =', comment?.id);
    onDeleteComment(comment?.id);
  };

  return (
    <div className="subcomment">
      <div className="subcomment-header">
        <span className="subcomment-author">{comment?.name || comment?.author || '익명'}</span>
        <span className="subcomment-separator">·</span>
        <span className="subcomment-time">{comment?.time || comment?.createdAt || ''}</span>
      </div>
      
      {/* 우측 상단 삭제 버튼 */}
      <div className="subcomment-actions-top">
        <button 
          className="action-icon-button" 
          onClick={handleDeleteClick}
          title="삭제"
        >
          <Trash2Icon size={14} />
        </button>
      </div>
      
      <p className="subcomment-content" style={{ whiteSpace: 'pre-line' }}>
        {comment?.contents || ''}
      </p>
    </div>
  );
}

export default Comment;