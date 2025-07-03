import '../assets/css/button.css';

const getButtonByType = (type, text, onClick) => {
  if(type === 'secondary') {
    return (
        <div className="secondary-button">
          <div className="button-wrapper" onClick={onClick}>
            <button className="button">{text}</button>
          </div>
        </div>
    )
  } else if( type === 'positive') {
     return ( 
        <div className="tab" onClick={onClick}>
          <div className="text-wrapper-19">{text}</div>
        </div>
     )
  } else if (type === 'negative') {
    return ( 
      <div className="tab-2" onClick={onClick}>
        <div className="text-wrapper-20">{text}</div>
      </div>
    )
  }

  return '버튼';
}

function Button({type, text, onClick}) {
    return getButtonByType(type, text, onClick);
}

export default Button;