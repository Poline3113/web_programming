"use strict";

let selectedX = '';
let selectedY = [];
let selectedR = [];

const errorContainer = document.getElementById('error');
const errorText = document.getElementById('errorText');

function showError(message) {
    hideError();
    errorText.textContent = message;
    errorContainer.classList.add('visible');
}

function hideError() {
    errorContainer.classList.remove('visible');
}

function setActive(buttonsClass) {
    document.querySelectorAll('.' + buttonsClass).forEach(btn => {
        btn.addEventListener('click', function() {
            if (this.classList.contains("active")) {
                this.classList.remove("active");
            } else {
                this.classList.add("active");
            }
        });
    });
}

setActive('y-btn');
setActive('r-btn');

function setX(value) {
    selectedX = value.trim();
}

function getSelectedValues() {

    selectedY = [];
    document.querySelectorAll('.y-btn.active').forEach(btn => {
        selectedY.push(parseFloat(btn.value));
    });
    
    selectedR = [];
    document.querySelectorAll('.r-btn.active').forEach(btn => {
        selectedR.push(parseFloat(btn.value));
    });
    
    return {
        x: selectedX,
        y: selectedY,
        r: selectedR
    };
}

function validateData() {
    const values = getSelectedValues();
    selectedX = values.x;
    if (!selectedX || isNaN(parseFloat(selectedX))) {
        return { isValid: false, message: 'X must be a num' };
    }
    const xNum = parseFloat(selectedX);
    if (xNum < -5 || xNum > 5) {
        return { isValid: false, message: 'X must be in [-5;5]'};
    }
    if (selectedY.length == 0) {
        return { isValid: false, message: 'Select at least one Y value' };
    }
    const validYValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
    selectedY = values.y;
    for (let y of selectedY) {
        const yNum = parseFloat(y);
        if (isNaN(yNum) || !validYValues.includes(yNum)) {
            return { isValid: false, message: 'Y must be one of these: -3, -2, -1, 0, 1, 2, 3, 4, 5' };
        }
    }
    selectedR = values.r;
    if (selectedR.length == 0) {
        return { isValid: false, message: 'Select at least one R value' };
    }
    const validRValues = [1, 1.5, 2, 2.5, 3];
    for (let r of selectedR) {
        const rNum = parseFloat(r);
        if (isNaN(rNum) || !validRValues.includes(rNum)) {
            return { isValid: false, message: 'R must be one of these: 1, 1.5, 2, 2.5, 3' };
        }
    }
    return { isValid: true, message: '' };
}

document.getElementById('result_btn')
.addEventListener('click', async function(event) {
    event.preventDefault();

    setX(document.getElementById('x-data').value.replace(',', '.').trim());
    const validation = validateData();
    
    if (!validation.isValid) {
        showError(validation.message);
        return;
    } else {
        hideError();
    }
    
    const data = getSelectedValues();
    for (const yvalue of data.y) {
        for (const rvalue of data.r) {
            try {
                const params = new URLSearchParams({
                    x: data.x,
                    y: yvalue,
                    r: rvalue
                });

                const response = await fetch(`/fcgi-bin/app.jar?${params.toString()}`);
                const result = await response.json();

                seeResult({x: data.x, y: yvalue, r: rvalue }, result);
                saveToMemory({x: data.x, y: yvalue, r: rvalue }, result);
            } catch (err) {
                seeResult(
                {x: data.x, y: yvalue, r: rvalue},
                    {
                        good: false,
                        start: new Date().toLocaleString(),
                        time: "-",
                        reason: "server is offline"
                    }
                );
            }

        }
    }
});

function seeResult(data, result) {
    let table = document.getElementById('results-table');

    const row = table.insertRow();

    row.insertCell().textContent = data.x;
    row.insertCell().textContent = data.y;
    row.insertCell().textContent = data.r;

    const resultCell = row.insertCell();
    if (result.good) {
        resultCell.textContent = result.result ? "Hit" : "miss";
        resultCell.style.color = result.result ? "green" : "red";
        row.insertCell().textContent = result.time;
        row.insertCell().textContent = result.start;
    } else {
        resultCell.textContent = result.reason;
        resultCell.style.color = "yellow";
        row.insertCell().textContent = result.time;
        row.insertCell().textContent = result.start;
    }
}

const canvas = document.getElementById('graph');
const ctx = canvas.getContext('2d');

let R = 2;

function drawGraph() {
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = Math.min(width, height) * 0.4;

    ctx.clearRect(0, 0, width, height);

    ctx.fillStyle = '#3B82F6';

    ctx.beginPath();
    ctx.rect(centerX + (-R / R) * scale, centerY + (0 / R) * scale, scale, -scale);
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + (-R/2 / 4*R) * scale, centerY);
    ctx.lineTo(centerX, centerY + (-R / -R) * scale);
    ctx.moveTo(centerX, centerY);
    ctx.arc(centerX, centerY, scale, 0, Math.PI / 2, false);
    ctx.closePath();
    ctx.fill();

    ctx.strokeStyle = 'white';
    ctx.lineWidth = 2;

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.stroke();

    ctx.fillStyle = 'white';
    ctx.font = '12px Arial';
    ctx.textAlign = 'center';

    ctx.fillText('R', centerX + scale, centerY + 15);
    ctx.fillText('-R', centerX - scale, centerY + 15);
    ctx.fillText('R/2', centerX + scale/2, centerY + 15);
    ctx.fillText('-R/2', centerX - scale/2, centerY + 15);

    ctx.fillText('R', centerX + 15, centerY - scale);
    ctx.fillText('-R', centerX + 15, centerY + scale);
    ctx.fillText('R/2', centerX + 15, centerY - scale/2);
    ctx.fillText('-R/2', centerX + 15, centerY + scale/2);

    ctx.fillText('x', width - 10, centerY + 15);
    ctx.fillText('y', centerX + 15, 10);
}

function resizeCanvas() {
    const container = canvas.parentElement;
    const size = Math.min(container.clientWidth, container.clientHeight);
    canvas.width = size;
    canvas.height = size;
    drawGraph();
}

function saveToMemory(data, res) {
    const save = JSON.parse(localStorage.getItem("results") || "[]");

    const newElem = {
        x: data.x,
        y: data.y,
        r: data.r,
        result: res.result,
        time: res.time,
        start: res.start,
        reason: res.reason,
        good: res.good
    };

    save.unshift(newElem);

    if (save.length > 100) {
        save.length = 100;
    }

    localStorage.setItem("results", JSON.stringify(save));
}


function load() {
     const savedResults = JSON.parse(localStorage.getItem("results") || "[]");
     let table = document.getElementById('results-table');

     savedResults.forEach(item => {
            const data = {
                x: item.x,
                y: item.y,
                r: item.r
            };

            const res = {
                result: item.result,
                time: item.time,
                start: item.start,
                good: item.good,
                reason: item.reason
            };

            seeResult(data, res);
        });
}

load();

window.addEventListener('resize', resizeCanvas);
resizeCanvas();
