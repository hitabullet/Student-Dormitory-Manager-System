/*
function float() {
    const prompt = document.querySelector('.prompt');
    console.log(prompt.innerHTML)
    const ul = document.getElementById("ul2");
    ul.innerHTML = prompt.innerHTML;

    function marquee() {
        // 当滚动到滚动内容的末端时，就回到原位重新开始滚动
        if (prompt.scrollLeft >= ul.scrollWidth) {
            prompt.scrollLeft = 0;
        } else {
           prompt.scrollLeft++;
        }
    }
    // 设置一个定时器
    let s = setInterval(marquee, 50);
    // 鼠标悬浮滚动停止
    prompt.onmouseover = function() { clearInterval(s);  }
    prompt.onmouseout = function() { s = setInterval('marquee()', 50);}
}
export { float };
*/
