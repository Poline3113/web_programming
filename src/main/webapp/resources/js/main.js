const canvas = document.getElementById('graph');
const ctx = canvas.getContext('2d');

let R = 2;

const errorElement = document.getElementById("error");
const input = document.getElementById("main:y");

input.addEventListener('input', function(event) {
    const y = parseFloat(event.target.value.trim().replace(/,/g, '.'));
    if (isNaN(y) || y < -3 || y > 5) {
        showError("Y must be in [-3, 5]");
    } else {
        hideError();
    }
});

function showError(message) {
    hideError();
    errorText.textContent = message;
    errorElement.classList.add('visible');
}

function hideError() {
    errorElement.classList.remove('visible');
}

function drawGraph() {
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = Math.min(width, height) * 0.4;

    ctx.clearRect(0, 0, width, height);

    ctx.fillStyle = '#3B82F6';

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.arc(centerX, centerY, scale, 0, -Math.PI / 2, true);
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + (-R / 4*R) * scale, centerY);
    ctx.lineTo(centerX, centerY + (R / -R / 2) * scale);
    ctx.lineTo(centerX, centerY + (R / -R) * scale);
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + (-R / -8*R) * scale, centerY);
    ctx.lineTo(centerX + (-R / -8*R) * scale, centerY + (-R / -R) * scale);
    ctx.lineTo(centerX, centerY + (-R / -R) * scale);
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

canvas.addEventListener('click', function(event) {
    const rect = canvas.getBoundingClientRect();
    const x = (event.clientX - rect.left);
    const y = (event.clientY - rect.top);

        const width = canvas.width;
        const height = canvas.height;
        const centerX = width / 2;
        const centerY = height / 2;
            const scale = Math.min(width, height) * 0.4;


    const data = document.getElementById("r_data").value;
    const graphX = (x - centerX) / scale;
    const graphY = (centerY - y) / scale;
    const R = parseFloat(data);
    document.getElementById("main:x").value = (graphX * R).toFixed(6);
    document.getElementById("main:y").value = (graphY * R).toFixed(6);
    document.getElementById('main:check').click();
});


function drawPoint(x, y, r, hit, centerX, centerY, scale) {
    const px = centerX + (x * scale) / r;
    const py = centerY - (y * scale) / r;
    ctx.beginPath();
    ctx.arc(px, py, 4, 0, 2 * Math.PI);
    ctx.fillStyle = hit ? 'green' : 'red';
    ctx.fill();
    ctx.closePath();
}

function drawPoints(data) {
    const points = JSON.parse(data);

    const rElement = document.getElementById('r_data');
    const r = rElement ? rElement.value : "1";
    console.log(r);

    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = Math.min(width, height) * 0.4;

    if (Array.isArray(points)) {
        drawGraph();

        points
            .filter(point => String(point.r).trim() === r.trim())
            .forEach(point => {
                drawPoint(
                    parseFloat(point.x),
                    parseFloat(point.y),
                    parseFloat(point.r),
                    point.message === 'hit',
                    centerX,
                    centerY,
                    scale
                );
            });
    }
}


resizeCanvas();