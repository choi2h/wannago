import TimeScheduleForm from './TimeScheduleForm';
import Button from './Button';
import '../assets/css/input-schedule.css';

function DailyScheduleForm({newSchedule, setNewSchedule, idx}){

  const addTimeSchedule = () => {
    console.log("add Time schedule!!");
    const timeSchedules = [...newSchedule.timeSchedules, {
        title : '',
        time : '',
        location : {
          name: '',
          lat: 0,
          lng: 0
        },
        contents : '',
    }]

    const modifiedSchedule = {...newSchedule, timeSchedules};
    setNewSchedule(idx, modifiedSchedule);
  }

  const updateTimeSchedule = (type, modifyIdx, value) => {
    const preTimeSchedules = newSchedule.timeSchedules;
    let modifiedTimeSchedules = preTimeSchedules;
    if (type === 'title') {
      modifiedTimeSchedules = preTimeSchedules.map((ts, idx) => idx === modifyIdx ? {...ts, title : value} : ts);
    } else if (type === 'location') {
      console.log(value);
      modifiedTimeSchedules = preTimeSchedules.map((ts, idx) => idx === modifyIdx ? 
        {
          ...ts, 
          locationName : value.name,
          lat : value.lat,
          lng : value.lng
        } : ts);
    } else if (type === 'contents') {
      modifiedTimeSchedules = preTimeSchedules.map((ts, idx) => idx === modifyIdx ? {...ts, contents : value} : ts);
    } else if (type === 'time') {
      modifiedTimeSchedules = preTimeSchedules.map((ts, idx) => idx === modifyIdx ? {...ts, time : value} : ts);
    } else {
      return;
    }

    const modifiedSchedule = {...newSchedule, timeSchedules: modifiedTimeSchedules};
    setNewSchedule(idx, modifiedSchedule);
  }

  return (
    <div className="input-schedule">
      <div className="daily-schedule-header">
        <div className="day-label">{idx+1} 일차</div>
        <Button type={'mini'} text={'+ 일정 추가하기'} onClick={addTimeSchedule}/>
      </div>
       { 
        newSchedule.timeSchedules.map((ts, idx) => 
          <TimeScheduleForm key={idx} newSchedule={ts} updateTimeSchedule={updateTimeSchedule} idx={idx}/>
        )
      }
      
    </div>
  );
};

export default DailyScheduleForm;