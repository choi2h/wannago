import '../assets/css/qna-list.css';
import DefaultLayout from '../layouts/DefatulLayout';
import QnaItem from '../components/QnaItem';
import PostItem from '../components/PostItem';
import { useParams, useNavigate } from 'react-router';
import { useEffect, useState } from 'react';
import { fetchMyPosts, fetchMyQnas } from '../api/mypage';

function MyPostPage() {
  const navigate = useNavigate();
  const loginId = localStorage.getItem("loginId"); // ✅ 로컬스토리지에서 로그인 ID 확인
  const { tab } = useParams(); // tab 값: post 또는 qna

  const [postList, setPostList] = useState([]);
  const [qnaList, setQnaList] = useState([]);

  // 로그인 안 했으면 로그인 페이지로 이동
  useEffect(() => {
    if (!loginId) {
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }
  }, [loginId]);

  // 탭 변경 시 게시글 또는 질문 목록 불러오기
  useEffect(() => {
    const loadData = async () => {
      if (!loginId) return;

      try {
        if (tab === 'post') {
  const posts = await fetchMyPosts(loginId); // ✅ 수정!
  setPostList(Array.isArray(posts) ? posts : []);
} else if (tab === 'qna') {
  const qnas = await fetchMyQnas(loginId); // ✅ 수정!
  setQnaList(Array.isArray(qnas) ? qnas : []);
}
      } catch (err) {
        console.error("마이페이지 데이터 로딩 실패:", err);
      }
    };

    loadData();
  }, [tab, loginId]);

  const categories = [
    { name: "나의 여행", tab: "post" },
    { name: "나의 질문", tab: "qna" }
  ];

  const selectedCategory = categories.find(category => category.tab === tab)?.name;

  const getList = () => {
    if (tab === 'post') {
      return (
        <section className="qna-list-section">
          {postList.length > 0 ? (
            postList.map((post, idx) => (
              <PostItem key={idx} post={post} />
            ))
          ) : (
            <p>게시글이 없습니다.</p>
          )}
        </section>
      );
    } else if (tab === 'qna') {
      return (
        <section className="qna-list-section">
          {qnaList.length > 0 ? (
            qnaList.map((qna, idx) => (
              <QnaItem key={idx} qna={qna} />
            ))
          ) : (
            <p>질문이 없습니다.</p>
          )}
        </section>
      );
    }
    return null;
  };

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">내가 쓴 글</h1>

        <nav className="category-menu-bar">
          {categories.map((category) => (
            <div 
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
}

export default MyPostPage;
