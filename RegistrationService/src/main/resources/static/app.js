const PUBLIC_VAPID_KEY = "BAIfDZp99GjLv9FyAgBB2XPX-gt7BafHz61wvAip7dQsrc_134XG5hUJ0Ao-hVDrQo8zYcELEjInf-EKjnHIamo";

const subBtn = document.getElementById('subscribeBtn');
const unsubBtn = document.getElementById('unsubscribeBtn');
const loginInput = document.getElementById('loginInput');
const statusText = document.getElementById('status');

// Регистрация Service Worker
if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/sw.js')
        .then(() => {
            console.log('SW зарегистрирован');
            checkSubscription();
        });
}

// Проверка текущего состояния подписки
async function checkSubscription() {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.getSubscription();

    if (subscription) {
        subBtn.style.display = 'none';
        unsubBtn.style.display = 'inline';
        loginInput.disabled = true; // Запрещаем менять логин, пока есть подписка
    } else {
        subBtn.style.display = 'inline';
        unsubBtn.style.display = 'none';
        loginInput.disabled = false;
    }
}

// Кнопка ПОДПИСАТЬСЯ
subBtn.onclick = async () => {
    const login = loginInput.value.trim();
    if (!login) {
        alert("Пожалуйста, введите логин перед подпиской!");
        return;
    }

    try {
        const registration = await navigator.serviceWorker.ready;
        const subscription = await registration.pushManager.subscribe({
            userVisibleOnly: true,
            applicationServerKey: PUBLIC_VAPID_KEY
        });

        const subJson = subscription.toJSON();

        // Формируем объект согласно твоему SubscriptionAddRequest
        const payload = {
            userLogin: login,
            channelType: "WEB",
            subscription: {
                endpoint: subJson.endpoint,
                p256dh: subJson.keys.p256dh,
                auth: subJson.keys.auth
            }
        };

        await fetch('/subscription/add', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: { 'Content-Type': 'application/json' }
        });

        statusText.innerText = "Успешно подписаны!";
        checkSubscription();
    } catch (e) {
        console.error("Ошибка подписки:", e);
        statusText.innerText = "Ошибка при подписке.";
    }
};

// Кнопка ОТПИСАТЬСЯ
unsubBtn.onclick = async () => {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.getSubscription();

    if (subscription) {
        const login = loginInput.value;

        // Формируем объект согласно твоему SubscriptionDeleteRequest
        const deletePayload = {
            userLogin: login,
            subscriptionEndpoint: subscription.endpoint
        };

        try {
            await fetch('/subscription/delete', {
                method: 'POST',
                body: JSON.stringify(deletePayload),
                headers: { 'Content-Type': 'application/json' }
            });

            await subscription.unsubscribe();
            statusText.innerText = "Отписка выполнена.";
            checkSubscription();
        } catch (e) {
            console.error("Ошибка при отписке:", e);
        }
    }
};