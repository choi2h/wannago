import '../assets/css/pagination.css';

function Pagination ({ totalPages, currentPage, onPageChange }) {
  const renderPageNumbers = () => {
    const pages = [];
    
    // 총 페이지가 5개 이하인 경우 모든 페이지 표시
    if (totalPages <= 5) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(
          <li key={i} className={`pagination-page ${i === currentPage ? 'selected' : ''}`}>
            <a href="#" onClick={(e) => { e.preventDefault(); onPageChange(i-1); }}>
              {i}
            </a>
          </li>
        );
      }
      return pages;
    }
    
    // 5개 초과인 경우 현재 페이지 기준 앞뒤 2개씩 표시
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, currentPage + 2);
    
    // 현재 페이지가 앞쪽에 있을 때
    if (currentPage <= 3) {
      startPage = 1;
      endPage = 5;
    }
    
    // 현재 페이지가 뒤쪽에 있을 때
    if (currentPage >= totalPages - 2) {
      startPage = totalPages - 4;
      endPage = totalPages;
    }
    
    // 페이지 번호 생성
    for (let i = startPage; i <= endPage; i++) {
      pages.push(
        <li key={i} className={`pagination-page ${i === currentPage ? 'selected' : ''}`}>
          <a href="#" onClick={(e) => { e.preventDefault(); onPageChange(i); }}>
            {i}
          </a>
        </li>
      );
    }
    
    return pages;
  }

  return (
    <nav className="pagination">
      <ul className="pagination-list">
        {/* 이전 버튼 */}
        <li className={`pagination-prev ${currentPage === 1 ? 'disabled' : ''}`}>
          <a 
            href="#" 
            onClick={(e) => { 
              e.preventDefault(); 
              if (currentPage > 1) onPageChange(currentPage - 1); 
            }}
          >
            ‹
          </a>
        </li>
        
        {/* 페이지 번호들 */}
        {renderPageNumbers()}
        
        {/* 다음 버튼 */}
        <li className={`pagination-next ${currentPage === totalPages ? 'disabled' : ''}`}>
          <a 
            href="#" 
            onClick={(e) => { 
              e.preventDefault(); 
              if (currentPage < totalPages) onPageChange(currentPage + 1); 
            }}
          >
            ›
          </a>
        </li>
      </ul>
    </nav>
  )
}

export default Pagination;