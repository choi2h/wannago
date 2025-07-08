import '../assets/css/input-post.css';
import Tag from './Tag';
import TagsForm from './TagsForm';

function PostForm ({post, updateTitle, updateTags, updateContents}) {
  return (
    <div className="input-post">
        <input 
          type="text" 
          placeholder="제목을 입력하세요." 
          className="title-input"
          value={post?.title || ''}
          onChange={(event) => updateTitle(event.target.value)}
        />
        <TagsForm tags={post?.tags || []} setTags={updateTags}/>

        <div className="divider" style={{margin: "0"}}/>
        
        <textarea 
          placeholder="내용을 입력하세요."
          className="content-input"
          rows="15"
          value={post?.contents || ''}
          onChange={(event) => updateContents(event.target.value)}

        />
    </div>
  );
};

export default PostForm;