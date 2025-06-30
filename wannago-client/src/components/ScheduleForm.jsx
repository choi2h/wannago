import { VuesaxLinearGallery } from '../assets/icons/VuesaxLinearGallery';
import '../assets/css/input-schedule.css';

function ScheduleForm(){
  return (
    <div className="input-schedule">
      <div className="schedule-header">
        <div className="day-label">2 일차</div>
        
        <input 
          type="text" 
          placeholder="제목을 입력하세요" 
          className="schedule-title-input"
        />
      </div>

      <div className="schedule-controls">
        <div className="time-input-container">
          <svg className="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="12,6 12,12 16,14"/>
          </svg>
          <input 
            type="time" 
            className="time-input"
            defaultValue="12:00"
          />
        </div>
        
        <div className="location-input-container">
          <svg className="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
            <circle cx="12" cy="10" r="3"/>
          </svg>
          <input 
            type="text"
            placeholder="장소를 입력하세요"
            className="location-input"
          />
        </div>
      </div>

      <textarea 
        placeholder="내용을 입력하세요."
        className="schedule-content-input"
        rows="8"
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
    </div>
  );
};

export default ScheduleForm;