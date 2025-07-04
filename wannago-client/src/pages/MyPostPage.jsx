import '../assets/css/qna-list.css';
import DefaultLayout from '../layouts/DefatulLayout';
import QnaItem from '../components/QnaItem';
import PostItem from '../components/PostItem';
import { useParams } from 'react-router';
import { useEffect, useState } from 'react';
import { fetchMyPosts, fetchMyQnas } from '../api/mypage'; // 새로 추가
import { useNavigate } from 'react-router-dom';

function MyPostPage(){
  const navigate = useNavigate();

  // ✅ 1. 로그인 여부 확인
  const loginId = localStorage.getItem("loginId");



  useEffect(() => {
    if (!loginId) {
      alert("로그인이 필요합니다.");
      navigate("/login");  // 로그인 안 했으면 로그인 페이지로 강제 이동
    }
  }, []);

  const { tab } = useParams();
  const [postList, setPostList] = useState([]);
  const [qnaList, setQnaList] = useState([]);

  useEffect(() => {
    const loadData = async () => {
      try {
        if(tab === 'post') {
          const posts = await fetchMyPosts(loginId);
          setPostList(posts);
        } else if(tab === 'qna') {
          const qnas = await fetchMyQnas(loginId);
          setQnaList(qnas);
        }
      } catch (err) {
        console.error("마이페이지 데이터 로딩 실패", err);
      }
    };
    loadData();
  }, [tab]);

  const categories = [
    { name: "나의 여행", tab: "post" },
    { name: "나의 질문", tab: "qna" }
  ];

  const selectedCategory = categories.find(category => category.tab === tab)?.name;

  const getList = () => {
  if(tab === 'post') {
    return (
      <section className="qna-list-section">
        {Array.isArray(postList) ? (
          postList.map((post, idx) => (
            <PostItem key={idx} post={post} />
          ))
        ) : (
          <p>게시글을 불러올 수 없습니다.</p>
        )}
      </section>
    );
  } else if(tab === 'qna') {
    return (
      <section className="qna-list-section">
        {Array.isArray(qnaList) ? (
          qnaList.map((qna, idx) => (
            <QnaItem key={idx} qna={qna} />
          ))
        ) : (
          <p>질문 목록을 불러올 수 없습니다.</p>
        )}
      </section>
    );
  }
  return null;
};

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">내가쓴글</h1>
        
        <nav className="category-menu-bar">
          {categories.map((category) => (
            <div 
              name={category.name}
              key={category.name} 
              className={`menu-item ${selectedCategory === category.name ? "selected" : ""}`}
            >
              <a href={`/my/${category.tab}`}>{category.name}</a>
            </div>
          ))}
        </nav>

        {getList()}

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

export default MyPostPage;
