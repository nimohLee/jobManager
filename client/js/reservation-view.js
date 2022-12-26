'use strict';



let roomCount = 1;
let adultCount = 1;
let kidsCount = 1;
const rooms = document.querySelector('.rooms');
const adult = document.querySelector('.adults');
const kids = document.querySelector('.kids');

document.querySelector('.rooms-btn').addEventListener('click',(e) => {addCount(rooms)});
document.querySelector('.adult-btn').addEventListener('click',(e)=>{addCount(adult)});
document.querySelector('.kids-btn').addEventListener('click',(e)=>{addCount(kids)});


/**
 * 페이지 처음 불러왔을 때 예약날짜 기본값 넣는 용도
 */
window.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('#start').value = localStorage.getItem('startDate');
    document.querySelector('#end').value = localStorage.getItem('endDate');
});


/**
 * 매개변수 : 버튼(+,- div) 클릭시 변경 될 숫자 input
*/
function addCount(props){
  props.value = plusCount(props);
}

/**
 * 클릭하는 element에 따라 더해거나 빼야할 count를 1더한 후 리턴하는 함수
*/
function plusCount(param){
    if(param.className=="rooms")
        return ++roomCount;
    else if(param.className=="adults")
        return ++adultCount;
    else if(param.className=="kids")
        return ++kidsCount;
}

