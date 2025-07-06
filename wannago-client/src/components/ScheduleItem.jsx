import { LocationOn4 } from "../assets/icons/LocationOn4";
import '../assets/css/view-schedule.css';

function ScheduleItem ({day, times}) {
    console.log(`day: ${day}`);
    return (
        <div className="schedule">
             <div className="schedule-day">{day}</div>
             {times.sort((a,b) => a.time.localeCompare(b.time)).map((item, idx) => <ViewSchedule key={idx} item={item}/>)}
        </div>
    )
}

function ViewSchedule({item}) {
    return (
        <div className="schedule-item">
            {/* 이미지 */}
            {/* <div className="group-3">
            <div className="frame-14" />

            <div className="frame-15" />

            <div className="frame-16" />
            </div> */}

           <div className="schedule-item">
                <div className="content-wrapper">
                    <div className="title">{item.title}</div>
                    <div className="location">
                        <LocationOn4 className="location-on" />
                        <div className="location-name">{item.locationName}</div>
                    </div>
                    <p className="contentx">{item.contents}</p>
                </div>
                <div className="time-wrapper">
                    <div className="time">{item.time}</div>
                </div>
            </div>
        </div>
    )
}

export default ScheduleItem;