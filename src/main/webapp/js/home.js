function onNextTick() {
  const xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = () => {
    if (xhttp.readyState !== 4 || xhttp.status !== 200) {
      return;
    }

    htmx.ajax("GET", `${ctx}/population`, {
      target: "#population",
      swap: "innerHTML",
    });
  };

  xhttp.open("PUT", `${ctx}/population`);
  xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

  xhttp.send();
}

document.addEventListener("DOMContentLoaded", () => {
  const nextTick = document.querySelector("#next-tick");
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
    auto = setInterval(() => onNextTick(), 250);
  }

  nextTick.addEventListener("click", onNextTick);
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
