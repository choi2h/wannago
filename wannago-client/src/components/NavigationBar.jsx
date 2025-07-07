import { Search } from '../assets/icons/search';
import '../assets/css/navigationbar.css';
import { useLocation } from 'react-router';
import { useEffect, useRef, useState } from 'react';
import SearchOverlay from './SearchOverlay';

const menus = [
    {
        name: '최신순',
        link: '/posts/recent'
    },
    {
        name: '랭킹순',
        link: '/posts/rank'
    },
        {
        name: 'Q&A',
        link: '/qnas'
    },
    {
        name: '북마크',
        link: '/posts/bookmark'
    }
]

const profileMenus = [
    {
        name: '내가 쓴글',
        // my를 userId로 바꿔서 사용
        link: '/my/post'
    },
    {
        name: '마이페이지',
        link: '/mypage'
    },
    {
        name: '로그아웃',
        link: '/logout'
    }
]

function NavigationBar() {
    const location = useLocation();
    const [isProfileMenuOpen, setIsProfileMenuOpen] = useState(false);
    const profileMenuRef = useRef(null);
    const [isLogin, setIsLogin] = useState(!!localStorage.getItem('accessToken'));
    const [isSearchOpen, setIsSearchOpen] = useState(false);

    useEffect(() => {
        const handleStorageChange = () => {
            setIsLogin(!!localStorage.getItem('accessToken'));
        };

        window.addEventListener('storage', handleStorageChange);
        return () => {
            window.removeEventListener('storage', handleStorageChange);
        };
    }, []);
    
    // 프로필 메뉴 토글
    const toggleProfileMenu = () => {
        setIsProfileMenuOpen(!isProfileMenuOpen);
    };

    const toggleSearch = () => {
        setIsSearchOpen(true);
    };

    const closeSearch = () => {
        setIsSearchOpen(false);
    };

    // 외부 클릭 시 메뉴 닫기
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (profileMenuRef.current && !profileMenuRef.current.contains(event.target)) {
                setIsProfileMenuOpen(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    return (
        <nav className="navigation-bar">
            <div className="nav-container">
                {/* 로고 섹션 */}
                <div className="logo-section">
                    <img
                        className="logo-icon"
                        alt="갈래말래 로고"
                        src="https://c.animaapp.com/mcgerl86CUPwHM/img/group.png"
                    />
                    <div className="logo-text">갈래말래</div>
                </div>

                {/* 네비게이션 메뉴 */}
                <div className="nav-menu">
                    { menus.map((menu, idx) => 
                        <a key={idx} href={menu.link} className={`nav-item ${menu.link === location.pathname ? "active" : ""}`}>{menu.name}</a>)}
                </div>

                {/* 우측 액션 섹션 */}
                <div className="action-section">
                    <button className="search-button" aria-label="검색" onClick={toggleSearch}>
                        <Search className="search-icon" />
                    </button>

                    <SearchOverlay 
                        isOpen={isSearchOpen} 
                        onClose={closeSearch} 
                    />
                    
                    {
                        isLogin ?
                            <button className="write-button">
                                {
                                    location.pathname === '/qnas' ?
                                    <a className="write-text" href="/qna/write">질문 작성</a> :
                                    <a className="write-text" href="/post/write">게시글 작성</a>
                                }
                            </button>
                         : 
                            <button className="write-button">
                                <a className="write-text" href="/login">로그인</a>
                            </button>
                    }

                    {
                        isLogin ?
                            <span className="profile-dropdown" ref={profileMenuRef}>
                                <button 
                                    className="profile-button" 
                                    aria-label="프로필"
                                    onClick={toggleProfileMenu}
                                >
                                <img
                                        className="profile-image"
                                        alt="프로필"
                                        src="https://c.animaapp.com/mcgerl86CUPwHM/img/ellipse-1.png"
                                    />
                                </button>

                                {/* 드롭다운 메뉴 */}
                                {isProfileMenuOpen && (
                                    <div className="profile-menu">
                                        {profileMenus.map((menu, idx) => (
                                            <a 
                                                key={idx} 
                                                href={menu.link} 
                                                className="profile-menu-item"
                                                onClick={() => setIsProfileMenuOpen(false)}
                                            >
                                                {menu.name}
                                            </a>
                                        ))}
                                    </div>
                                )}
                            </span>
                        : 
                            ""
                    }
                </div>
            </div>
        </nav>
    );
}

export default NavigationBar;