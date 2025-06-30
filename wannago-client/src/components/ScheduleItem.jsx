import { LocationOn4 } from "../assets/icons/LocationOn4";
import '../assets/css/view-schedule.css';

function ViewSchedule ({day, items}) {
    return (
        <div className="schedule">
             <div className="schedule-day">{day}일차</div>
             {items.map((item, idx) => <ScheduleItem key={idx} item={item}/>)}
        </div>
    )
}

function ScheduleItem({item}) {
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
                        <div className="location-name">{item.location}</div>
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

export default ViewSchedule;