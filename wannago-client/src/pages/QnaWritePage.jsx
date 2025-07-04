import Button from '../components/Button';
import QnaForm from '../components/QnaForm';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/post-write.css';

function QnaWritePage () {
  return (
    <DefaultLayout>
        <div class="body">
          <QnaForm />
          <div className="bottom-button">
            <Button type={'negative'} text={'작성취소'}/>
            <Button type={'positive'} text={'작성완료'}/>
          </div>
        </div>
    </DefaultLayout>
  );
};

export default QnaWritePage;