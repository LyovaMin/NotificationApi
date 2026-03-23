const API_URL = 'http://192.168.100.34:8080/api/updateStatus';

async function updatePushStatus(pushId, status) {
    console.log(`Отправка статуса: ${status} для ID: ${pushId}`);
    if (!pushId) {
        console.warn('pushId отсутствует, статус не будет обновлен');
        return;
    }

    try {
        await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                pushId: pushId,
                pushStatus: status
            })
        });
        console.log(`Статус ${status} успешно отправлен`);
    } catch (e) {
        console.error('Ошибка при fetch:', e);
    }
}

self.addEventListener('push', function(event) {
    let data = {};
    if (event.data) {
        try {
            data = event.data.json();
            console.log('Данные получены:', data);
        } catch (e) {
            console.error('Ошибка парсинга JSON в пуше');
        }
    }

    const pushId = data.pushId;

    const options = {
        body: data.body || 'Сообщение',
        icon: '/icon.png',
        // ВАЖНО: сохраняем эти данные ВНУТРИ уведомления для других событий
        data: {
            url: data.url || '/',
            pushId: pushId
        }
    };

    event.waitUntil(
        Promise.all([
            self.registration.showNotification(data.title || 'Оповещение', options),
            updatePushStatus(pushId, 'DELIVERED')
        ])
    );
});

self.addEventListener('notificationclick', function(event) {
    console.log('Клик по уведомлению');
    // Достаем сохраненные данные из уведомления
    const notificationData = event.notification.data;
    const pushId = notificationData.pushId;
    const url = notificationData.url;

    event.notification.close();

    event.waitUntil(
        Promise.all([
            clients.openWindow(url),
            updatePushStatus(pushId, 'READ')
        ])
    );
});

self.addEventListener('notificationclose', function(event) {
    console.log('Уведомление закрыто');
    const pushId = event.notification.data.pushId;

    event.waitUntil(
        updatePushStatus(pushId, 'DISMISSED')
    );
});