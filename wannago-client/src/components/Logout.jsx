import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Logout() {
    const navigate = useNavigate();

    useEffect(() => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');

        navigate('/');
    }, [navigate]);

    return null;
}

export default Logout;