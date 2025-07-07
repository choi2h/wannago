import { useState } from 'react';
import '../assets/css/comment.css';

function Comment({ comment, postId, onAddReply, onUpdateComment, onDeleteComment }) {
  const [showReplyInput, setShowReplyInput] = useState(false);
  const [replyContent, setReplyContent] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [editedContent, setEditedContent] = useState(comment.contents);

  const isTopLevelComment = comment.parentId === null;

  const handleReplyClick = () => {
    setShowReplyInput(!showReplyInput);
    setReplyContent('');
  };

  const handleReplySubmit = () => {
    onAddReply(comment.id, replyContent);
    setShowReplyInput(false);
    setReplyContent('');
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setEditedContent(comment.contents);
  };

  const handleSaveEdit = () => {
    onUpdateComment(comment.id, editedContent);
    setIsEditing(false);
  };

  const handleDeleteClick = () => {
    onDeleteComment(comment.id);
  };

  return (
    <div className="comment-wrapper">
      <div className="comment">
        <div className="comment-header">
          <div className="comment-header-left">
            <span className="comment-author">{comment.name || comment.author}</span>
            <span className="comment-separator">·</span>
            <span className="comment-time">{comment.time || comment.createdAt}</span>
          </div>
          <div className="comment-actions">
            {isEditing ? (
              <>
                <button className="action-button" onClick={handleSaveEdit}>저장</button>
                <button className="action-button" onClick={() => setIsEditing(false)}>취소</button>
              </>
            ) : (
              <>
                {isTopLevelComment && (
                  <>
                    <button className="action-button" onClick={handleReplyClick}>답글</button>
                    <button className="action-button" onClick={handleEditClick}>수정</button>
                  </>
                )}
                <button className="action-button" onClick={handleDeleteClick}>삭제</button>
              </>
            )}
          </div>
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

        {comment.replies && comment.replies.length > 0 && (
          <div className="subcomments-container">
            {comment.replies.map((subComment, index) => (
              <SubComment
                key={subComment.id || index}
                comment={subComment}
                postId={postId}
                onDeleteComment={onDeleteComment}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

function SubComment({ comment, postId, onDeleteComment }) {
  const handleDeleteClick = () => {
    onDeleteComment(comment.id);
  };

  return (
    <div className="subcomment">
      <div className="subcomment-header">
        <div className="subcomment-header-left">
          <span className="subcomment-author">{comment.name || comment.author}</span>
          <span className="subcomment-separator">·</span>
          <span className="subcomment-time">{comment.time || comment.createdAt}</span>
        </div>
        <div className="subcomment-actions">
          <button className="action-button" onClick={handleDeleteClick}>삭제</button>
        </div>
      </div>

      <p className="subcomment-content" style={{ whiteSpace: 'pre-line' }}>
        {comment.contents}
        
      </p>
    </div>
  );
}

export default Comment;
