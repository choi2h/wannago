import { useState } from "react";
import { BookmarkFilled } from "../assets/icons/BookmarkFilled";
import DefaultLayout from '../layouts/DefatulLayout';
import Input from '../components/Input';
import Comment from '../components/Comment';
import ScheduleItem from '../components/ScheduleItem';
import Tag from "../components/Tag";
import '../assets/css/post-detail.css';

const schedules = [
  {
    day: 1,
    items: [
      {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
       {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
       {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
    ]
  },
  {
    day: 2,
    items: [
      {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
       {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
       {
      time: '9시30분',
      location: '속초 고속터미널',
      title: '속초 고속터미널로 출발',
      contents: '아침일찍 만나서 속초 고속터미널로 갈거임~'
      },
    ]
  }
];

const comments = [
  {
    name: 'james',
    time : '2025.09.23 16:40',
    contents : '정말 맛있겠네요!!!',
    subComments : [
      {
        name: 'amy',
        time : '2025.09.23 16:45',
        contents : '정말 맛있겠네요!!!2222'
      }
    ]
  },
  {
    name: 'james2',
    time : '2025.09.23 16:40',
    contents : '정말 맛있겠네요!!!',
    subComments : []
  }
]

const tags = [
  '태그1', '태그2', '태그3'
]

function PostDetailPage() {
  const [isBookmarked, setIsBookmarked] = useState(false);

  const handleBookmarkClick = () => {
    setIsBookmarked(!isBookmarked);
  };

  return (
    <DefaultLayout>
    <div className="post-detail">
        <div className="title">
          <div className="simple-delicious">Simple Delicious Beef Tacos</div>

          <div className="frame-2">
            {
              tags.map(tag => <Tag type='view' text={tag} onClick={() => console.log('click heart icon!!!!')}/>)
            }
          </div>
        </div>

        <div className="author">
          <div style={{display: "flex", flexDirection: 'row', alignItems: 'center'}}>
            <div className="text-wrapper-4">사용자2</div>
            <div className="text-wrapper-5">2025.06.26</div>
          </div>
          
        <div className="frame-4">
          <div 
            className={`bookmark-circle-wrapper ${isBookmarked ? 'bookmarked' : ''}`}
            onClick={handleBookmarkClick}
          >
            <BookmarkFilled className="bookmark-filled" isActive={isBookmarked}/>
          </div>
          <div className="stats">
            <div className="heart-button">
              <img
                className="heart-icon"
                alt="Heart icon"
                src="https://c.animaapp.com/mccxjumpIKwo6s/img/free-icon-like-6924834-1.png"
              />
              <span className="heart-count">5</span>
            </div>
          </div>
        </div>
        </div>

        <div className="edit-delete-section">
          <div className="text-wrapper-2">수정 | 삭제</div>
        </div>

        <div className="map-wrapper">
          <img
            className="image"
            alt="Image"
            src="https://c.animaapp.com/mccxjumpIKwo6s/img/image-1.png"
          />
        </div>

        <p className="contents">
          드디어 만난 인생 순대!!! 지금까지 먹은 순대는 순대가 아니었구나 한 맛.
          순대를 주문하면 가자미식해와 무말랭이를 조금 주는데 같이 먹으니 천국.
          회냉면은 그럭저럭 괜찮았다. 근데 순대랑 같이 먹으면 조합 최고.
        </p>

        {
          schedules.map((schedule, idx) => <ScheduleItem key={idx} day={schedule.day} items={schedule.items}/>)
        }
        <div className="text-wrapper-9">댓글</div>
        {
          comments.map((comment, idx) => <Comment key={idx} comment={comment}/>)
        }
        <Input/>
      </div>
      </DefaultLayout>
  );
};

export default PostDetailPage;