import { useEffect, useState } from "react";
import { BookmarkFilled } from "../assets/icons/BookmarkFilled";
import DefaultLayout from '../layouts/DefatulLayout';
import InputComment from '../components/InputComment'; // InputComment 임포트
import Comment from '../components/Comment'; // Comment 임포트
import ScheduleItem from '../components/ScheduleItem';
import Map from "../components/Map";
import Tag from "../components/Tag";
import '../assets/css/post-detail.css';
import {
    selectPostById,
    togglePostLike,
    addPostBookmark,
    removePostBookmark,
    addComment,
    addReply,
    getAllCommentsWithReplies,
    updateComment,
    deleteComment
} from '../service/post-service';
import { useParams, useNavigate } from "react-router";

function PostDetailPage() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [post, setPost] = useState(null);
    // 북마크 관련 상태
    const [isBookmarked, setIsBookmarked] = useState(false);
    // 좋아요 관련 상태
    const [likeCount, setLikeCount] = useState(0);
    const [liked, setLiked] = useState(false);
    // 댓글
    const [comments, setComments] = useState([]);

    // 댓글 입력 필드의 상태 (InputComment로 전달)
    const [newCommentContent, setNewCommentContent] = useState('');

    useEffect(() => {
        console.log("Post!!");
        const fetchPost = async () => {
            try {
                // 게시글 정보 로드
                const postResult = await selectPostById(id);
                setPost(postResult);
                //좋아요, 북마크 정보 실시간으로 반영
                if (postResult.statusInfo) {
                    setLikeCount(postResult.statusInfo.likeCount);
                    setLiked(postResult.statusInfo.liked);
                    setIsBookmarked(postResult.statusInfo.bookmarked);
                }
                // 댓글 정보 로드
                const commentsResult = await getAllCommentsWithReplies(id);
                setComments(commentsResult);
            } catch (err) {
                console.error('게시글 및 댓글 정보 불러오기 실패:', err);
                console.error('🔐 서버 응답 상태:', err.response?.status);
                console.error('📦 응답 데이터:', err.response?.data);
                alert("게시글을 가져올 수 없습니다.");
                navigate('/');
            }
        };

        fetchPost(); // 함수 실행
    }, [id, navigate]);

    // 북마크 버튼 클릭 핸들러
    const handleBookmarkClick = async () => {
        try {
            if (isBookmarked) {
                // 현재 북마크 상태라면 -> 삭제 요청
                await removePostBookmark(id);
                setIsBookmarked(false); // 상태 업데이트
                alert("북마크가 해제되었습니다!");
            } else {
                // 현재 북마크 상태가 아니라면 -> 등록 요청
                const success = await addPostBookmark(id);
                if (success) {
                    setIsBookmarked(true); // 성공하면 북마크 상태를 true로 변경
                    alert("북마크가 등록되었습니다!");
                } else {
                    alert("북마크 등록에 실패했습니다.");
                }
            }
        } catch (error) {
            console.error("북마크 처리 중 오류 발생:", error);
            alert("북마크 처리 중 오류가 발생했습니다.");
        }
    };

    // 좋아요 버튼 클릭 핸들러
    const handleLikeClick = async () => {
        try {
            // 백엔드에 좋아요 토글 요청
            const newLikedStatus = await togglePostLike(id);
            // 토글된 좋아요 상태로 프론트엔드 상태 업데이트
            setLiked(newLikedStatus);

            // 좋아요 개수 즉시 반영 (백엔드 응답에 따라 1 증가/감소)
            if (newLikedStatus) {
                setLikeCount(prevCount => prevCount + 1);
            } else {
                setLikeCount(prevCount => prevCount - 1);
            }
        } catch (error) {
            console.error("좋아요 토글 실패:", error);
            alert("좋아요 처리 중 오류가 발생했습니다.");
        }
    };

    // 댓글 입력 필드의 내용 변경 핸들러
    const handleCommentInputChange = (e) => {
        setNewCommentContent(e.target.value);
    };

    // 댓글 작성 버튼 클릭 또는 Enter 키 입력 시 호출될 핸들러
    const handleAddComment = async () => {
        if (!newCommentContent.trim()) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }
        try {
            const commentData = {
                contents: newCommentContent.trim(),
            };
            const addedComment = await addComment(id, commentData);
            setComments(prevComments => [...prevComments, addedComment]); // 새 댓글 목록에 추가
            setNewCommentContent(''); // 입력 필드 초기화
        } catch (error) {
            console.error("댓글 작성 실패:", error);
            alert("댓글 작성에 실패했습니다. 로그인했는지 확인해 주세요.");
        }
    };

    // 댓글 입력 필드에서 Enter 키 눌렀을 때 제출 처리 (Shift+Enter는 줄바꿈)
    const handleCommentInputKeyDown = (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault(); // 기본 줄바꿈 동작 방지
            handleAddComment(); // 댓글 제출
        }
    };

    // 대댓글 작성 핸들러
    const handleAddReply = async (parentId, replyContent) => {
        if (!replyContent.trim()) {
            alert('대댓글 내용을 입력해주세요.');
            return;
        }
        try {
            console.log('handleAddReply 호출됨 - parentId:', parentId);
            console.log('handleAddReply 호출됨 - replyContent:', replyContent);
            const replyData = {
                contents: replyContent.trim(),
            };
            const addedReply = await addReply(id, parentId, replyData);

            // 대댓글이 추가된 CommentResponse를 받아와서 comments 상태 업데이트
            setComments(prevComments =>
                prevComments.map(comment =>
                    comment.id === parentId 
                        ? {
                            ...comment,
                            replies: comment.replies 
                                ? [...comment.replies, addedReply]
                                : [addedReply]
                        }
                        : comment
                )
            );
        } catch (error) {
            console.error("대댓글 작성 실패:", error);
            console.error('🔐 서버 응답 상태:', error.response?.status);
            console.error('📦 응답 데이터:', error.response?.data);
            alert("대댓글 작성에 실패했습니다. 로그인했는지 확인해 주세요.");
        }
    };

    // 댓글 수정 핸들러
    const handleUpdateComment = async (commentId, updatedContent) => {
        if (!updatedContent.trim()) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }
        try {
            const updatedCommentFromServer = await updateComment(id, commentId, { contents: updatedContent.trim() }); 
            
            setComments(prevComments =>
                prevComments.map(comment => {
                    // 1. 최상위 댓글인 경우 (parentId가 null)
                    if (comment.id === commentId && comment.parentId === null) {
                        // 서버 응답에서 받은 내용으로 업데이트하고, 기존 replies는 유지
                        return { ...comment, contents: updatedCommentFromServer.contents };
                    }
                    // 2. 대댓글인 경우 (replies 배열 안에서 찾기)
                    if (comment.replies) {
                        const updatedReplies = comment.replies.map(reply => {
                            if (reply.id === commentId) {
                                // 서버 응답에서 받은 내용으로 대댓글 업데이트
                                return { ...reply, contents: updatedCommentFromServer.contents };
                            }
                            return reply;
                        });
                        // replies 배열이 변경되었다면 새로운 객체로 반환
                        // 변경이 없더라도 새로운 배열을 생성하여 React의 불변성 원칙을 따름
                        if (updatedReplies.some((reply, index) => reply.contents !== comment.replies[index].contents)) {
                            return { ...comment, replies: updatedReplies };
                        }
                    }
                    return comment; // 변경 없는 댓글은 그대로 반환
                })
            );
            alert('댓글이 수정되었습니다.'); // 성공 메시지
        } catch (error) {
            console.error("댓글 수정 실패:", error);
            alert("댓글 수정에 실패했습니다. 권한이 없거나 로그인하지 않았을 수 있습니다.");
        }
    };


    // 댓글 삭제 핸들러
    const handleDeleteComment = async (commentId) => {
        if (window.confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            try {
                const success = await deleteComment(id, commentId); 
                if (success) {
                    setComments(prevComments => {
                        // 최상위 댓글 삭제 시 해당 댓글 필터링
                        const filteredTopLevel = prevComments.filter(comment => comment.id !== commentId);
                        
                        // 대댓글 삭제 시 부모 댓글 내에서 필터링
                        return filteredTopLevel.map(comment => {
                            if (comment.replies) {
                                const filteredReplies = comment.replies.filter(reply => reply.id !== commentId);
                                // 변경이 없으면 불필요한 렌더링 방지를 위해 원본 객체 반환
                                if (JSON.stringify(filteredReplies) !== JSON.stringify(comment.replies)) {
                                     return { ...comment, replies: filteredReplies };
                                }
                            }
                            return comment;
                        });
                    });
                    alert('댓글이 삭제되었습니다.');
                } else {
                    alert('댓글 삭제에 실패했습니다.');
                }
            } catch (error) {
                console.error("댓글 삭제 실패:", error);
                alert("댓글 삭제에 실패했습니다. 권한이 없거나 로그인하지 않았을 수 있습니다.");
            }
        }
    };

    const handleEdit = () => {
        navigate(`/post/edit/${id}`, { state: { post } });
    }

    const handleDelete = () => {
        console.log("삭제");
        // 여기에 게시글 삭제 로직 추가 (예: confirm 후 deletePost API 호출)
    }

    if (!post) {
        return <DefaultLayout><div> 게시글을 로딩중입니다. </div></DefaultLayout>
    }

    return (
        <DefaultLayout>
            <div className="post-detail">
                <div className="title">
                    <div className="simple-delicious">{post?.title}</div>

                    <div className="frame-2">
                        {
                            post.tags?.map((tag, idx) => <Tag key={idx} type='view' text={tag} onClick={() => console.log('click heart icon!!!!')} />)
                        }
                    </div>
                </div>

                <div className="author">
                    <div style={{ display: "flex", flexDirection: 'row', alignItems: 'center' }}>
                        <div className="text-wrapper-4">{post.author}</div>
                        <div className="text-wrapper-5">{post.createdAt}</div>
                    </div>

                    <div className="frame-4">
                        {/* ... (북마크 버튼) ... */}
                        <div
                            className={`bookmark-circle-wrapper ${isBookmarked ? 'bookmarked' : ''}`}
                            onClick={handleBookmarkClick}
                        >
                            <BookmarkFilled className="bookmark-filled" isActive={isBookmarked} />
                        </div>
                        {/* ... (좋아요 버튼) ... */}
                        <div className="stats">
                            <div className="heart-button" onClick={handleLikeClick}>
                                <img
                                    className="heart-icon"
                                    alt="Heart icon"
                                    src="https://c.animaapp.com/mccxjumpIKwo6s/img/free-icon-like-6924834-1.png"
                                />
                                <span className="heart-count">{likeCount}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="edit-delete-section">
                    <span className="text-wrapper-2 edit-btn" onClick={handleEdit}>수정</span>
                    <span className="text-wrapper-2">|</span>
                    <span className="text-wrapper-2 delete-btn" onClick={handleDelete}>삭제</span>
                </div>

                <Map />

                <p className="contents" style={{ whiteSpace: 'pre-line' }}>
                    {post.contents}
                </p>

                {
                    post.schedules.sort((a, b) => a.day[0] - b.day[0]).map((schedule, idx) => <ScheduleItem key={idx} day={schedule.day} times={schedule.timeSchedules} />)
                }
                <div className="text-wrapper-9">댓글</div>

                {/* 댓글 입력 필드: InputComment 사용 (상태와 로직을 PostDetailPage에서 관리) */}
                <InputComment
                    typeText="댓글"
                    value={newCommentContent} // 상태를 직접 연결
                    onChange={handleCommentInputChange} // 변경 핸들러 연결
                    onButtonClick={handleAddComment} // 버튼 클릭 핸들러 연결
                    onKeyDown={handleCommentInputKeyDown} // Enter 키 처리 핸들러 연결
                    disabled={!newCommentContent.trim()} // 버튼 비활성화 로직 연결
                />

                {/* 댓글 리스트 렌더링 */}
                {
                    comments.map((comment, idx) => (
                        <Comment
                            key={comment.id || idx} // comment.id 사용
                            comment={comment}
                            postId={id}
                            onAddReply={handleAddReply}
                            onUpdateComment={handleUpdateComment}
                            onDeleteComment={handleDeleteComment}
                        />
                    ))
                }
            </div>
        </DefaultLayout>
    );
};

export default PostDetailPage;