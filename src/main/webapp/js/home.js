function drawPopulation() {
  htmx.ajax("GET", `${ctx}/population`, {
    target: "#population",
    swap: "innerHTML",
  });
}

function nextTick() {
  const xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = () => {
    if (xhttp.readyState === 4 && xhttp.status === 200) return;
    drawPopulation();
  };
  xhttp.open("PUT", `${ctx}/population`);
  xhttp.send();
}

document.addEventListener("DOMContentLoaded", () => {
  const autoPlay = document.querySelector("#auto-play");

  let auto = null;
  function onAutoPlay() {
    if (auto) {
      autoPlay.removeAttribute("on");
      clearInterval(auto);
      auto = null;
      return;
    }

    autoPlay.toggleAttribute("on", true);
    auto = setInterval(() => nextTick(), 250);
  }

  autoPlay.addEventListener("click", onAutoPlay);

  document.querySelectorAll("#configuration label").forEach((label) => {
    const input = label.querySelector("input[type=range]");
    if (!input) return;
    const span = label.querySelector("span");

    input.addEventListener("change", (e) => {
      span.innerText = e.target.value;
    });
  });
});
