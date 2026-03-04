// Слушаем событие поступления push-уведомления
self.addEventListener('push', function(event) {
    console.log('Push получен');

    let data = {};
    if (event.data) {
        try {
            // Пытаемся распарсить как JSON
            data = event.data.json();
        } catch (e) {
            // Если не JSON (например, тест из DevTools), берем как текст
            data = { title: "Тестовое пуш-уведомление", body: event.data.text() };
        }
    }

    const options = {
        body: data.body || 'Сообщение без текста',
        data: { url: data.url },
        icon: '/icon.png'
    };

    event.waitUntil(
        self.registration.showNotification(data.title || 'Оповещение', options)
    );
});

// Обработка клика по уведомлению
self.addEventListener('notificationclick', function(event) {
    event.notification.close(); // Закрываем уведомление

    // Если нажата кнопка "Открыть" или само тело уведомления
    event.waitUntil(
        clients.openWindow(event.notification.data.url)
    );
});

