import { useEffect } from 'react';

function Logout() {
    useEffect(() => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');

        window.location.href = '/';
    }, []);

    return null;
}

export default Logout;