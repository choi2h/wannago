import { useEffect, useState } from "react";
import '../assets/css/search_location.css';
import Button from "./Button";

function SearchLocation({setPlaceInfo, setIsOpenModal}) {
    const [ps, setPs] = useState();
    const [map, setMap] = useState();
    // const [infowindow, setInfowindow] = useState();
    const [markers, setMarkers] = useState([]);
    const [searchList, setSearchList] = useState([]);
    const [selectedPlace, setSelectedPlace] = useState();

    const addNewMarker = (marker) => {
        setMarkers((prev) => [marker, ...prev]);
    };

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            // 이미 로드되어 있으면 재로드하지 않음
            window.kakao.maps.load(() => { 
                const mapContainer = document.getElementById("search-map");
                const mapOption = {
                    center: new window.kakao.maps.LatLng(37.566826, 126.9786567),
                    level: 2
                };

                const mapInstance = new window.kakao.maps.Map(mapContainer, mapOption);
                setMap(mapInstance);
                setPs(new window.kakao.maps.services.Places());
             });

            
             return;
            // return () => {
            //     if (script.parentNode) {
            //         document.head.removeChild(script);
            //     }
            //     document.removeEventListener('keydown', handleKeyPress);
            // };
        }

        const script = document.createElement('script');
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAOMAP_API_KEY}&autoload=false&libraries=services`;
        script.async = true;
        document.head.appendChild(script);

        script.onload = () => {
            window.kakao.maps.load(() => {
                const mapContainer = document.getElementById("search-map");
                const mapOption = {
                    center: new window.kakao.maps.LatLng(37.566826, 126.9786567),
                    level: 2
                };

                const mapInstance = new window.kakao.maps.Map(mapContainer, mapOption);
                setMap(mapInstance);
                setPs(new window.kakao.maps.services.Places());
                // setInfowindow(new window.kakao.maps.InfoWindow({ zIndex: 1 }));
            });
        };

        // 키보드 이벤트 추가
        const handleKeyPress = (e) => {
            if (e.key === 'Enter' && document.activeElement.id === 'keyword') {
                searchPlaces();
            }
        };

        document.addEventListener('keydown', handleKeyPress);

        // return () => {
        //     if (script.parentNode) {
        //         document.head.removeChild(script);
        //     }
        //     document.removeEventListener('keydown', handleKeyPress);
        // };
        return;
    }, []);

    const searchPlaces = (e) => {
        console.log("Click search!!")
        if (e) e.preventDefault();
        const keyword = document.getElementById('keyword').value;
        if (!keyword.trim()) {
            alert('키워드를 입력해주세요!');
            return;
        }
        if (ps) {
            ps.keywordSearch(keyword, placesSearchCB);
        }
    };

    const placesSearchCB = (data, status, pagination) => {
        if (status === window.kakao.maps.services.Status.OK) {
            displayPlaces(data);
            displayPagination(pagination);
        } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
            alert('검색 결과가 존재하지 않습니다.');
        } else {
            alert('검색 결과 중 오류가 발생했습니다.');
        }
    };

    const selectPlace = (place) => {
        setSelectedPlace({
            name: place.place_name,
            lat: place.y,
            lng: place.x
        });
        
        // 선택된 장소로 지도 중심 이동
        const moveLatLon = new window.kakao.maps.LatLng(place.y, place.x);
        map.setCenter(moveLatLon);
        map.setLevel(2);
        
        console.log("선택된 장소:", place.place_name, place.y, place.x);
    };

    const displayPlaces = (places) => {
        const listEl = document.getElementById('placesList');
        const fragment = document.createDocumentFragment();
        const bounds = new window.kakao.maps.LatLngBounds();

        removeAllChildNods(listEl);
        removeMarker();

        for (let i = 0; i < places.length; i++) {
            const place = places[i];
            const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
            addMarker(placePosition, i);
            bounds.extend(placePosition);
            // fragment.appendChild(itemEl);
        }

        // listEl.appendChild(fragment);
        setSearchList([...places]);
        console.log(searchList);
        map.setBounds(bounds);
    };

    const addMarker = (position, idx) => {
        // const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png';
        // const imageSize = new window.kakao.maps.Size(36, 37);
        // const imgOptions = {
        //     spriteSize: new window.kakao.maps.Size(36, 691),
        //     spriteOrigin: new window.kakao.maps.Point(0, (idx * 46) + 10),
        //     offset: new window.kakao.maps.Point(13, 37)
        // };
        // const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
        const marker = new window.kakao.maps.Marker({
            position,
            // image: markerImage
        });
        marker.setMap(map);
        addNewMarker(marker);
        return marker;
    };

    const removeMarker = () => {
        markers.forEach(marker => marker.setMap(null));
        setMarkers([]);
    };

    const displayPagination = (pagination) => {
        const paginationEl = document.getElementById('pagination');
        const fragment = document.createDocumentFragment();
        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild(paginationEl.lastChild);
        }
        for (let i = 1; i <= pagination.last; i++) {
            const el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;
            if (i === pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (e) => {
                    e.preventDefault();
                    pagination.gotoPage(i);
                };
            }
            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    };

    const removeAllChildNods = () => {
        // while (el.hasChildNodes()) {
        //     el.removeChild(el.lastChild);
        // }

        setSearchList([]);
    };

    return (
        <div className="map-container">
            <div className="map_wrap">
                <div id="search-map" />
                <div id="menu_wrap">
                    <div className="option">
                        <div className="search-form">
                            <label htmlFor="keyword">키워드</label>
                            <input type="text" id="keyword" placeholder="장소를 검색하세요" />
                            <button onClick={searchPlaces} className="search-btn">검색</button>
                        </div>
                    </div>
                    <div className="results-container">
                        <ul id="placesList">
                            {
                                searchList.map((searchItem, idx) => <SearchPlaceItem key={idx} idx={idx} place={searchItem} onClick={() => {
                                    selectPlace(searchItem, idx);
                                    console.log("click place!!!");
                                }}/>)
                            }
                        </ul>
                        <div id="pagination"></div>
                    </div>
                     {/* <div id="search-map" /> */}
                </div>
            </div>

            <div className="action-buttons">
                <Button type={'positive'} text={'선택완료'} onClick={() => {
                    if (selectedPlace) {
                        console.log("선택 완료:", selectedPlace);
                        setPlaceInfo(selectedPlace);
                        setIsOpenModal(false)
                    } else {
                        alert("장소를 선택해주세요.");
                    }
                }}/>
            </div>
        </div>
    );
}


function SearchPlaceItem({idx, place, onClick}) {
    return (
        <li className="item" onClick={onClick}>
            <span className={`markerbg marker_${idx + 1}`}>{idx+1}</span>
            <div className="place_info">
                <h5>{place.place_name}</h5>
                <span className="address">{place.road_address_name || place.address_name}</span>
                {/* {place.phone ? <span className="tel">{place.phone}</span> : ''} */}
            </div>
        </li>
    );
}
export default SearchLocation;