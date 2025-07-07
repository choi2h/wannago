import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom'; // ❗ URL의 파라미터(id)를 가져오기 위해 import
import AskViewer from '../components/AskViewer';
import AnswerItem from '../components/AnswerItem';
import Input from '../components/Input';
import DefaultLayout from '../layouts/DefatulLayout';
import QNA_CATEGORY from '../utils/QnaCategory';
import '../assets/css/qna-detail.css';
import { getQna } from '../service/qna-service';

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


function QnaDetailPage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [ask, setAsk] = useState();

  useEffect(() => {
    const fetchAskDetail = async () => {
      const response = await getQna(id);
      console.log(response);
      setAsk(response);
    };

    fetchAskDetail();
  }, [id]); // id값이 바뀔 때마다 API를 다시 호출합니다.

  const handleEdit = () => {
    navigate(`/qna/edit/${id}`, {state: {ask : {...ask, category: getCategory(ask.category)}}});
  }

  const handleDelete = () => {
    console.log("삭제");
  }

   const getCategory = (category) => {
    let result = QNA_CATEGORY[0];
    QNA_CATEGORY.forEach((qnaCategory) => {
      console.log(qnaCategory.api.toUpperCase() + " " + category.toUpperCase(), "=>",  qnaCategory.api.toUpperCase() === category.toUpperCase());
      if(qnaCategory.api.toUpperCase() === category.toUpperCase()) result = qnaCategory;
    })

    return result;
  }

  if(!ask) {
    return (
      <DefaultLayout>
        <span>게시글을 불러오는 중입니다..</span>
      </DefaultLayout>
    )
  }

  return (
    <DefaultLayout>
      <div className="qna-detail">
  
        <AskViewer ask={ask} onClickEdit={handleEdit} onClickDelete={handleDelete}/>

        <div className="answers-section">
          <div className="answers-count">{answers.length}개 답변</div>
          <div className="answers-list">
            {answers.map((ans, idx) => <AnswerItem key={idx} answer={ans}/>)}
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