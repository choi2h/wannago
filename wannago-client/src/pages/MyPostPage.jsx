import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';
import DefaultLayout from '../layouts/DefatulLayout';
import PostItem from '../components/PostItem';
import QnaItem from '../components/QnaItem';
import { fetchMyPosts, fetchMyQnas } from '../api/mypage'; // API import 경로는 확인해주세요
import '../assets/css/qna-list.css';

// 한 페이지에 보여줄 항목 수
const ITEMS_PER_PAGE = 10;
// 페이지네이션 그룹에 표시할 페이지 수
const PAGE_GROUP_SIZE = 5;

function MyPostPage() {
    const navigate = useNavigate();
    const { tab = 'post' } = useParams(); // URL에 탭 정보가 없으면 'post'를 기본값으로
    const [searchParams, setSearchParams] = useSearchParams();

    // --- 상태 관리 ---
    const [list, setList] = useState([]); // 게시글 또는 질문 목록
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    const loginId = localStorage.getItem("loginId");

    // --- 데이터 로딩 및 라우팅 로직 ---

    useEffect(() => {
        if (!loginId) {
            alert("로그인이 필요합니다.");
            navigate("/login");
        }
    }, [loginId, navigate]);

    useEffect(() => {
        const pageParam = parseInt(searchParams.get('page') || '1', 10);
        setCurrentPage(pageParam);
    }, [searchParams]);

    useEffect(() => {
        if (!loginId) return;

        const loadData = async () => {
            setIsLoading(true);
            setError(null);
            const pageToFetch = currentPage - 1; // API는 0-based 페이지

            try {
                const fetcher = tab === 'post' ? fetchMyPosts : fetchMyQnas;
                // [수정] API 호출 시 page와 size 인자를 모두 전달
                const responseData = await fetcher(loginId, pageToFetch, ITEMS_PER_PAGE);

                if (responseData && typeof responseData === 'object') {
                    setList(responseData);
                    // setTotalPages(responseData.totalPages || 1);
                } else {
                    // 비정상 응답 처리
                    setList([]);
                    setTotalPages(1);
                }
            } catch (err) {
                console.error(`❌ 마이페이지 ${tab} 데이터 로딩 실패:`, err);
                setError("데이터를 불러오는 데 실패했습니다.");
            } finally {
                setIsLoading(false);
            }
        };

        loadData();
    }, [tab, loginId, currentPage, navigate]);

    // --- 렌더링 함수 ---

    const handlePageClick = (pageNumber) => {
        setSearchParams({ page: pageNumber.toString() });
    };

    const renderPagination = () => {
        if (totalPages <= 1) return null;

        const pageNumbers = [];
        const startPage = Math.floor((currentPage - 1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE + 1;
        const endPage = Math.min(startPage + PAGE_GROUP_SIZE - 1, totalPages);

        for (let i = startPage; i <= endPage; i++) {
            pageNumbers.push(i);
        }

        return (
            <nav className="pagination">
                <ul className="pagination-list">
                    {currentPage > 1 && (
                        <li><a href="#" onClick={(e) => { e.preventDefault(); handlePageClick(currentPage - 1); }}>이전</a></li>
                    )}
                    {pageNumbers.map(number => (
                        <li key={number} className={number === currentPage ? "selected" : ""}>
                            <a href="#" onClick={(e) => { e.preventDefault(); handlePageClick(number); }}>{number}</a>
                        </li>
                    ))}
                    {currentPage < totalPages && (
                        <li><a href="#" onClick={(e) => { e.preventDefault(); handlePageClick(currentPage + 1); }}>다음</a></li>
                    )}
                </ul>
            </nav>
        );
    };

    const renderContent = () => {
        if (isLoading) return <p className="loading-message">데이터를 불러오는 중입니다...</p>;
        if (error) return <p className="error-message">{error}</p>;
        console.log(list);
        if (list.length === 0) return <p className="no-data-message">{tab === 'post' ? '작성한 게시글이 없습니다.' : '작성한 질문이 없습니다.'}</p>;

        return (
            <section className="qna-list-section">
                {tab === 'post'
                    ? list.map(post => <PostItem key={post.postId} post={post} />) // [수정] 고유 ID로 key 설정
                    : list.map(qna => <QnaItem key={qna.askId} qna={qna} onClick={(id) => navigate(`/qna/${id}`)}/>)         // [수정] 고유 ID로 key 설정
                }
            </section>
        );
    };

    return (
        <DefaultLayout>
            <div className="qna-page-container">
                <h1 className="page-main-title">내가 쓴 글</h1>
                <nav className="category-menu-bar">
                    {[
                        { name: "나의 여행", tab: "post" },
                        { name: "나의 질문", tab: "qna" }
                    ].map(category => (
                        <div key={category.tab} className={`menu-item ${tab === category.tab ? "selected" : ""}`}>
                            {/* [수정] navigate 함수를 사용하여 페이지 새로고침 방지 */}
                            <a href="#" onClick={(e) => {
                                e.preventDefault();
                                navigate(`/mypage/${category.tab}?page=1`);
                            }}>{category.name}</a>
                        </div>
                    ))}
                </nav>
                {renderContent()}
                {renderPagination()}
            </div>
        </DefaultLayout>
    );
}

export default MyPostPage;