import { useEffect } from "react";
import '../assets/css/post-detail.css';

function Map () {
    useEffect(() => {
        const script = document.createElement('script');
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAOMAP_API_KEY}&autoload=false`;
        script.async = true;
        document.head.appendChild(script);

        console.log(document.head.lastChild);


        script.onload = () => {
            console.log("onLoad!!!");

            setTimeout(() => {
                if (window.kakao && window.kakao.maps) {
                    window.kakao.maps.load(() => {
                        const container = document.getElementById('map-wrapper');
                        const options = {
                            center: new window.kakao.maps.LatLng(33.450701, 126.570667),
                            level: 3
                        };

                        new window.kakao.maps.Map(container, options);
                    });
                }
            }, 100);
        }

        return () => {
            document.head.removeChild(script);
        };
     }, []);

  return (
    <div id="map-wrapper" style={{ width: '100%', height: '400px', margin: '10px 0'}}>
    </div>
  );
}

export default Map;