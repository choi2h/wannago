import '../assets/css/comment.css';

function Comment({ comment }) {
  return (
    <div className="comment-wrapper">
      <div className="comment">
        <div className="comment-header">
          <span className="comment-author">{comment.name}</span>
          <span className="comment-separator">·</span>
          <span className="comment-time">{comment.time}</span>
        </div>
        
        <p className="comment-content">
          {comment.contents}
        </p>
        
        <button className="comment-menu">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="12" cy="12" r="1"/>
            <circle cx="19" cy="12" r="1"/>
            <circle cx="5" cy="12" r="1"/>
          </svg>
        </button>
      </div>
      
      {/* SubComments 렌더링 */}
      {comment.subComments && comment.subComments.length > 0 && (
        <div className="subcomments-container">
          {comment.subComments.map((subComment, index) => (
            <SubComment key={index} comment={subComment} />
          ))}
        </div>
      )}
    </div>
  );
}

function SubComment({ comment }) {
  return (
    <div className="subcomment">
      <div className="subcomment-header">
        <span className="subcomment-author">{comment.name}</span>
        <span className="subcomment-separator">·</span>
        <span className="subcomment-time">{comment.time}</span>
      </div>
      
      <p className="subcomment-content">
        {comment.contents}
      </p>
      
      <button className="subcomment-menu">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <circle cx="12" cy="12" r="1"/>
          <circle cx="19" cy="12" r="1"/>
          <circle cx="5" cy="12" r="1"/>
        </svg>
      </button>
    </div>
  );
}

export default Comment;