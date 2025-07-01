import AskViewer from '../components/AskViewer';
import Input from '../components/Input';
import AnswerItem from '../components/AnswerItem';
import DefaultLayout from '../layouts/DefatulLayout';
import '../assets/css/qna-detail.css';

const answers = [
  {
    author: "작성자1",
    createdTime: "2025.06.26",
    isAccepted: false,
    contents: "안녕하세요 진짜 맛있는 해물 짬뽕 찾고 있습니다.\n" +
              "레드오크호텔 앞에있다고 알겠는데, 계속해서 어디인지 기억내게, 해쪽오\n" +
              "기보니 없었던 해물 덮밥음이고 어디가 맞았는지 모르겠어요\n" +
              "혹시 내일 정말 중에서 해물음은 어떤곳인지???\n" +
              "해일이랑 기부실 해물 먹고, 중에서 주진 해주세요\n" +
              "꼭 내일 정말 부탁드리겠습니다"
  },
  {
    author: "작성자2",
    createdTime: "2025.06.26",
    isAccepted: false,
    contents: "안녕하세요 진짜 맛있는 해물 짬뽕 찾고 있습니다.\n" +
              "레드오크호텔 앞에있다고 알겠는데, 계속해서 어디인지 기억내게, 해쪽오\n" +
              "기보니 없었던 해물 덮밥음이고 어디가 맞았는지 모르겠어요\n" +
              "혹시 내일 정말 중에서 해물음은 어떤곳인지???\n" +
              "해일이랑 기부실 해물 먹고, 중에서 주진 해주세요\n" +
              "꼭 내일 정말 부탁드리겠습니다"
  },
  {
    author: "작성자3",
    createdTime: "2025.06.26",
    isAccepted: false,
    contents: "안녕하세요 진짜 맛있는 해물 짬뽕 찾고 있습니다.\n" +
              "레드오크호텔 앞에있다고 알겠는데, 계속해서 어디인지 기억내게, 해쪽오\n" +
              "기보니 없었던 해물 덮밥음이고 어디가 맞았는지 모르겠어요\n" +
              "혹시 내일 정말 중에서 해물음은 어떤곳인지???\n" +
              "해일이랑 기부실 해물 먹고, 중에서 주진 해주세요\n" +
              "꼭 내일 정말 부탁드리겠습니다"
  }
]

const ask = {
  category : "맛집",
  title : "애월 맛집 괜찮은곳이요",
  author: "작성자",
  createdTime: "2025.06.26",
  contents: "안녕하세요 진짜 맛있는 애월 맛집 찾고 있습니다.\n" +
        "핸드폰으로 막 알아보고 있는데.. 계속해서 어디로 가야할까..하네요\n" +
        "가보지 않았던 애월 맛집들이라 어디가 맛있는지 모르겠어요\n" +
        "혹시 애월 맛집 중에서 괜찮은곳 있을까요??\n" +
        "왠만하면 가보신 애월 맛집 중에서 추천 해주세요\n" +
        "꼭 애월 맛집 부탁드리겠습니다"
}

function QnaDetailPage() {
  return (
    <DefaultLayout>
      <div className="qna-detail">
        <AskViewer ask={ask} />
        <div className="answers-section">
          <div className="answers-count">{answers.length}개 답변</div>
          <div className="answers-list">
            {answers.map((ans, idx) => <AnswerItem key={idx} answer={ans} />)}
          </div>
          <div className="reply-section">
            <div className="reply-title">답변 작성하기</div>
            <Input typeText="답변" onSubmit={() => {console.log("답변 작성 버튼 클릭!!!!!!")}}/>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default QnaDetailPage;