// ------------------------------------------------------
// Small optional enhancement (JavaScript is optional per
// the brief, but this adds a bit of interactivity):
//
// Fades each timeline card into view as the user scrolls
// down to it, using the IntersectionObserver API instead
// of a scroll event listener (better for performance).
// ------------------------------------------------------

document.addEventListener("DOMContentLoaded", () => {
  const cards = document.querySelectorAll(".timeline-card");

  // set the starting (hidden) state in JS so the page still
  // works fine even if JavaScript fails to load
  cards.forEach((card) => {
    card.style.opacity = "0";
    card.style.transform = "translateY(20px)";
    card.style.transition = "opacity 0.6s ease, transform 0.6s ease";
  });

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.style.opacity = "1";
          entry.target.style.transform = "translateY(0)";
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.2 }
  );

  cards.forEach((card) => observer.observe(card));
});