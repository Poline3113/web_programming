let x = [];
let y = "";
let r = [];

let xData = document.querySelectorAll('input[name="x-checkbox"]:checked');
let yData = document.getElementById("y-data");
let rData = document.querySelector('button[class="r-btn"].active');

function clickR(button) {
    const rValue = button.value;
    const targetId = `r${rValue}`;
    const targetGraph = document.getElementById(targetId);

    const isSelected = button.getAttribute('data-selected') === 'true';
    if (isSelected) {
        const index = r.indexOf(rValue);
        if (index === -1) {
            Console.log("R error");
        } else {
            r.splice(r.indexOf(rValue), 1);
        }
        button.classList.remove('active');
        button.removeAttribute('data-selected');
        if (targetGraph) {
            targetGraph.setAttribute('visibility', 'hidden');
        }
    } else {
        r.push(rValue);
        button.classList.add('active');
        button.setAttribute('data-selected', 'true');
        if (targetGraph) {
            targetGraph.setAttribute('visibility', 'visible');
        }
    }
    //r.forEach((value, index) => {
        //console.log(`R[${index}] = ${value}`);
    //});
}

function clickX(checkbox) {
    const xValue = parseFloat(checkbox.value);
    const isSelected = checkbox.checked;

    if (isSelected) {
        if (!x.includes(xValue)) {
            x.push(xValue);
        }
    } else {
        const index = x.indexOf(xValue);
        if (index !== -1) {
            x.splice(index, 1);
        }
    }
}

function validateData() {
    if (x.length === 0) {
        return { isValid: false, message: 'Select at least one X value' };
    }
    const validXValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
    for (let xVal of x) {
        const xNum = parseFloat(xVal);
        if (isNaN(xNum) || !validXValues.includes(xNum)) {
            return { isValid: false, message: 'X must be one of these: -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2' };
        }
    }

    if (y === null || y === "") {
        return { isValid: false, message: 'Y must be a number' };
    }
    const yNum = parseFloat(y);
    if (isNaN(yNum) || yNum < -5 || yNum > 3) {
        return { isValid: false, message: 'Y must be in [-5, 3]' };
    }

    const rValidation = validateR();

    if (!rValidation.risValid) {
        showError(rValidation.message);
        return { isValid: false, message};
    }

    return { isValid: true, message: 'all good' };
}

function validateR() {
    if (r.length === 0) {
        return { risValid: false, message: 'Select at least one R value' };
    }
    const validRValues = [1, 1.5, 2, 2.5, 3];
    for (let rVal of r) {
        const rNum = parseFloat(rVal);
        if (isNaN(rNum) || !validRValues.includes(rNum)) {
            return { risValid: false, message: 'R must be one of these: 1, 1.5, 2, 2.5, 3' };
        }
    }

    return { risValid: true, message: 'r is ok' };
}


async function sendDataToServer() {

    const yInput = document.getElementById("y-data");
    if (!yInput) {
        showError("input data error");
        return;
    }

    let yValue = yInput.value.trim();
    if (yValue === '') {
        y = null;
    } else {
       y = parseFloat(yValue);
       if (isNaN(y)) {
       y = null;
       }
    }

    const validation = validateData();

    if (!validation.isValid) {
        showError(validation.message);
        return;
    } else {
        hideError();
    }

    for (const rValue of r) {
        for (const xValue of x) {
            const params = new URLSearchParams({
            x: xValue,
            y: y,
            r: rValue
            });

            const response = await fetch(`./controller?${params.toString()}`, {
            method: 'POST',
            });
            if (response.ok) {
                console.log("success");
            } else {
                console.error("server error");
            }
        }
    }
    window.location.href = "result.jsp";
}

document.getElementById('result_btn').addEventListener('click', sendDataToServer);

document.querySelectorAll('input[name="x-checkbox"]').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        clickX(this);
    });
});


document.getElementById('plot').addEventListener('click', async function(event) {
    const svg = document.getElementById('plot');
    const rect = svg.getBoundingClientRect();

    const xPixel = event.clientX - rect.left;
    const yPixel = event.clientY - rect.top;

    const viewBox = svg.getAttribute('viewBox').split(' ').map(Number);
    const viewBoxX = viewBox[0];
    const viewBoxY = viewBox[1];
    const viewBoxWidth = viewBox[2];
    const viewBoxHeight = viewBox[3];

    const scale = 70;

    const xLogical = ((xPixel - rect.width * .5) * viewBoxWidth / rect.width) / scale;
    const yLogical = -((yPixel - rect.height * .5) * viewBoxHeight / rect.height) / scale;

    //console.log(xLogical);
    //console.log(yLogical);

    const rValidation = validateR();

    if (!rValidation.risValid) {
        showError(rValidation.message);
        return;
    } else {
        hideError();
    }

    for (const rValue of r) {
        const params = new URLSearchParams({
            x: xLogical,
            y: yLogical,
            r: rValue
        });

        const response = await fetch(`./controller?${params.toString()}`, {
        method: 'POST',
        });
        if (response.ok) {
            console.log("success");
        } else {
            console.error("server error");
        }
    }
    location.reload();
});

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

