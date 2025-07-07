import { BrowserRouter, Routes, Route, Navigate } from 'react-router';
import PostListPage from './pages/PostListPage';
import LoginPage from './pages/LoginPage';
import PostDetailPage from './pages/PostDetailPage';
import PostWritePage from './pages/PostWritePage';
import PostEditPage from './pages/PostEditPage';
import QnaListPage from './pages/QnaListPage';
import QnaWritePage from './pages/QnaWritePage';
import QnaDetailPage from './pages/QnaDetailPage';
import MyPostPage from './pages/MyPostPage';
import SignupPage from './pages/SignupPage';
import MyPage from './pages/MyPage';
import MyPageEdit from './pages/MyPageEdit';
import Logout from './components/Logout';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/posts/recent" replace />}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/signup" element={<SignupPage/>}/>
        <Route path="/posts/:tab" element={<PostListPage/>}/>
        <Route path="/post/:id" element={<PostDetailPage/>}/>
        <Route path="/post/edit/:id" element={<PostEditPage/>}/>
        <Route path="/post/write" element={<PostWritePage/>}/>
        <Route path="/qnas" element={<QnaListPage/>}/>
        <Route path="/qna/write" element={<QnaWritePage/>}/>
        <Route path="/qnas/detail" element={<QnaDetailPage/>}/>
        <Route path="/my/:tab" element={<MyPostPage/>}/>
        <Route path="/mypage" element={<MyPage/>}/>
        <Route path="/mypage/edit" element={<MyPageEdit/>}/>
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
