import '../assets/css/input-post.css';
import Tag from './Tag';

function PostForm () {
  return (
    <div className="input-post">
        <input 
          type="text" 
          placeholder="제목을 입력하세요." 
          className="title-input"
        />
        <div className="tag">
          <Tag type='input' text='tag1'/>
          <Tag type='input' text='tag2'/>
        </div>

        <div className="divider" />
        
        <textarea 
          placeholder="내용을 입력하세요."
          className="content-input"
          rows="15"
        />
    </div>
  );
};

export default PostForm;