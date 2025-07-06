import React from 'react';

const MyPostList = ({ posts }) => {
  if (!posts.length) return <p>작성하신 글이 없습니다.</p>;

  return (
    <ul>
      {posts.map(post => (
        <li key={post.postId} style={{ marginBottom: '1rem' }}>
          <h3>{post.title}</h3>
          <p>해시태그: {post.hashtags.join(', ')}</p>
          <p>공개 여부: {post.isPublic ? '공개' : '비공개'}</p>
          <p>좋아요 수: {post.likeCount}</p>
          <p>작성일: {post.createdDate}</p>
        </li>
      ))}
    </ul>
  );
};

export default MyPostList;
