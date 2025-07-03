import DefaultLayout from '../layouts/DefatulLayout';
import PostForm from '../components/PostForm';
import ScheduleForm from '../components/ScheduleForm';
import Button from '../components/Button';
import '../assets/css/post-write.css';
import { useState } from 'react';
import { inputNewPost } from '../service/post-service';
import { Navigate, useNavigate } from 'react-router';

function PostWritePage () {
  const navigate = useNavigate();
  const [newPost, setNewPost] = useState(
      {
        title : '',
        contents : '',
        tags : [],
        schedules : [
          {
                title : '',
                time : '',
                contents : '',
                locationName: '',
                lat: 0,
                lng: 0,
          },
        ]
      }
  );

  const completeWirtePost = () => {
    console.log("작성 완료했대!!");
    inputNewPost(newPost);
  }

  const updateSchedule = (preIdx, newSchedule) => {
    const schedules = newPost.schedules.map((schedule, idx) => idx === preIdx ? newSchedule : schedule);
    setNewPost((prePost) => {return {...prePost, schedules : [...schedules]}});
    console.log(newPost.schedules[preIdx]);
  }

  const updateTItle = (title) => {
    setNewPost((prePost) => {return {...prePost, title}});
  }

    const updateContents = (contents) => {
    setNewPost((prePost) => {return {...prePost, contents}});
  }

  const updateTags = (tags) => {
    setNewPost((prePost) =>  {return {...prePost, tags}});
  }

  const addSchedule = () => {
    setNewPost((prePost) => {
      return {
        ...prePost,
        schedules: [
          ...prePost.schedules,
          {
            title : '',
            time : '',
            location : {
              name: '',
              lat: 0,
              lng: 0
            },
            contents : '',
          }
        ]      
      }
    });
    console.log(newPost);
  }

  return (
    <DefaultLayout>
        <div className="body">
          <PostForm updateTitle={updateTItle} tags={newPost.tags} updateTags={updateTags} updateContents={updateContents}/>
          {
            newPost.schedules.map((schedule, idx) => <ScheduleForm key={idx} newSchedule={schedule} setNewSchedule={updateSchedule} idx={idx}/>)
          }
          <div className="add-schedule">
            <Button type={'secondary'} text={'+ 일정 추가하기'} onClick={addSchedule}/>
          </div>
          <div className="bottom-button">
            <Button type={'negative'} text={'작성취소'} onClick={() => {navigate("/")}}/>
            <Button type={'positive'} text={'작성완료'} onClick={completeWirtePost}/>
          </div>
        </div>
    </DefaultLayout>
  );
};

export default PostWritePage;