import { useEffect } from "react";

function Map () {
    useEffect(() => {
        const script = document.createElement('script');
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAOMAP_API_KEY}&autoload=false`;
        script.async = true;
        document.head.appendChild(script);

        console.log(document.head.lastChild);


        script.onload = () => {
            console.log("onLoad!!!");

            window.kakao.maps.load(() => {
                const container = document.getElementById('map-wrapper'); // 지도를 표시할 div
                const options = {
                    center: new window.kakao.maps.LatLng(33.450701, 126.570667),
                    level: 3
                };

                // script로 호출하면 전역에 저장됨 => 전역을 사용하기 위해서는 window. 를 붙여서 호출
                new window.kakao.maps.Map(container, options);
            });
        }

        return () => {
            document.head.removeChild(script);
        };
  }, []);

  return (
    <div id="map-wrapper" style={{ width: '100%', height: '400px' }}>
          
    </div>
  );
}

export default Map;