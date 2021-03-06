const carouselSlide = document.querySelector('.carousel-slide');
const carouselImages = document.querySelectorAll('.carousel-slide img');

// button
const prevBtn = document.querySelector('#prevBtn');
const nextBtn = document.querySelector('#nextBtn');
// counter
let counter = 0;
const size = carouselImages[0].clientWidth;
carouselSlide.style.transform = 'translateX('+(-size*counter) + 'px)';
//console.log(carouselImages.length)
// button Listener
if(nextBtn){
    nextBtn.addEventListener('click',() => {
    if(counter >= carouselImages.length-1) return;
    carouselSlide.style.transition = 'transform 0.4s ease-in-out';
    counter++;
    carouselSlide.style.transform = 'translateX('+(-size*counter) + 'px)';

  });
}

if(prevBtn){
    prevBtn.addEventListener('click', () => {
    if(counter <= 0) return;
    carouselSlide.style.transition = 'transform 0.4s ease-in-out';
    counter--;
    carouselSlide.style.transform = 'translateX('+(-size*counter)+'px)';
  });
}
