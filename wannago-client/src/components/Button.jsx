import '../assets/css/button.css';

const getButtonByType = (type, text, onClick) => {
  if(type === 'secondary') {
    return (
        <div className="secondary-button">
          <div className="button-wrapper">
            <button className="button" onClick={onClick}>{text}</button>
          </div>
        </div>
    )
  } else if( type === 'positive') {
     return ( 
        <div className="tab">
          <div className="text-wrapper-19" onClick={onClick}>{text}</div>
        </div>
     )
  } else if (type === 'negative') {
    return ( 
      <div className="tab-2">
        <div className="text-wrapper-20" onClick={onClick}>{text}</div>
      </div>
    )
  }

  return '버튼';
}

function Button({type, text, onClick}) {
    return getButtonByType(type, text, onClick);
}

export default Button;