import '../assets/css/tags-input.css';
import Tag from './Tag';

function TagsForm({tags, setTags}) {
  const removeTags = (indexToRemove) => {
    // 태그를 삭제하는 메소드
    const filter = tags.filter((el,index) => index !== indexToRemove);
    setTags(filter);
  };

  const addTags = (event) => {
    // tags 배열에 새로운 태그를 추가하는 메소드 
    const inputVal = event.target.value;
    // 이미 입력되어 있는 태그인지 검사하여 이미 있는 태그라면 추가하지 말기 
    // 아무것도 입력하지 않은 채 Enter 키 입력시 메소드 실행하지 말기
    // 태그가 추가되면 input 창 비우기 
    if(event.key === "Enter" && inputVal !== '' && !tags.includes(inputVal)){
      setTags([...tags,inputVal]);
      event.target.value = '';
    }
  };


  return (
      <div className='tags-input'>

        {tags.length === 0 ? 
          ""
          :
          <ul className="tags-list">
            {tags.map((tag, index) => (
              <Tag type="input" text={tag} onClick={() => removeTags(index)}/>
            ))}
          </ul>
        }
        <input
          className="tags-input"
          type="text"
          onKeyUp={(e) => {
            { addTags(e) }
          }}
          placeholder={tags.length === 0 ? "태그를 입력하세요." : ""}
        />
      </div>
  );
}

export default TagsForm;