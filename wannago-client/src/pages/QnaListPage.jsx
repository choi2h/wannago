import { useState } from 'react';
import '../assets/css/qna-list.css';
import DefaultLayout from '../layouts/DefatulLayout';
import QnaItem from '../components/QnaItem';

const Qnas = [
  {
    title : "여행 계획 세우기 좋은 앱 추천해주세요!",
    createdDate : "2025.06.26",
    author : "김여행",
    isAccepted : false,
    contents : "안녕하세요! 이번 여름휴가 때 제주도로 여행을 가려고 하는데, 여행 계획 세우기 좋은 앱이나 웹사이트가 있을까요? 숙소 예약부터 맛집, 관광지 정보까지 한 번에 볼 수 있으면 좋겠어요. 추천 부탁드립니다!"
  },
  {
    title : "제주도 동쪽 맛집 추천 부탁드려요!",
    createdDate : "2025.06.26",
    author : "박미식",
    isAccepted : false,
    contents : "제주도 동쪽으로 여행 가는데, 현지인 추천 맛집이 궁금해요! 특히 해산물이나 흑돼지 맛집 위주로 알려주시면 감사하겠습니다. 웨이팅이 길어도 맛있으면 괜찮아요!"
  },
  {
    title : "제주도에서 스쿠버 다이빙 체험할 수 있는 곳 추천!",
    createdDate : "2025.06.26",
    author : "최액티",
    isAccepted : true,
    contents : "스쿠버 다이빙을 한 번도 해본 적 없는데, 제주도에서 초보자도 쉽게 체험할 수 있는 곳이 있을까요? 장비 대여나 강습 포함된 패키지로 추천해주시면 좋겠어요. 바다 풍경이 예쁜 곳이면 더 좋고요!"
  },
  {
    title : "제주도 서쪽 가볼 만한 숨은 명소 알려주세요!",
    createdDate : "2025.06.26",
    author : "이지도",
    isAccepted : true,
    contents : "제주도 서쪽으로 여행 가는데, 너무 유명한 곳 말고 현지인만 아는 숨은 명소나 예쁜 카페, 조용한 해변 같은 곳이 있을까요? 사진 찍기 좋은 곳이면 더 환영입니다!"
  }
];

function QnaListPage(){
  // 실제 앱에서는 카테고리 선택 상태를 관리하는 state가 필요합니다.
  const categories = ["맛집", "액티비티", "기타", "명소"];
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);

  const updateSelectedCategory = (category) => {
    setSelectedCategory(category);
  }

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">Q&amp;A</h1>
        
        <nav className="category-menu-bar">
          {categories.map((category) => (
            <div 
              name={category}
              key={category} 
              className={`menu-item ${selectedCategory === category ? "selected" : ""}`}
              onClick={() => updateSelectedCategory(category)}
            >
              <a href="#">{category}</a> {/* 실제 앱에서는 라우팅 링크가 될 수 있습니다. */}
            </div>
          ))}
        </nav>

        <section className="qna-list-section">
          { Qnas.map((qna, idx) => <QnaItem key={idx} qna={qna}/>)}
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

export default QnaListPage;