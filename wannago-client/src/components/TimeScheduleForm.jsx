import Modal from './MapModal';
import SearchLocation from './SearchLocation';
import { VuesaxLinearGallery } from '../assets/icons/VuesaxLinearGallery';
import { TimeIcon, LocationIcon } from '../assets/icons/Icons';
import { useState } from 'react';
import '../assets/css/input-schedule.css';

function TimeScheduleForm({newSchedule, updateTimeSchedule, idx}){
  const [isOpenLocationModal, setIsOpenLocationModal] = useState(false);

  const updateLocation = ({name, lat, lng}) => {
    updateTimeSchedule('location', idx, {name, lat, lng});
  }

  return (
    <div className="input-time-schedule">
      <div className="schedule-header">
        <input 
          type="text" 
          placeholder="제목을 입력하세요" 
          value={newSchedule.title}
          className="schedule-title-input"
          onChange={(event) => updateTimeSchedule('title', idx, event.target.value)}
        />
      </div>

      <div className="schedule-controls">
        <div className="time-input-container">
          <TimeIcon className="input-icon"/>
          <input 
            type="time" 
            className="time-input"
            defaultValue={newSchedule?.time || "12:00"}
            onChange={(event) => updateTimeSchedule('time', idx, event.target.value)}
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
        value={newSchedule?.contents || ''}
        onChange={(event) =>updateTimeSchedule('contents', idx, event.target.value)}
      />

     <div className="media-input-container">
        <input 
          type="file"
          accept="image/*"
          className="media-file-input"
          id="media-input"
        />
        {/* <label htmlFor="media-input" className="media-input-label">
          <VuesaxLinearGallery className="media-icon" />
          이미지 추가
        </label> */}
      </div>
      {
        isOpenLocationModal ? 
        <Modal><SearchLocation selectedPlace={newSchedule.location} setPlaceInfo={updateLocation} setIsOpenModal={setIsOpenLocationModal}/></Modal> : ""
      }
    </div>
  );
};

export default TimeScheduleForm;