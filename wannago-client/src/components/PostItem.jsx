import { useNavigate } from 'react-router';
import '../assets/css/post-list-frame.css';
import Tag from './Tag';

function PostItem ({post}) {
  const navigate = useNavigate();
  const onClickPostItem = () => {
      navigate(`/post/${post.id}`);
  }

  return (
    <article className="post-frame" onClick={onClickPostItem}>
      <header className="post-header">
        <h2 className="post-title">{post.title}</h2>
      </header>
      <div className="post-meta">
          <span className="post-author">{post.author}</span>
          <time className="post-date">{post.createdDate}</time>
        </div>
      <p className="post-contents">{post.contents}</p>
      <div className='post-list-footer'>
      <div className="post-tags">
          {post.tags && post.tags.length > 0 && post.tags.map((tag, index) => (
            <Tag key={index} type='view' text={tag}/>
          ))}
        </div>
        <div className="post-likes">
            <img
              className="view-heart-icon"
              alt="Heart icon"
              src="https://c.animaapp.com/mccxjumpIKwo6s/img/free-icon-like-6924834-1.png"
            />
            <span className="like-count">{post.likeCount}</span>
          </div>
      </div>
    </article>
  );
};

export default PostItem;