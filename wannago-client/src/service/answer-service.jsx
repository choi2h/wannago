import axios from "axios";
import api from '../utils/axios';

const QNA_API = `${import.meta.env.VITE_API_SERVER_ADDRESS}/qna`;

const getLoginId = () => localStorage.getItem('loginId');

// 1. 답변 목록 조회
const selectAnswersByQnaId = async (id) => {
    return await api.get(`/qna/${id}/answer`)
    .then((response) => {
        console.log(response.status);
        console.log(response.data);

        return response.data;
    })
    .catch((err) => {
        console.error('❌ 답변 목록 조회 중 오류:', err);
        throw err;
    });
};

// 2. 새 답변 등록
const inputNewAnswer = async (id, answerData) => {
    console.log('Add answer!!!');
    console.log(JSON.stringify(answerData));

    const dataToSend = {
        ...answerData,
        loginId: answerData.loginId || getLoginId(),
    };

    return await api.post(`/qna/${id}/answer`, dataToSend)
    .then((response) => {
        console.log("Success to add new answer!!");
        console.log(response.status);

        return response.data;
    })
    .catch((err) => {
        console.error('❌ 답변 등록 중 오류:', err);
        throw err;
    });
};

// 3. 답변 수정
const updateAnswer = async (id, answerId, answerData) => {
    const dataToSend = {
        ...answerData,
        loginId: answerData.loginId || getLoginId(),
    };

    return await api.put(`/qna/${id}/answer/${answerId}`, dataToSend)
    .then((response) => {
        console.log("Success to update answer!!");
        console.log(response.status);

        return response.data;
    })
    .catch((err) => {
        console.error('❌ 답변 수정 중 오류:', err);
        throw err;
    });
};

// 4. 답변 삭제
const deleteAnswer = async (id, answerId) => {
    const loginId = getLoginId();

    return await api.delete(`/qna/${id}/answer/${answerId}`, {
        data: { loginId }
    })
    .then((response) => {
        console.log("Success to delete answer!!");
        console.log(response.status);

        return response.data || {};
    })
    .catch((err) => {
        console.error('❌ 답변 삭제 중 오류:', err);
        throw err;
    });
};

// 5. 답변 채택
const acceptAnswer = async (id, answerId) => {
    const loginId = getLoginId();

    return await api.post(`/qna/${id}/answer/${answerId}`, {
        loginId,
    })
    .then((response) => {
        console.log("Success to accept answer!!");
        console.log(response.status);

        return response.data;
    })
    .catch((err) => {
        console.error('❌ 답변 채택 중 오류:', err);
        throw err;
    });
};

export { selectAnswersByQnaId, inputNewAnswer, updateAnswer, deleteAnswer, acceptAnswer };