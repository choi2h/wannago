import DefaultLayout from '../layouts/DefatulLayout';
import PostForm from '../components/PostForm';
import ScheduleForm from '../components/ScheduleForm';
import Button from '../components/Button';
import '../assets/css/post-write.css';

function PostWritePage () {
  return (
    <DefaultLayout>
        <div class="body">
          <PostForm />
          <ScheduleForm />
          <div class="add-schedule">
            <Button type={'secondary'} text={'+ 일정 추가하기'}/>
          </div>
          <div className="bottom-button">
            <Button type={'negative'} text={'작성취소'}/>
            <Button type={'positive'} text={'작성완료'}/>
          </div>
        </div>
    </DefaultLayout>
  );
};

export default PostWritePage;