import DefaultLayout from '../layouts/DefatulLayout';
import PostForm from '../components/PostForm';
import DailyScheduleForm from '../components/DailyScheduleForm';
import Button from '../components/Button';
import '../assets/css/post-write.css';
import { useEffect, useState } from 'react';
import { modifyPost } from '../service/post-service';
import { useLocation, useNavigate } from 'react-router';

function PostEditPage () {
  const {state} = useLocation();
  const navigate = useNavigate();
  const [post, setPost] = useState();

  useEffect(() => {
    setPost(state.post);
    console.log(state.post);
  }, [state])

  const completeWirtePost = () => {
    console.log("작성 완료했대!!");
    const schedules = post.schedules.map((schedule, idx) => {return {...schedule, day: `${idx+1}일차`}});
    modifyPost({...post, schedules})
    .then(navigate(`/post/${post.id}`))
    .catch();
  }

  const updateSchedule = (preIdx, newSchedule) => {
    const schedules = post.schedules.map((schedule, idx) => idx === preIdx ? newSchedule : schedule);
    setPost((prePost) => {return {...prePost, schedules : [...schedules]}});
    console.log(post.schedules[preIdx]);
  }

  const updateTItle = (title) => {
    setPost((prePost) => {return {...prePost, title}});
  }

    const updateContents = (contents) => {
    setPost((prePost) => {return {...prePost, contents}});
  }

  const updateTags = (tags) => {
    setPost((prePost) =>  {return {...prePost, tags}});
  }

  const addSchedule = () => {
    setPost((prePost) => {
      return {
        ...prePost,
        schedules: [
          ...prePost.schedules,
          {
            day: '',
            timeSchedules : [
                {
                  title : '',
                  time : '',
                  contents : '',
                  locationName: '',
                  lat: 0,
                  lng: 0,
                }
            ]
          },
        ]      
      }
    });
    console.log(post);
  }

  if(!post) {
    return '게시글을 가져오는 중입니다.';
  }

  return (
    <DefaultLayout>
        <div className="body">
          <PostForm post={post} updateTitle={updateTItle} updateTags={updateTags} updateContents={updateContents}/>
          {
            post?.schedules.map((schedule, idx) => <DailyScheduleForm key={idx} newSchedule={schedule} setNewSchedule={updateSchedule} idx={idx}/>)
          }
          <div className="add-schedule">
            <Button type={'secondary'} text={'+ 일차 추가하기'} onClick={addSchedule}/>
          </div>
          <div className="bottom-button">
            <Button type={'negative'} text={'작성취소'} onClick={() => {navigate("/")}}/>
            <Button type={'positive'} text={'작성완료'} onClick={completeWirtePost}/>
          </div>
        </div>
    </DefaultLayout>
  );
};

export default PostEditPage;