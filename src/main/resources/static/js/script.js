
const timer = document.getElementById("timer");
let seconds = parseInt(localStorage.getItem("seconds")) || 0;
let interval;

function startTimer() {
    interval = setInterval(() => {
        seconds++;
        timer.textContent = seconds;
        localStorage.setItem("seconds", seconds);
    }, 1000);
}

function stopTimer() {
    clearInterval(interval);
}

function resetTimer() {
    clearInterval(interval);
    seconds = 0;
    timer.textContent = seconds;
    localStorage.removeItem("started");
    localStorage.setItem("seconds", seconds);
}

document.addEventListener("DOMContentLoaded", function () {
    if (localStorage.getItem("started")) {
        startTimer();
    }

    timer.textContent = seconds;

    document.getElementById("startBtn").addEventListener("click", function () {
        localStorage.setItem("started", true);
        startTimer();
    });

    document.getElementById("stopBtn").addEventListener("click", stopTimer);
    document.getElementById("resetBtn").addEventListener("click", resetTimer);
    document.getElementById("resetBtns").addEventListener("click", resetTimer);
});






