import { useEffect } from "react";

interface PropsInfo {
    x: string;
    y: string;
    location: string | undefined;
}

function NaverMap(props: PropsInfo) {
    useEffect(() => {
        const initMap = () => {
            const map = new naver.maps.Map("map", {
                center: new naver.maps.LatLng(+props.y, +props.x),
                zoom: 18,
            });
            new naver.maps.Marker({
                position: new naver.maps.LatLng(+props.y, +props.x),
                map: map,
            });
        };
        initMap();
    }, []);

    //지도 사이즈 관련 스타일
    const mapStyle = {
        width: "100%",
        height: "400px",
    };

    return <div id="map" style={mapStyle}></div>;
}

export default NaverMap;
