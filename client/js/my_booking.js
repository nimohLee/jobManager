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