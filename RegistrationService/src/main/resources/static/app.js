const PUBLIC_VAPID_KEY = "BAIfDZp99GjLv9FyAgBB2XPX-gt7BafHz61wvAip7dQsrc_134XG5hUJ0Ao-hVDrQo8zYcELEjInf-EKjnHIamo";
const subBtn = document.getElementById('subscribeBtn');
const unsubBtn = document.getElementById('unsubscribeBtn');

// Регистрация SW при загрузке
if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/sw.js')
        .then(reg => {
            console.log('SW зарегистрирован');
            checkSubscription();
        });
}

async function checkSubscription() {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.getSubscription();

    if (subscription) {
        subBtn.style.display = 'none';
        unsubBtn.style.display = 'inline';
    } else {
        subBtn.style.display = 'inline';
        unsubBtn.style.display = 'none';
    }
}

subBtn.onclick = async () => {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: PUBLIC_VAPID_KEY
    });

    await fetch('/api/subscribe', {
        method: 'POST',
        body: JSON.stringify(subscription.toJSON()),
        headers: { 'Content-Type': 'application/json' }
    });
    checkSubscription();
};

unsubBtn.onclick = async () => {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.getSubscription();

    if (subscription) {
        // 1. Сначала уведомляем сервер (пока у нас есть данные подписки)
        try {
            await fetch('/api/unsubscribe', {
                method: 'POST',
                body: JSON.stringify(subscription.toJSON()),
                headers: { 'Content-Type': 'application/json' }
            });
            console.log('Сервер удалил подписку');
        } catch (e) {
            console.error('Не удалось связаться с сервером для отписки', e);
        }

        // 2. Затем удаляем подписку в самом браузере
        await subscription.unsubscribe();
        console.log('Браузер отписан');
    }
    checkSubscription();
};