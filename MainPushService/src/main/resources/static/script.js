document.getElementById('sendBtn').onclick = async () => {
    const statusDiv = document.getElementById('status');
    statusDiv.innerText = '????????...';

    const requestData = {
        batchId: parseInt(document.getElementById('batchId').value),
        companyId: parseInt(document.getElementById('companyId').value),
        channelType: document.getElementById('channelType').value,
        ttl: parseInt(document.getElementById('ttl').value),
        payload: {
            title: document.getElementById('title').value,
            body: document.getElementById('body').value,
            icon: document.getElementById('icon').value,
            url: document.getElementById('url').value,
        }
    };

    try {
        const response = await fetch('/api/sendPushToAllUsers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const result = await response.json();
            statusDiv.style.color = 'green';
            statusDiv.innerText = '??????? ??????????: ' + JSON.stringify(result);
        } else {
            statusDiv.style.color = 'red';
            statusDiv.innerText = '?????? ???????: ' + response.status;
        }
    } catch (error) {
        statusDiv.style.color = 'red';
        statusDiv.innerText = '?????? ????: ' + error.message;
    }
};