import DefaultLayout from '../layouts/DefatulLayout';
import PostItem from '../components/PostItem';
import '../assets/css/qna-list.css';
import { useParams } from 'react-router';

const posts = [
  {
    title : "여행 계획 세우기 좋은 앱 추천해주세요!",
    createdDate : "2025.06.26",
    author : "김여행",
    contents : "안녕하세요! 이번 여름휴가 때 제주도로 여행을 가려고 하는데, 여행 계획 세우기 좋은 앱이나 웹사이트가 있을까요? 숙소 예약부터 맛집, 관광지 정보까지 한 번에 볼 수 있으면 좋겠어요. 추천 부탁드립니다!",
    tags : ["여행", "휴가", "제주도"],
    likeCount : 5
  },
  {
    title : "제주도 동쪽 맛집 추천 부탁드려요!",
    createdDate : "2025.06.26",
    author : "박미식",
    contents : "제주도 동쪽으로 여행 가는데, 현지인 추천 맛집이 궁금해요! 특히 해산물이나 흑돼지 맛집 위주로 알려주시면 감사하겠습니다. 웨이팅이 길어도 맛있으면 괜찮아요!",
    tags : ["여행", "동쪽", "맛집"],
    likeCount : 5
  },
  {
    title : "제주도에서 스쿠버 다이빙 체험할 수 있는 곳 추천!",
    createdDate : "2025.06.26",
    author : "최액티",
    contents : "스쿠버 다이빙을 한 번도 해본 적 없는데, 제주도에서 초보자도 쉽게 체험할 수 있는 곳이 있을까요? 장비 대여나 강습 포함된 패키지로 추천해주시면 좋겠어요. 바다 풍경이 예쁜 곳이면 더 좋고요!",
    tags : ["액티비티", "스쿠버다이빙", "제주도"],
    likeCount : 5
  },
  {
    title : "제주도 서쪽 가볼 만한 숨은 명소 알려주세요!",
    createdDate : "2025.06.26",
    author : "이지도",
    isAccepted : true,
    contents : "제주도 서쪽으로 여행 가는데, 너무 유명한 곳 말고 현지인만 아는 숨은 명소나 예쁜 카페, 조용한 해변 같은 곳이 있을까요? 사진 찍기 좋은 곳이면 더 환영입니다!",
    tags : ["여행", "휴가", "서쪽"],
    likeCount : 5
  }
];

function PostListPAge(){
  const { tab } = useParams();

  const getPageTitle = () => {
    if(tab === 'recent') {
        return "최신순";
    } else if (tab === 'rank') {
        return '랭킹순';
    } else if (tab === 'bookmark') {
      return '북마크';
    }
  }

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">{getPageTitle()}</h1>
    
        <section className="qna-list-section">
          { posts.map((post, idx) => <PostItem key={idx} post={post}/>)} {/* QnaListFrame -> PostListFrame으로 변경 */}
        </section>
        
        <nav className="pagination"> 
          <ul className="pagination-list">
            <li className="pagination-page selected"><a href="#">1</a></li>
            <li className="pagination-page"><a href="#">2</a></li>
            <li className="pagination-page"><a href="#">3</a></li>
            <li className="pagination-gap"><span>...</span></li>
            <li className="pagination-page"><a href="#">67</a></li>
            <li className="pagination-page"><a href="#">68</a></li>
          </ul>
        </nav>
      </div>
    </DefaultLayout>
  );
};

export default PostListPAge;