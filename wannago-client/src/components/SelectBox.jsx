import {IconOutlinedActionMainClock1} from '../icons/IconOutlinedActionMainClock1';
import '../css/select-box.css';

function SelectBox() {
    return (
      <div className="group-3">
        <div className="input-2">
          <IconOutlinedActionMainClock1 className="icon-outlined-action-main-clock-1" />
          <div className="text-wrapper-15">시간을 선택하세요.</div>
        </div>

        <div className="input-3">
          <div className="content-3">
            <img
              className="vector-2"
              alt="Vector"
              src="https://c.animaapp.com/mcgerl86CUPwHM/img/vector.svg"
            />

            <div className="text-wrapper-16">장소를 입력하세요.</div>
          </div>
        </div>
      </div>
    );
}

export default SelectBox;