import DefaultLayout from '../layouts/DefatulLayout';
import PostItem from '../components/PostItem';
import '../assets/css/qna-list.css';
import { useParams } from 'react-router';
import { useEffect, useState } from 'react';
import { selectPosts } from '../service/post-service';
import Pagination from '../components/Pagination';

function PostListPage(){
  const { tab } = useParams();
  const [pagePost, setPagePost] = useState();

  const getPageTitle = () => {
    if(tab === 'recent') {
        return "최신순";
    } else if (tab === 'rank') {
        return '랭킹순';
    } else if (tab === 'bookmark') {
      return '북마크';
    }
  }

  const getCriteria = () => {
    if(tab === 'recent') {
        return "createdDate";
    } else if (tab === 'rank') {
        return 'likeCount';
    }
  }

  const getPosts = async(pageNo) => {
    console.log("move-page ====> " + pageNo);
      try {
        const criteria = getCriteria();
        const pagePost = await selectPosts(pageNo, criteria);
        setPagePost(pagePost);
      } catch(error) {
        console.error("게시글 불러오기 실패:", error);
      }
    }

  useEffect(() => {
    getPosts(0);
  }, [tab])

  if(!pagePost) {
    return <DefaultLayout><div> 등록된 게시글이 없습니다. </div></DefaultLayout>
  }

  return (
    <DefaultLayout>
      <div className="qna-page-container">
        <h1 className="page-main-title">{getPageTitle()}</h1>
    
        <section className="qna-list-section">
          {/* fix: 옵셔널 체이닝 적용 */}
          { pagePost?.posts?.map((post, idx) => <PostItem key={idx} post={post} />) }
        </section>
        
        {/* <nav className="pagination"> 
          <ul className="pagination-list">
            {
              
            }
            <li className="pagination-page selected"><a href="#">1</a></li>
            <li className="pagination-page"><a href="#">2</a></li>
            <li className="pagination-page"><a href="#">3</a></li>
            <li className="pagination-gap"><span>...</span></li>
            <li className="pagination-page"><a href="#">67</a></li>
            <li className="pagination-page"><a href="#">68</a></li>
          </ul>
        </nav> */}
        <Pagination currentPage={pagePost.currentPage+1} totalPages={pagePost.totalPage} onPageChange={getPosts}/>
      </div>
    </DefaultLayout>
  );
};

export default PostListPage;