import { useEffect, useState } from "react";
import { BookmarkFilled } from "../assets/icons/BookmarkFilled";
import DefaultLayout from '../layouts/DefatulLayout';
import Input from '../components/Input';
import Comment from '../components/Comment';
import ScheduleItem from '../components/ScheduleItem';
import Map from "../components/Map";
import Tag from "../components/Tag";
import '../assets/css/post-detail.css';
import { selectPostById } from '../service/post-service';
import { useParams } from "react-router";


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

// const tags = [
//   '태그1', '태그2', '태그3'
// ]

function PostDetailPage() {
  const [isBookmarked, setIsBookmarked] = useState(false);
  const { id } = useParams();
  const [post, setPost] = useState(null); 
  useEffect(() => {
      console.log("Post!!");
      const fetchPost = async () => {
      try {
        const selectPost = await selectPostById(id); // 비동기 호출
        setPost(selectPost);
        console.log(`post setting!!!! ${post}`);
      } catch (error) {
        console.error("게시글 불러오기 실패:", error);
      }
    };

    fetchPost(); // 함수 실행
  }, [id]);

  const handleBookmarkClick = () => {
    setIsBookmarked(!isBookmarked);
  };

  if(!post) {
    return <DefaultLayout><div> 게시글을 로딩중입니다. </div></DefaultLayout>
  }

  return (
    <DefaultLayout>
    <div className="post-detail">
        <div className="title">
          <div className="simple-delicious">{post?.title}</div>

          <div className="frame-2">
            {
              post.tags?.map((tag,idx) => <Tag key={idx} type='view' text={tag} onClick={() => console.log('click heart icon!!!!')}/>)
            }
          </div>
        </div>

        <div className="author">
          <div style={{display: "flex", flexDirection: 'row', alignItems: 'center'}}>
            <div className="text-wrapper-4">{post.author}</div>
            <div className="text-wrapper-5">{post.createdAt}</div>
          </div>
          
        <div className="frame-4">
          <div 
            className={`bookmark-circle-wrapper ${post.statusInfo.bookmared ? 'bookmarked' : ''}`}
            onClick={handleBookmarkClick}
          >
            <BookmarkFilled className="bookmark-filled" isActive={post.statusInfo.bookmared}/>
          </div>
          <div className="stats">
            <div className="heart-button">
              <img
                className="heart-icon"
                alt="Heart icon"
                src="https://c.animaapp.com/mccxjumpIKwo6s/img/free-icon-like-6924834-1.png"
              />
              <span className="heart-count">{post.statusInfo.likeCount}</span>
            </div>
          </div>
        </div>
        </div>

        <div className="edit-delete-section">
          <div className="text-wrapper-2">수정 | 삭제</div>
        </div>
        
        <Map/>

        <p className="contents" style={{whiteSpace: 'pre-line'}}>
          {post.contents}
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