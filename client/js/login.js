
//   login menu hiding script



let loginBtn = document.querySelector('.login-menu-btn');
let loginMenu = document.querySelector('.login-menu');
let toLogin = document.querySelector('#sub-login-btn');

const add = () => {
  loginMenu.classList.add('blocked');
}
const remove = () => {
  loginMenu.classList.remove('blocked');
}

loginBtn.addEventListener('mouseover', add);
loginBtn.addEventListener('mouseout', remove);





//   login script

// id와 pw 입력되었는지 boolean
let idOK = false;
let pwOK = false;

function vaildateFormCheck() {

  let uID = document.querySelector("#userid");
  let idBtn = document.querySelector("#btn");
  let text = document.querySelector(".text-box");
  let uPW = document.querySelector("#passwd");

  let uidValue = uID.value.trim();
  let upwValue = uPW.value.trim();

  if (uidValue === null || uidValue === '') {
    errorCheck(uID, '아이디를 입력해주세요');
  } else {
    successCheck(uID);
    idOK = true;
  }
  if (uPW.value == null || uPW.value == '') {
    errorCheck(uPW, '비밀번호를 입력해주세요');
  } else {
    successCheck(uPW);
    pwOK = true;
  }


  if (idOK == true && pwOK == true) {
    location.href = "./main.html";
  }

}


function errorCheck(input, msg) {
  const formControl = input.parentElement;
  const small = formControl.querySelector("small");
  formControl.className = "form-control error";
  small.innerHTML = msg;
}

function successCheck(input) {
  const formControl = input.parentElement;
  formControl.className = "form-control success";
};


document.getElementById('form').addEventListener('submit', (e) => {

  e.preventDefault();
  vaildateFormCheck();
})