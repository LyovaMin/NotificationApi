const API_URL = 'http://192.168.100.80:8080/api/updateStatus';

async function updatePushStatus(pushId, status) {
    console.log(`[SW] Попытка отправки статуса: ${status} для ID: ${pushId}`);
    if (!pushId) {
        console.warn('[SW] pushId отсутствует, пропуск сетевого запроса');
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            mode: 'cors',
            keepalive: true, // Критично для завершения запроса после закрытия уведомления
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                pushId: pushId,
                pushStatus: status
            })
        });

        if (response.ok) {
            console.log(`[SW] Статус ${status} успешно подтвержден сервером`);
        } else {
            console.error(`[SW] Сервер вернул ошибку ${response.status} при отправке ${status}`);
        }
    } catch (e) {
        console.error('[SW] Сетевая ошибка при fetch (проверьте доступность API):', e);
    }
}

self.addEventListener('push', function(event) {
    let data = {};
    if (event.data) {
        try {
            data = event.data.json();
            console.log('[SW] Push получен:', data);
        } catch (e) {
            console.error('[SW] Ошибка парсинга JSON в пуше');
        }
    }

    const pushId = data.pushId;

    const options = {
        body: data.body || 'Сообщение',
        icon: '/icon.png',
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
    console.log('[SW] Клик по уведомлению');
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
    console.log('[SW] Уведомление закрыто пользователем (смахивание)');

    const notification = event.notification;
    const pushId = (notification && notification.data) ? notification.data.pushId : null;

    if (pushId) {
        event.waitUntil(
            updatePushStatus(pushId, 'DISMISSED')
        );
    } else {
        console.warn('[SW] Данные pushId не найдены в событии закрытия');
    }
});