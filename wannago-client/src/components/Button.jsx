// Button.jsx (이전 수정된 코드와 동일)
import '../assets/css/button.css';

function Button({ type = 'button', text, onClick, ...props }) {
    let className = "my-custom-button-base"; // 모든 버튼에 공통적으로 적용될 기본 클래스

    if (type === 'secondary') {
        className += " secondary-button";
    } else if (type === 'positive') {
        className += " tab";
    } else if (type === 'negative') {
        className += " tab-2";
    } else if (type === 'mini') {
        className += " mini-button";
    }

    return (
        <button
            type={type} // 🚨 이 부분이 중요! MyPageEdit에서 넘어온 type="submit"을 받아서 적용
            className={className}
            onClick={onClick}
            {...props}
        >
            <div className="button-text-wrapper">{text}</div>
        </button>
    );
}

export default Button;