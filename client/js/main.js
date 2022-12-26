gsap.registerPlugin(ScrollTrigger); //gsap plugin 등록(ScrollTrigger)

if (window.matchMedia("(min-width: 768px)").matches) {
    gsap.to(".intro-text", {
        scrollTrigger: {
            trigger: ".intro-text",
            toggleActions: "play reverse play",

            end: "top center",
            // scrub : true,
        },
        xPercent: 140,
        pin: true,
        duration: 1.5,
    });
}

const img = document.querySelector(".bg");
const m = window.matchMedia("screen and (max-width: 768px)");
const reveal = () => {
    const reveals = document.querySelectorAll(".reveal");

    for (let i = 0; i < reveals.length; i++) {
        const windowHeight = window.innerHeight;
        const elementTop = reveals[i].getBoundingClientRect().bottom; // getBoundigClientRect() -
        elementVisible = 0;

        if (elementTop < windowHeight - elementVisible) {
            reveals[i].classList.add("active");
            img.classList.add("opacity");
        } else {
            reveals[i].classList.remove("active");
            img.classList.remove("opacity");
        }
    }
};

if (!m.matches) window.addEventListener("scroll", reveal);

const mapOptions = {
    center: new naver.maps.LatLng(35.14846063861386, 129.11349699731272),
    zoom: 20,
};
const map = new naver.maps.Map("map", {
    center: new naver.maps.LatLng(35.14846063861386, 129.11349699731272),
    zoom: 20,
});

const startDate = document.querySelector("#start");
const endDate = document.querySelector("#end");
const dateClass = new Date();
const year = dateClass.getFullYear();
const month =
    dateClass.getMonth() < 10
        ? `0${dateClass.getMonth() + 1}`
        : dateClass.getMonth() + 1;
const date =
    dateClass.getDate() < 10
        ? `0${dateClass.getDate() + 1}`
        : dateClass.getDate() + 1;

startDate.min = new Date().toISOString().substring(0, 10);

/**
 * startDate(datePicker)에 변경이 발생할 때마다 endDate(datePicker)의 최소 입력가능 날짜가 startDate+1로 설정됨
 */
startDate.addEventListener("change", (e) => {
    const changedEndDate = new Date(Date.parse(startDate.value));
    changedEndDate.setDate(changedEndDate.getDate() +1);
    endDate.min = changedEndDate.toISOString().substring(0,10); 
});


function toReservation() {
    localStorage.setItem("startDate", startDate.value);
    localStorage.setItem("endDate", endDate.value);
    location.href = "./reservation.html";
}

document.querySelector("#frm").addEventListener("submit", (e) => {
    e.preventDefault();
    toReservation();
});

const loginBtn = document.querySelector(".login-menu-btn");
const loginMenu = document.querySelector(".login-menu");

const add = () => {
    loginMenu.classList.add("blocked");
};
const remove = () => {
    loginMenu.classList.remove("blocked");
};

loginBtn.addEventListener("mouseover", add);
loginBtn.addEventListener("mouseout", remove);

const button = document.getElementById("go-reservation");
const section = document.getElementById("reservation-section");

button.addEventListener("click", () => {
    section.scrollIntoView({
        behavior: "smooth",
    });
});
