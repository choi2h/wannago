import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AskViewer from '../components/AskViewer';
import AnswerItem from '../components/AnswerItem';
import Input from '../components/Input';
import DefaultLayout from '../layouts/DefatulLayout';

import {
  inputNewAnswer,
  selectAnswersByQnaId,
  updateAnswer,
  deleteAnswer,
  acceptAnswer
} from '../service/answer-service';

import QNA_CATEGORY from '../utils/QnaCategory';
import '../assets/css/qna-detail.css';
import { deleteQna, getQna } from '../service/qna-service';
import { HttpStatusCode } from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;

const getQnaDetail = async (id) => {
  const response = await getQna(id);
  if (response.status !== HttpStatusCode.Ok) throw new Error('질문을 불러오는데 실패했습니다.');
  return response.data;
};

function QnaDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [ask, setAsk] = useState();
  const [answers, setAnswers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);

  // 로그인 상태 확인
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const loginId = localStorage.getItem('loginId');

    if (token && loginId) {
      setCurrentUser({
        name: loginId,
        id: loginId,
        loginId: loginId
      });
    } else {
      setCurrentUser(null);
    }
  }, []);

  // 데이터 로딩
  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const [askData, answersData] = await Promise.all([
          getQnaDetail(id),
          selectAnswersByQnaId(id)
        ]);
        setAsk(askData);
        setAnswers(sortAnswers(answersData));
      } catch (err) {
        console.error('데이터 로딩 중 오류:', err);
        setError(err.message || '데이터를 불러오는데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      loadData();
    }
  }, [id]);
 // ✅ ✅ ✅ 여기 아래에 추가하세요!
  useEffect(() => {
    if (ask && currentUser) {
      console.log('🔎 ask:', ask);
      console.log('🔎 currentUser:', currentUser);
      console.log('🔎 비교값:',
        currentUser?.loginId,
        ask?.loginId,
        currentUser?.loginId === ask?.loginId
      );
    }
  }, [ask, currentUser]);
  // 답변 정렬 (채택된 답변이 먼저 오도록)
  const sortAnswers = (answers) => {
    return [...answers].sort((a, b) => {
      const aIsAccepted = a.accepted === true;
      const bIsAccepted = b.accepted === true;
      if (aIsAccepted && !bIsAccepted) return -1;
      if (!aIsAccepted && bIsAccepted) return 1;
      return new Date(b.createdDate) - new Date(a.createdDate);
    });
  };

  // 답변 채택
  const handleAcceptAnswer = async (answerId) => {
    if (!window.confirm('이 답변을 채택하시겠습니까?')) return;
    try {
      await acceptAnswer(id, answerId);
      const updatedAnswers = answers.map(answer => ({
        ...answer,
        accepted: answer.answerId === answerId
      }));
      setAnswers(sortAnswers(updatedAnswers));
      alert('답변이 채택되었습니다.');
    } catch (err) {
      console.error(err);
      alert('답변 채택 중 오류가 발생했습니다.');
    }
  };

  // 새 답변 등록
  const handleSubmitAnswer = async (contents) => {
    if (!currentUser) {
      alert('로그인이 필요합니다.');
      navigate('/login');
      return;
    }
    if (!contents.trim()) {
      alert('답변 내용을 입력해주세요.');
      return;
    }
    try {
      setIsSubmitting(true);


      const answerData = {
        contents: contents.trim(),
        author: currentUser.loginId,
        id: id,
        loginId: currentUser.loginId // loginId 추가
      };

      const newAnswer = await inputNewAnswer(id, answerData);

      // 새 답변을 리스트에 추가
      const addedAnswer = {
        ...newAnswer,
        accepted: false,
        author: currentUser.loginId,
        loginId: currentUser.loginId,
        createdDate: new Date().toISOString()
      };

      setAnswers(sortAnswers([...answers, addedAnswer]));
      alert('답변이 등록되었습니다.');
    } catch (err) {
      console.error(err);
      alert('답변 등록 중 오류가 발생했습니다.');
    } finally {
      setIsSubmitting(false);
    }
  };

  // 답변 삭제
  const handleDeleteAnswer = async (answerId) => {
    if (!window.confirm('정말 삭제하시겠습니까?')) return;
    try {
      await deleteAnswer(id, answerId);
      setAnswers(answers.filter(a => a.answerId !== answerId));
      alert('삭제되었습니다.');
    } catch (err) {
      console.error(err);
      alert('답변 삭제 중 오류 발생');
    }
  };

  // 답변 수정
  const handleEditAnswer = async (answerId, newContents) => {
    if (!newContents.trim()) {
      alert('답변 내용을 입력해주세요.');
      return;
    }
    try {
      // answer-service.js의 updateAnswer 함수에 맞춘 데이터 구조
      const answerData = {
        contents: newContents.trim(),
        answerId: answerId,
        loginId: currentUser.loginId // loginId 추가
      };

      await updateAnswer(id, answerId, answerData);

      setAnswers(sortAnswers(answers.map(a =>
        a.answerId === answerId ? { ...a, contents: newContents.trim() } : a
      )));
      alert('답변이 수정되었습니다.');
    } catch (err) {
      console.error(err);
      alert('답변 수정 중 오류 발생');
    }
  };

  // 권한 확인
  const isQuestionAuthor = currentUser && ask && (
    currentUser.loginId === ask.author ||
    currentUser.loginId === ask.loginId ||
    currentUser.id === ask.authorId
  );

  const hasAcceptedAnswer = answers.some(a => a.accepted === true);

  // 로딩 상태
  if (loading) {
    return (
      <DefaultLayout>
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <div className="loading-text">로딩 중...</div>
        </div>
      </DefaultLayout>
    );
  }

  // 에러 상태
  if (error) {
    return (
      <DefaultLayout>
        <div className="error-container">
          <div className="error-message">{error}</div>
          <button onClick={() => window.location.reload()}>새로고침</button>
        </div>
      </DefaultLayout>
    );
  }

  // 질문이 없는 경우
  if (!ask) {
    return (
      <DefaultLayout>
        <div className="error-container">
          <div className="error-message">질문을 찾을 수 없습니다.</div>
        </div>
      </DefaultLayout>
    );
  }

  const handleEdit = () => {
    navigate(`/qna/edit/${id}`, {state: {ask : {...ask, category: getCategory(ask.category)}}});
  }

  const handleDelete = () => {
    if(localStorage.getItem('loginId') !== ask.author) {
      console.log('삭제 권한이 없습니다.');
      return;
    }

    const confirmDelete = window.confirm("정말 이 질문을 삭제하시겠습니까?");
    if(!confirmDelete) return;

    deleteQna(id).then((response) => {
        console.log(response);
        if(response.status === HttpStatusCode.Ok) {
            navigate("/qnas");
        } else {
            alert("질문 삭제에 실패했습니다.");
        }
    });
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
          <div className="answers-count">
            {answers.length}개 답변
            {hasAcceptedAnswer && <span className="accepted-indicator">(채택 있음)</span>}
          </div>

          <div className="answers-list">
            {answers.length > 0 ? (
              answers.map((answer, index) => (
                <AnswerItem
                  key={answer.answerId}
                  answer={answer}
                  isQuestionAuthor={isQuestionAuthor}
                  hasAcceptedAnswer={hasAcceptedAnswer}
                  onAccept={handleAcceptAnswer}
                  onDelete={handleDeleteAnswer}
                  onEdit={handleEditAnswer}
                  currentUser={currentUser}
                  isFirstAccepted={index === 0 && answer.accepted === true}
                />
              ))
            ) : (
              <div className="no-answers">아직 답변이 없습니다.</div>
            )}
          </div>

          <div className="reply-section">
            <div className="reply-title">
              답변 작성하기
              {currentUser && (
                <span className="user-info">({currentUser.loginId})</span>
              )}
            </div>

            {currentUser ? (
              <Input
                typeText="답변"
                onSubmit={handleSubmitAnswer}
                disabled={isSubmitting}
                placeholder="답변을 입력해주세요"
              />
            ) : (
              <div className="login-required">
                <p>로그인 후 답변을 작성할 수 있습니다.</p>
                <button onClick={() => navigate('/login')}>로그인</button>
              </div>
            )}
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}

export default QnaDetailPage;