import NavigationBar from '../components/NavigationBar';

function DefaultLayout ({children}) {
    return (
        <div className="post-write">
            <NavigationBar />
            {children}
        </div>
    );
}

export default DefaultLayout;