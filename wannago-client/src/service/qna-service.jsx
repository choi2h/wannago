import { HttpStatusCode } from 'axios';
import api from '../utils/axios';

const getQnaList = async (category) => {
    return api.get(`/qnas?category=${category}`)
    .then((response) => {
        console.log("success to requeset for get qna list.", response.data.asks);
        return response.data.asks;
    });
};

const inputQna = async (qna) => {
    return api.post('/qna', {...qna, category: qna.category.api})
        .then((response) => {
            console.log(response);
            return response;
        }).catch((error) => {
            console.log('질문 작성을 실패했습니다.' + error);
        });
}

const getQna = async (id) => {
    return api.get(`/qna/${id}`)
        .then((response) => {
            console.log(`Get qna ${id}`, response)
            return response;
        });
}

const updateQna = async (id, qna) => {
     return api.put(`/qna/${id}`, {...qna, category: qna.category.api})
        .then((response) => {
            return response;
        });
}

const deleteQna = async (id) => {
    return api.delete(`/qna/${id}`);
}

export {getQnaList, getQna, inputQna, updateQna, deleteQna};