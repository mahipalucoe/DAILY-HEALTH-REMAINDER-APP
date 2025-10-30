export const requestNotificationPermission = async () => {
  if (!('Notification' in window)) {
    console.warn('Notifications not supported in this browser');
    return false;
  }

  if (Notification.permission === 'granted') {
    return true;
  }

  if (Notification.permission !== 'denied') {
    const permission = await Notification.requestPermission();
    return permission === 'granted';
  }

  return false;
};

export const showNotification = (title, options) => {
  if (Notification.permission === 'granted') {
    new Notification(title, {
      icon: '/favicon.ico',
      badge: '/favicon.ico',
      ...options,
    });
  }
};

export const scheduleReminder = (reminder) => {
  const [hours, minutes] = reminder.time.split(':').map(Number);
  const now = new Date();
  const scheduledTime = new Date(now);
  scheduledTime.setHours(hours, minutes, 0, 0);

  if (scheduledTime <= now) {
    scheduledTime.setDate(scheduledTime.getDate() + 1);
  }

  const timeUntilReminder = scheduledTime.getTime() - now.getTime();

  if (timeUntilReminder > 0 && timeUntilReminder < 24 * 60 * 60 * 1000) {
    setTimeout(() => {
      showNotification(reminder.title, {
        body: reminder.notes || `Time for your ${reminder.type} reminder!`,
        tag: `reminder-${Date.now()}`,
        requireInteraction: true,
      });
    }, timeUntilReminder);
  }
};