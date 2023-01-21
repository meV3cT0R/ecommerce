var star = document.getElementsByClassName("pre-star");
var value = parseInt(document.getElementById("rating").textContent);
console.log(value);
rate(star, value - 1);


var postStar = document.getElementsByClassName("post-star");
postStar[0].addEventListener("click", () => {
  rate(postStar, 0);
});
postStar[1].addEventListener("click", () => {
  rate(postStar, 1);
});
postStar[2].addEventListener("click", () => {
  rate(postStar, 2);
});
postStar[3].addEventListener("click", () => {
  rate(postStar, 3);
});
postStar[4].addEventListener("click", () => {
  rate(postStar, 4);
});


document.getElementById("rate").addEventListener("click", () => {
  var count = 0;
  for (var i = 0; i < postStar.length; i++) {
    if (postStar[i].classList.contains("text-warning")) {
      count++;
    }
  }

  document.getElementById("userRating").value = count;
  document.getElementById("ratingForm").submit();
});


function rate(star, index) {
  for (var i = 0; i < star.length; i++) {
    star[i].classList.remove("text-warning");
  } star
  for (var i = 0; i <= index; i++) {
    star[i].classList.add("text-warning");
  }
}