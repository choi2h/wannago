// InputComment.jsx (수정됨: 로직 제거, UI만 남음)
import React from 'react'; // useState는 더 이상 필요 없으므로 제거
import '../assets/css/input-comment.css'; // 기존 CSS 경로는 유지

/**
 * 재사용 가능한 Button 컴포넌트입니다.
 * 이 파일 내에 두거나, 별도의 파일로 분리할 수 있습니다.
 */
function Button({ type, text, onClick, disabled }) {
  return (
    <button
      className={`btn btn-${type}`}
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </button>
  );
}

/**
 * 댓글 또는 대댓글 입력을 위한 UI 컴포넌트입니다.
 * 이 컴포넌트는 자체적인 상태를 가지지 않고, 모든 데이터와 핸들러를 프롭스로 받습니다.
 *
 * @param {object} props - 컴포넌트 프롭스.
 * @param {string} props.value - textarea의 현재 값.
 * @param {function} props.onChange - textarea 값이 변경될 때 호출될 핸들러.
 * @param {string} props.typeText - 플레이스홀더와 버튼에 표시될 텍스트 (예: "댓글", "대댓글").
 * @param {function} props.onButtonClick - 버튼 클릭 시 호출될 핸들러.
 * @param {function} [props.onKeyDown] - 키 입력 시 호출될 핸들러 (Enter 제출 로직용).
 * @param {boolean} [props.disabled] - 버튼 비활성화 여부.
 */
function InputComment({ value, onChange, typeText, onButtonClick, onKeyDown, disabled }) {
  return (
    <div className="input-comment">
      <div className="comment-input-row">
        <textarea
          className="comment-input"
          placeholder={`${typeText}을 입력하세요...`}
          value={value} // 프롭스로 받은 value 사용
          onChange={onChange} // 프롭스로 받은 onChange 사용
          onKeyDown={onKeyDown} // 키 다운 이벤트 핸들러 추가
          rows="3"
        />
        <Button
          type="positive"
          text={`${typeText} 작성`}
          onClick={onButtonClick} // 프롭스로 받은 onButtonClick 사용
          disabled={disabled} // 프롭스로 받은 disabled 사용
        />
      </div>
    </div>
  );
}

export default InputComment;