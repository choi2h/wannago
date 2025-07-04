import '../assets/css/input-post.css';
import Tag from './Tag';
import TagsForm from './TagsForm';

function PostForm ({updateTitle, tags, updateTags, updateContents}) {
  return (
    <div className="input-post">
        <input 
          type="text" 
          placeholder="제목을 입력하세요." 
          className="title-input"
          onChange={(event) => updateTitle(event.target.value)}
        />
        <TagsForm tags={tags} setTags={updateTags}/>

        <div className="divider" style={{margin: "0"}}/>
        
        <textarea 
          placeholder="내용을 입력하세요."
          className="content-input"
          rows="15"
          onChange={(event) => updateContents(event.target.value)}

        />
    </div>
  );
};

export default PostForm;