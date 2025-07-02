import { VuesaxLinearGallery } from '../assets/icons/VuesaxLinearGallery';
import { TimeIcon, LocationIcon } from '../assets/icons/Icons';
import '../assets/css/input-schedule.css';
import { useState } from 'react';
import Modal from './MapModal';
import SearchLocation from './SearchLocation';

function ScheduleForm({newSchedule, setNewSchedule, idx}){
  const [isOpenLocationModal, setIsOpenLocationModal] = useState(false);

  const updateTitle = (title) => {
    setNewSchedule(idx, {...newSchedule, title});
  }

  const updateLocation = ({name, lat, lng}) => {
    console.log(name + " lat:" + lat + " lng:" + lng);
    const locationName = name;
    setNewSchedule(idx, {...newSchedule, locationName, lat, lng});
    console.log(newSchedule);
  }

  const updateContents = (contents) => {
    const modifiedSchedule = {...newSchedule, contents};
    setNewSchedule(idx, modifiedSchedule);
  }

  const updateTime = (time) => {
    setNewSchedule(idx, {...newSchedule, time});
  }

  return (
    <div className="input-schedule">
      <div className="schedule-header">
        <div className="day-label">{idx+1} 일차</div>
        
        <input 
          type="text" 
          placeholder="제목을 입력하세요" 
          className="schedule-title-input"
          onChange={(event) => updateTitle(event.target.value)}
        />
      </div>

      <div className="schedule-controls">
        <div className="time-input-container">
          <TimeIcon className="input-icon"/>
          <input 
            type="time" 
            className="time-input"
            defaultValue="12:00"
            onChange={(event) => updateTime(event.target.value)}
          />
        </div>
        
        <div className="location-input-container">
          <LocationIcon className="input-icon"/>
          <input 

            type="text"
            placeholder="장소를 입력하세요"
            className="location-input"
            value={newSchedule?.locationName || ''} // 안전한 접근
            onClick={() => {
              console.log('click!!')
              setIsOpenLocationModal(true);
            }}
            readOnly
          />
        </div>
      </div>

      <textarea 
        placeholder="내용을 입력하세요."
        className="schedule-content-input"
        rows="8"
        onChange={(event) =>updateContents(event.target.value)}
      />

     <div className="media-input-container">
        <input 
          type="file"
          accept="image/*"
          className="media-file-input"
          id="media-input"
        />
        <label htmlFor="media-input" className="media-input-label">
          <VuesaxLinearGallery className="media-icon" />
          이미지 추가
        </label>
      </div>
      {
      isOpenLocationModal ? 
      <Modal><SearchLocation selectedPlace={newSchedule.location} setPlaceInfo={updateLocation} setIsOpenModal={setIsOpenLocationModal}/></Modal> : ""
    }
    </div>
  );
};

export default ScheduleForm;